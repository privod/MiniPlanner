package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.view.adapter.BaseAdapter;
import ru.home.miniplanner.view.adapter.PlanAdapter;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.divider.DividerItemDecoration;
import ru.home.miniplanner.view.edit.PlanEditActivity;
import ru.home.miniplanner.view.widget.AvatarViewSwitcher;

public class PlansActivity extends AppCompatActivity {

    private Dao<Plan> planDao;
    PlanAdapter planAdapter;
    RecyclerView recyclerView;

    MenuItem editMenuItem;
    ActionMode actionMode;
    private MultiSelector multiSelector = new MultiSelector();
    private ModalMultiSelectorCallback mActionModeCallback
            = new ModalMultiSelectorCallback(multiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            getMenuInflater().inflate(R.menu.action_mode, menu);

            editMenuItem = menu.findItem(R.id.action_edit);
            ViewService.setStatusBar(PlansActivity.this, R.color.material_gray_700);

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
//                List<Plan> plans = planDao.getAll();

                List<Integer> selectedPositions = new ArrayList<>(multiSelector.getSelectedPositions());
                for (int position: selectedPositions) {
                    planDao.delete(planAdapter.getItemId(position));
//                    planAdapter.remove(position);
                }
                planAdapter.updateData(planDao.getAll());
//                planAdapter.notifyDataSetChanged();

                mode.finish();
                multiSelector.clearSelections();

                return true;
            } else if (item.getItemId() == R.id.action_edit) {
                int position = multiSelector.getSelectedPositions().get(0);         // Режим редактирования возможен только если выцделен один элемен, поэтому цикла не делаю, а выбираю нулевой элемент.
                Plan plan = planAdapter.getData().get(position);
                startPlanEditActivity(plan);
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

//            for (int position: multiSelector.getSelectedPositions()) {
//                selectSwitch(position, 0);      // Назанчение второго параметра (long id) непонятно, внутри метода isSelected он не используется
//            }

            multiSelector.setSelectable(false);
            multiSelector.clearSelections();

            ViewService.setStatusBar(PlansActivity.this, R.color.colorPrimaryDark);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter(multiSelector);
//        planAdapter.updateData(planDao.getAll());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (null != recyclerView) {
            recyclerView.setAdapter(planAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new SlideInLeftAnimator());
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.plans_appearance);
//        recyclerView.startAnimation(anim);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startPlanEditActivity(new Plan());
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        planAdapter.updateData(planDao.getAll());
//        planAdapter.notifyDataSetChanged();

//        for (int position = 0; position < planAdapter.getItemCount(); position++) {
//            planAdapter.notifyItemChanged(position);
//        }

//        planAdapter.notifyItemRangeInserted(0, planAdapter.getItemCount());
    }

    public void startPartiesActivity(int position) {
        Intent intent = new Intent(PlansActivity.this, PartiesActivity.class);
        intent.putExtra(Plan.EXTRA_NAME, planDao.getAll().get(position));
        startActivityForResult(intent, getResources().getInteger(R.integer.request_code_parties));
    }

    private void startPlanEditActivity(Plan plan) {
        Intent intent = new Intent(PlansActivity.this, PlanEditActivity.class);
//        intent.putExtra(getString(R.string.argument_id), id);
        intent.putExtra(plan.getClass().getSimpleName(), plan);
        startActivityForResult(intent, getResources().getInteger(R.integer.request_code_plan_edit));
    }

    public void selectSwitch(BaseAdapter.ViewHolder holder) {
        if (!multiSelector.isSelectable()) {
            actionMode = startSupportActionMode(mActionModeCallback);
        }

        multiSelector.setSelected(holder, !multiSelector.isSelected(holder.getAdapterPosition(), holder.getItemId()));
//        multiSelector.setSelected(position, id, !multiSelector.isSelected(position, id));           // Назанчение второго параметра (long id) непонятно, внутри метода isSelected он не используется

        int selectCount = multiSelector.getSelectedPositions().size();
        if (selectCount == 0) {
            actionMode.finish();
        } else if (selectCount == 1) {
            editMenuItem.setVisible(true);
        } else {
            editMenuItem.setVisible(false);
        }

//        AvatarViewSwitcher avatarViewSwitcher = (AvatarViewSwitcher) (recyclerView.getChildAt(position).findViewById(R.id.view_switcher_avatar));
        AvatarViewSwitcher avatarViewSwitcher = (AvatarViewSwitcher) holder.itemView.findViewById(R.id.view_switcher_avatar);
        avatarViewSwitcher.showNext();

//        planAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == getResources().getInteger(R.integer.request_code_plan_edit)
                && resultCode == RESULT_OK) {
            Plan plan = (Plan) data.getSerializableExtra(Plan.class.getSimpleName());

//            if (plan.getId() == 0) {
//                planAdapter.notifyItemInserted(planAdapter.getItemCount() - 1);
//            } else {
//                for (int position = 0; position < planAdapter.getItemCount(); position++) {
//                    if (planAdapter.getData().get(position).getId().equals(plan.getId())) {
//                        planAdapter.getData().set(position, plan);
//                    }
//                }
//            }

            planDao.save(plan);

//            planAdapter.updateData(planDao.getAll());
//            long id = data.getLongExtra(getString(R.string.argument_id), 0);
//            int position = planDao.getAll().indexOf(planDao.getById(id));
//            planAdapter.notifyItemInserted(position);

            planAdapter.updateData(planDao.getAll());
//            planAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_cre_debug_data) {

            Dao<Plan> planDao = HelperFactory.getHelper().getPlanDao();

            Plan plan;
            plan = new Plan();
            plan.setName("Рыбылка");
            plan.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            planDao.save(plan);
            int posBegin = planDao.getAll().indexOf(plan);

            plan = new Plan();
            plan.setName("Хмельники");
            plan.setDateReg(new GregorianCalendar(2015, 7, 2).getTime());
            planDao.save(plan);

            plan = new Plan();
            plan.setName("Баня");
            plan.setDateReg(new GregorianCalendar(2016, 2, 13).getTime());
            planDao.save(plan);

            plan = new Plan();
            plan.setName("Дача");
            plan.setDateReg(new GregorianCalendar(2016, 7, 17).getTime());
            planDao.save(plan);

            planAdapter.updateData(planDao.getAll());
//            planAdapter.notifyDataSetChanged();
//            planAdapter.notifyItemRangeInserted(posBegin, posBegin + 4);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }
}
