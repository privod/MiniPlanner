package ru.home.miniplanner.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.adapter.BaseAdapter;
import ru.home.miniplanner.view.widget.AvatarViewSwitcher;


public class BaseListFragment  <T extends Domain> extends Fragment {
    private static final String ARG_EDIT_ACTIVITY_CLASS = "edit_activity_class";

    protected Class<? extends Activity> editActivityClass;
    private Class<T> entityClass;
    private Class<? extends Activity> insideActivityClass;

//    private OnFragmentInteractionListener activity;    // TODO Возможно достаточно класса Context
    private AppCompatActivity activity;
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
                adapter.updateData(dao.getAll());

                mode.finish();
                multiSelector.clearSelections();

                return true;

            } else if (item.getItemId() == R.id.action_edit) {

                int position = multiSelector.getSelectedPositions().get(0);         // Режим редактирования возможен только если выцделен один элемен, поэтому цикла не делаю, а выбираю нулевой элемент.
                T entity = adapter.getData().get(position);
                startEditActivity(entity);
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

    public BaseListFragment() {
        // Required empty public constructor
    }

//    public static BaseListFragment newInstance(Class<? extends Activity> editActivityClass) {
//        BaseListFragment fragment = new BaseListFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_EDIT_ACTIVITY_CLASS, editActivityClass);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            editActivityClass = (Class<? extends Activity>) getArguments().getSerializable(ARG_EDIT_ACTIVITY_CLASS);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (null != recyclerView) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setAdapter(adapter);


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
//        if (activity instanceof OnFragmentInteractionListener) {
//            this.activity = (OnFragmentInteractionListener) activity;
//        } else {
//            throw new RuntimeException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.updateData(dao.getAll());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    protected Intent getEditActivityIntent(T entity) {
        Intent intent = new Intent((Context) activity, editActivityClass);
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        return  intent;
    }

    protected void startEditActivity(T entity) {
        Intent intent = getEditActivityIntent(entity);
        startActivityForResult(intent, request_code_edit);
    }
}
