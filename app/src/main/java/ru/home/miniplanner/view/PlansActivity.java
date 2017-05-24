package ru.home.miniplanner.view;

import android.view.Menu;
import android.view.MenuItem;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import ru.home.miniplanner.R;
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

            Plan plan = new Plan();
            plan.setName("Рыбылка");
            plan.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            planDao.save(plan);

            Party partyP = new Party();
            partyP.setPlan(plan);
            partyP.setName("Петя");
            partyDao.save(partyP);

            Bay bay = new Bay();
            bay.setDescription("Закуска");
            bay.setSum(new BigDecimal("500"));
            bay.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            bay.setParty(partyP);
            bayDao.save(bay);

            Party partyV = new Party();
            partyV.setPlan(plan);
            partyV.setName("Вася");
            partyDao.save(partyV);

            Contribution contribution = new Contribution();
            contribution.setFrom(partyV);
            contribution.setTo(partyP);
            contribution.setSum(new BigDecimal("300"));
            contributionDao.save(contribution);

            Party partyPH = new Party();
            partyPH.setPlan(plan);
            partyPH.setName("Пафнутий");
            partyDao.save(partyPH);

            bay = new Bay();
            bay.setDescription("Водка");
            bay.setSum(new BigDecimal("1000"));
            bay.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            bay.setParty(partyPH);
            bayDao.save(bay);

            bay = new Bay();
            bay.setDescription("Пиво");
            bay.setSum(new BigDecimal("900"));
            bay.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            bay.setParty(partyPH);
            bayDao.save(bay);

            contribution = new Contribution();
            contribution.setFrom(partyV);
            contribution.setTo(partyPH);
            contribution.setSum(new BigDecimal("400"));
            contributionDao.save(contribution);

            contribution = new Contribution();
            contribution.setFrom(partyP);
            contribution.setTo(partyPH);
            contribution.setSum(new BigDecimal("200"));
            contributionDao.save(contribution);


            plan = new Plan();
            plan.setName("Хмельники");
            plan.setDateReg(new GregorianCalendar(2015, 7, 2).getTime());
            planDao.save(plan);

            Party partyI = new Party();
            partyI.setPlan(plan);
            partyI.setName("Игорь");
            partyDao.save(partyI);

            Party partyD = new Party();
            partyD.setPlan(plan);
            partyD.setName("Дима");
            partyDao.save(partyD);

            bay = new Bay();
            bay.setDescription("Закуска");
            bay.setSum(new BigDecimal("500"));
            bay.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            bay.setParty(partyI);
            bayDao.save(bay);

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

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }
}
