package ru.bespalov.miniplanner.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.ArrayList;
import java.util.List;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.db.Dao;
import ru.bespalov.miniplanner.model.Domain;
import ru.bespalov.miniplanner.view.adapter.BaseAdapter;
import ru.bespalov.miniplanner.view.widget.AvatarViewSwitcher;

import static android.app.Activity.RESULT_OK;


public abstract class BaseListFragment  <T extends Domain> extends Fragment {
    protected Class<? extends Activity> editActivityClass;
    private Class<? extends Activity> insideActivityClass;

    protected PartyContentActivity activity;
    protected Dao<T> dao;
    protected int request_code_edit;

    protected BaseAdapter<? extends BaseAdapter.ViewHolder, T> adapter;
    protected RecyclerView recyclerView;

    private MenuItem editMenuItem;
    private ActionMode actionMode;
    protected MultiSelector multiSelector = new MultiSelector();
    private ModalMultiSelectorCallback mActionModeCallback
            = new ModalMultiSelectorCallback(multiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            activity.getMenuInflater().inflate(R.menu.action_mode, menu);

            editMenuItem = menu.findItem(R.id.action_edit);
            ViewService.setStatusBar(activity, R.color.material_gray_700);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            if (super.onPrepareActionMode(actionMode, menu)) {
                multiSelector.setSelectable(true);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_remove) {

                List<Integer> selectedPositions = new ArrayList<>(multiSelector.getSelectedPositions());
                for (int position: selectedPositions) {
                    dao.delete(adapter.getData().get(position));
                }
                adapter.updateData(getList());
                activity.refreshSubtitle();

                mode.finish();
                multiSelector.clearSelections();

                return true;

            } else if (item.getItemId() == R.id.action_edit) {

                int position = multiSelector.getSelectedPositions().get(0);         // Режим редактирования возможен только если выцделен один элемен, поэтому цикла не делаю, а выбираю нулевой элемент.
                T entity = adapter.getData().get(position);
                listItemEdit(entity);
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            super.onDestroyActionMode(actionMode);

            for (int i = 0; i < recyclerView.getChildCount(); i++ ) {
                View view = recyclerView.getChildAt(i);
                AvatarViewSwitcher avatarViewSwitcher = (AvatarViewSwitcher) (view.findViewById(R.id.view_switcher_avatar));
                BaseAdapter.ViewHolder holder = (BaseAdapter.ViewHolder) recyclerView.getChildViewHolder(view);
                if (multiSelector.isSelected(holder.getAdapterPosition(), holder.getItemId())) {
                    avatarViewSwitcher.showNext();
                }
            }

            multiSelector.setSelectable(false);
            multiSelector.clearSelections();

            ViewService.setStatusBar(activity, R.color.colorPrimaryDark);
        }
    };

    public BaseListFragment(Class<? extends Activity> editActivityClass,
                            Class<? extends Activity> insideActivityClass
    ) {
        this.editActivityClass = editActivityClass;
        this.insideActivityClass = insideActivityClass;
    }

    protected abstract T newEntityInstance();

    protected abstract List<T> getList();

    private class ItemAction implements BaseAdapter.ItemAction {

        @Override
        public void open(int position) {
            BaseListFragment.this.listItemOpen(position);
        }

        @Override
        public void selectSwitch(BaseAdapter.ViewHolder holder) {
            BaseListFragment.this.selectSwitch(holder);
        }

        @Override
        public void edit(int position) {
            T entity = adapter.getData().get(position);
            listItemEdit(entity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ItemAction action = new ItemAction();
        adapter.setItemAction(action);

        View view = inflater.inflate(R.layout.fragment_base_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (null != recyclerView) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (activity != null) {
//            activity.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.activity = (PartyContentActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.updateData(getList());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public void listItemOpen(int position) {
        Intent intent = new Intent(activity, insideActivityClass);
        T entity = getList().get(position);
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        startActivity(intent);
    }

    protected Intent getEditActivityIntent(T entity) {
        Intent intent = new Intent(activity, editActivityClass);
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        return  intent;
    }

    protected void listItemEdit(T entity) {
        Intent intent = getEditActivityIntent(entity);
        startActivityForResult(intent, request_code_edit);
    }

    protected void newItemEdit() {
        listItemEdit(newEntityInstance());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == request_code_edit && resultCode == RESULT_OK) {
            adapter.updateData(getList());
            activity.refreshSubtitle();
        }
    }

    public void selectSwitch(BaseAdapter.ViewHolder holder) {
        if (!multiSelector.isSelectable()) {
            actionMode = activity.startSupportActionMode(mActionModeCallback);
        }

        multiSelector.setSelected(holder, !multiSelector.isSelected(holder.getAdapterPosition(), holder.getItemId()));

        int selectCount = multiSelector.getSelectedPositions().size();
        if (selectCount == 0) {
            actionMode.finish();
        } else if (selectCount == 1) {
            editMenuItem.setVisible(true);
        } else {
            editMenuItem.setVisible(false);
        }

        AvatarViewSwitcher avatarViewSwitcher = (AvatarViewSwitcher) holder.itemView.findViewById(R.id.view_switcher_avatar);
        avatarViewSwitcher.showNext();
    }
}
