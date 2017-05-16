package ru.home.miniplanner.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.adapter.BaseAdapter;
import ru.home.miniplanner.view.divider.DividerItemDecoration;
import ru.home.miniplanner.view.widget.AvatarViewSwitcher;

/**
 * Created by privod on 26.09.2016.
 */
//public abstract class BaseListActivity<T extends Domain>  extends AppCompatActivity {
public abstract class BaseListActivity<T extends Domain>  extends BaseActivity<T> {

//    private final Class<? extends Activity> editActivityClass;
    private final Class<T> entityClass;
    private final Class<? extends Activity> insideActivityClass;

//    protected Dao<T> dao;
    protected BaseAdapter<? extends BaseAdapter.ViewHolder, T> adapter;
//    protected RecyclerView recyclerView;
//    protected int request_code_edit;

    protected RecyclerView recyclerView;
    private MenuItem editMenuItem;
    private ActionMode actionMode;
    protected MultiSelector multiSelector = new MultiSelector();
    private ModalMultiSelectorCallback mActionModeCallback
            = new ModalMultiSelectorCallback(multiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            getMenuInflater().inflate(R.menu.action_mode, menu);

            editMenuItem = menu.findItem(R.id.action_edit);
            ViewService.setStatusBar(BaseListActivity.this, R.color.material_gray_700);

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

            ViewService.setStatusBar(BaseListActivity.this, R.color.colorPrimaryDark);
        }
    };

    public BaseListActivity(Class<? extends Activity> editActivityClass,
            Class<T> entityClass,
            Class<? extends Activity> insideActivityClass
    ) {
        super(editActivityClass);
        this.entityClass = entityClass;
        this.insideActivityClass = insideActivityClass;
    }

//    protected abstract T newEntityInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        HelperFactory.setHelper(this);
        FrameLayout layout = (FrameLayout) findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.recycler_view, layout, true);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (null != recyclerView) {
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new SlideInLeftAnimator());
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        if (null != fab) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startEditActivity(newEntityInstance());
//
//                }
//            });
//        }
    }

    public void startInsideActivity(int position) {
        Intent intent = new Intent(BaseListActivity.this, insideActivityClass);
        T entity = dao.getAll().get(position);
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        startActivity(intent);
    }

//    private void startEditActivity(T entity) {
//        Intent intent = new Intent(BaseListActivity.this, editActivityClass);
//        intent.putExtra(entity.getClass().getSimpleName(), entity);
////        startActivityForResult(intent, getResources().getInteger(R.integer.request_code_plan_edit));
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
//        ActivityCompat.startActivityForResult(this, intent, request_code_edit, options.toBundle());
//    }

    @Override
    protected void startEditActivity(T entity) {
//        super.startEditActivity(entity);
        Intent intent = getEditActivityIntent(entity);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        ActivityCompat.startActivityForResult(this, intent, request_code_edit, options.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == request_code_edit && resultCode == RESULT_OK) {
            T entity = entityClass.cast(data.getSerializableExtra(entityClass.getSimpleName()));
            dao.save(entity);
            adapter.updateData(dao.getAll());
        }
    }

    public void selectSwitch(BaseAdapter.ViewHolder holder) {
        if (!multiSelector.isSelectable()) {
            actionMode = startSupportActionMode(mActionModeCallback);
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
