package ru.home.miniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

import ru.home.miniplanner.view.adapter.PlanAdapter;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.PlanDao;

public class PlansActivity extends AppCompatActivity {

    private static final String TAG = PlansActivity.class.getSimpleName();
    private static final int REQUEST_PLAN_EDIT = 10;

    PlanDao planDao;
//    List<Plan> planList;
    PlanAdapter planAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter(this, planDao.getAll());
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(planAdapter);

        registerForContextMenu(listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlanEditActivity(new Plan());
            }
        });
    }

    private void openPlanEditActivity(Plan plan) {
        Intent intent = new Intent(PlansActivity.this, PlanEditActivity.class);
        intent.putExtra("plan", plan);
        startActivityForResult(intent, REQUEST_PLAN_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == REQUEST_PLAN_EDIT && resultCode == RESULT_OK) {
            Plan plan = (Plan) data.getSerializableExtra("plan");
            planDao.save(plan);
            planAdapter.setData(planDao.getAll());
            planAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_plan, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = item.getItemId();
        Plan plan = (Plan) listView.getItemAtPosition(menuInfo.position);

        if (id == R.id.contextmenu_plan_edit) {
            openPlanEditActivity(plan);
            return true;
        } else if (id == R.id.contextmenu_plan_del) {
            planDao.delete(plan);
            planAdapter.setData(planDao.getAll());
            planAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plans, menu);
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
