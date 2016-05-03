package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.view.RVAdater.PlanAdapter;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.PlanDao;
import ru.home.miniplanner.view.divider.DividerItemDecoration;
import ru.home.miniplanner.view.edit.PlanEditActivity;
import ru.home.miniplanner.view.widget.AvatarLetterView;

public class PlansActivity extends AppCompatActivity {

    private static final String LOG_TAG = PlansActivity.class.getSimpleName();
    public static final int REQUEST_PARTIES = 10;
    private static final int REQUEST_PLAN_EDIT = 20;

    PlanDao planDao;
    PlanAdapter planAdapter;
    RecyclerView recyclerView;
//    List<Plan> planSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter(/*this*/);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(planAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                openPartiesActivity(planDao.getAll().get(position));
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                planSelect(view, planDao.getAll().get(position));
//            }
//        }));

//        registerForContextMenu(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlanEditActivity(0);
            }
        });

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.plans_appearance);
        recyclerView.startAnimation(anim);
    }

    @Override
    protected void onResume() {
        super.onResume();

        planAdapter.setPlans(planDao.getAll());
        planAdapter.notifyDataSetChanged();
    }

//    public List<Plan> getAllPlans() {
//        return planDao.getAll();
//    }

    public void openPartiesActivity(int position) {
        Intent intent = new Intent(PlansActivity.this, PartiesActivity.class);
        intent.putExtra(Plan.EXTRA_NAME, planDao.getAll().get(position));
        startActivityForResult(intent, REQUEST_PARTIES);
    }

    private void openPlanEditActivity(int position) {
        Plan plan;
        if (position == 0) {
            plan = new Plan();
        } else {
            plan = planDao.getAll().get(position);
        }
        Intent intent = new Intent(PlansActivity.this, PlanEditActivity.class);
        intent.putExtra(Plan.EXTRA_NAME, plan);
        startActivityForResult(intent, REQUEST_PLAN_EDIT);
    }

    public void planSelect(View view, int position) {

        Plan plan = planDao.getAll().get(position);
        plan.setSelected(!plan.isSelected());
        AvatarLetterView avatarLetterView = (AvatarLetterView) view.findViewById(R.id.avatarLetter);
        avatarLetterView.AnimationSwitchSelectedState();

//        final ImageView selectorImage  = (ImageView) view.findViewById(R.id.selectorImage);
//        final Animation animToSide = AnimationUtils.loadAnimation(this, R.anim.to_side);
//        final Animation animFromSide = AnimationUtils.loadAnimation(this, R.anim.from_side);
//        animToSide.setAnimationListener( new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                avatarLetterView.setVisibility(View.INVISIBLE);
//                selectorImage.setVisibility(View.VISIBLE);
//                selectorImage.startAnimation(animFromSide);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//        });
//        avatarLetterView.startAnimation(animToSide);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == REQUEST_PLAN_EDIT && resultCode == RESULT_OK) {
            Plan plan = (Plan) data.getSerializableExtra(Plan.EXTRA_NAME);
            planDao.save(plan);
            planAdapter.setPlans(planDao.getAll());
            planAdapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.context_plan, menu);
//
//        if (v.getParent() instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) v.getParent();
//            planAdapter.setPosition(recyclerView.getChildAdapterPosition(v));
//        }
////        AdapterView.AdapterContextMenuInfo info;
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        long id = item.getItemId();
//
//        int position = planAdapter.getPosition();
//        Plan plan = getAllPlans().get(position);
//
//        if (id == R.id.context_plan_edit) {
//            openPlanEditActivity(plan);
//            return true;
//        } else if (id == R.id.context_plan_del) {
//            planDao.delete(plan);
//            planAdapter.setPlans(getAllPlans());
//            planAdapter.notifyDataSetChanged();
//            return true;
//        }
//
//        return super.onContextItemSelected(item);
//    }

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();      // TODO Нет гарантий вызова onDestroy, переделать
        super.onDestroy();
    }
}
