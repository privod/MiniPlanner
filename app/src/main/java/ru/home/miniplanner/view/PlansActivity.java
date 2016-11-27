package ru.home.miniplanner.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.GregorianCalendar;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.view.adapter.PlanAdapter;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.edit.PlanEditActivity;

public class PlansActivity extends BaseListActivity<Plan> {
    public PlansActivity() {
        super(Plan.class, PartiesActivity.class, PlanEditActivity.class);

        request_code_edit = RequestCode.PLAN_EDIT;
    }

    @Override
    protected Plan newEntityInstance() {
        return new Plan();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPlanDao();
        adapter = new PlanAdapter(multiSelector);
        recyclerView.setAdapter(adapter);
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
        } else if (id == R.id.action_cre_debug_data) {

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

            adapter.updateData(planDao.getAll());

            return true;    // */
        }

        return super.onOptionsItemSelected(item);
    }
}
