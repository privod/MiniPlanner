package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.view.RVAdater.PlanAdapter;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.PlanDao;
import ru.home.miniplanner.view.divider.DividerItemDecoration;
import ru.home.miniplanner.view.edit.PlanEditActivity;

public class PlansActivity extends AppCompatActivity {

    private static final String LOG_TAG = PlansActivity.class.getSimpleName();
    public static final int REQUEST_PARTIES = 10;
    private static final int REQUEST_PLAN_EDIT = 20;

    PlanDao planDao;
    PlanAdapter planAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(planAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Plan plan = planDao.getAll().get(position);
//                openPartiesActivity(plan);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

//        registerForContextMenu(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlanEditActivity(new Plan());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Plan> plans = planDao.getAll();
//        for (Plan plan : plans){
//            planDao.refresh(plan);
//        }
        planAdapter.setPlans(plans);
        planAdapter.notifyDataSetChanged();
    }

    private void openPartiesActivity(Plan plan) {
        Intent intent = new Intent(PlansActivity.this, PartiesActivity.class);
        intent.putExtra(Plan.EXTRA_NAME, plan);
        startActivityForResult(intent, REQUEST_PARTIES);
    }

    private void openPlanEditActivity(Plan plan) {
        Intent intent = new Intent(PlansActivity.this, PlanEditActivity.class);
        intent.putExtra(Plan.EXTRA_NAME, plan);
        startActivityForResult(intent, REQUEST_PLAN_EDIT);
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
//    public void onClick(View v) {
//        Log.e(LOG_TAG, "OnClickListener called on CardView number " + getPosition());
////
//            Plan plan = plans.get(v.getPosition());
//            Intent intent = new Intent(view.getContext(), PartiesActivity.class);
//            intent.putExtra(Plan.EXTRA_NAME, plan);
//            ((Activity) view.getContext()).startActivityForResult(intent, PlansActivity.REQUEST_PARTIES);
//    }
//    @Override
//    public void onContextClick(AdapterView<?> parent, View view, int position, long id) {
//        Plan plan = (Plan) recyclerView.getItemAtPosition(position);
//        openPartiesActivity(plan);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_plan, menu);

        if (v.getParent() instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) v.getParent();
            planAdapter.setPosition(recyclerView.getChildAdapterPosition(v));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = item.getItemId();
//        Plan plan = (Plan) planDao.getAll().get(menuInfo.position);

        int position = planAdapter.getPosition();
        Plan plan = (Plan) planDao.getAll().get(position);

        if (id == R.id.context_plan_edit) {
            openPlanEditActivity(plan);
            return true;
        } else if (id == R.id.context_plan_del) {
            planDao.delete(plan);
            planAdapter.setPlans(planDao.getAll());
            planAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }
}
