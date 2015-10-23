package ru.home.miniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.adapter.PlanAdapter;
import ru.home.miniplanner.db.DBHelper;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.domain.Plan;
import ru.home.miniplanner.service.PlanDao;
import ru.home.miniplanner.service.PlanerService;

public class PlansActivity extends AppCompatActivity {

    private static final String TAG = PlansActivity.class.getSimpleName();

    PlanerService service = new PlanerService(new DBHelper(this));
    PlanDao planDao;
    List<Plan> planList;
    PlanAdapter planAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        try {
            planDao = HelperFactory.getHelper().getPlanDao();
            planList = planDao.getAllPlans();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        planAdapter = new PlanAdapter(this, planList);
        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlansActivity.this, EditActivity.class);
                //intent.putExtra("plan", );
                startActivityForResult(intent, 1);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null == data) {
            return;
        }
        Plan plan = (Plan) data.getSerializableExtra("plan");
        service.save(plan);
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
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}
