package ru.home.miniplanner.view;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.Menu;
import android.view.MenuItem;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.adapter.PlanAdapter;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.edit.PlanEditActivity;

public class PlansActivity extends BaseListActivity<Plan> {
    public PlansActivity() {
        super(PlanEditActivity.class, Plan.class, PartiesActivity.class);

        request_code_edit = RequestCode.PLAN_EDIT;
    }

    @Override
    protected Plan newEntityInstance() {
        return new Plan();
    }

    @Override
    protected List<Plan> getList() {
        return dao.getAll();
    }

    @Override
    protected void onCreateBeforeView() {
        super.onCreateBeforeView();

        HelperFactory.setHelper(this);

        dao = HelperFactory.getHelper().getPlanDao();
        adapter = new PlanAdapter(multiSelector);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinator);
            layout.setTransitionName("plan");
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
        } else if (id == R.id.action_cre_debug_data) {

            Dao<Plan> planDao = HelperFactory.getHelper().getPlanDao();
            Dao<Party> partyDao = HelperFactory.getHelper().getPartyDao();
            Dao<Bay> bayDao = HelperFactory.getHelper().getBayDao();
            Dao<Contribution> contributionDao = HelperFactory.getHelper().getContributionDao();

            Plan plan = Util.getPlanInstance("Рыбылка", new GregorianCalendar(2015, 5, 28).getTime());
            planDao.save(plan);

            Party partyP = Util.getPartyInstance("Петя", plan);
            partyDao.save(partyP);
            Party partyV = Util.getPartyInstance("Вася", plan);
            partyDao.save(partyV);
            Party partyPH = Util.getPartyInstance("Пафнутий", plan);
            partyDao.save(partyPH);

            bayDao.save(Util.getBayInstace("Закуска", new BigDecimal("500"), new GregorianCalendar(2015, 5, 28).getTime(), partyP));
            bayDao.save(Util.getBayInstace("Водка", new BigDecimal("1000"), new GregorianCalendar(2015, 5, 28).getTime(), partyPH));
            bayDao.save(Util.getBayInstace("Пиво", new BigDecimal("900"), new GregorianCalendar(2015, 5, 28).getTime(), partyPH));

            contributionDao.save(Util.getContributionInstance(partyV, partyP, new BigDecimal("300")));
            contributionDao.save(Util.getContributionInstance(partyV, partyPH, new BigDecimal("700")));
            contributionDao.save(Util.getContributionInstance(partyP, partyPH, new BigDecimal("500")));

            plan = Util.getPlanInstance("Хмельники", new GregorianCalendar(2015, 7, 2).getTime());
            planDao.save(plan);

            Party partyI = Util.getPartyInstance("Игорь", plan);
            partyDao.save(partyI);

            Party partyD = Util.getPartyInstance("Дима", plan);
            partyDao.save(partyD);

            bayDao.save(Util.getBayInstace("Закуска", new BigDecimal("500"), new GregorianCalendar(2015, 6, 30).getTime(), partyI));

            plan = Util.getPlanInstance("Дача", new GregorianCalendar(2016, 7, 17).getTime());
            planDao.save(plan);

            adapter.updateData(planDao.getAll());

            return true;    // */
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }
}
