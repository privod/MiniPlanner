package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;
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

    private static enum Mode {
        NORMAL, EDIT, REMOVE
    }

    private static Mode mode;

    PlanDao planDao;
    PlanAdapter planAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
//    List<Plan> planSelected;

    MenuItem editMenuItem;
    MenuItem removeMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(planAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.plans_appearance);
        recyclerView.startAnimation(anim);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlanEditActivity(0);
            }
        });
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

    private int getSelectedPlanCount(List<Plan> plans) {
        int result = 0;
        for (Plan plan: plans) {
            if (plan.isSelected()) { result += 1; }
        }
        return result;
    }

    private void startMode(Mode modeToStart) {
        if (modeToStart == Mode.NORMAL) {
            editMenuItem.setVisible(false);
            removeMenuItem.setVisible(false);
            toolbar.setLogo(null);
//            toolbar.setTitle(getString(R.string.title_plans));
            toolbar.setBackgroundResource(R.color.colorPrimary);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        } else if (modeToStart == Mode.EDIT) {
            editMenuItem.setVisible(true);
            removeMenuItem.setVisible(true);
            toolbar.setLogo(R.drawable.ic_keyboard_backspace_white_24dp);
//            toolbar.setTitle(getString(R.string.title_plans));
            toolbar.setBackgroundResource(R.color.colorSelect);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorSelectDark));
            }
        } else if (modeToStart == Mode.REMOVE) {
            editMenuItem.setVisible(false);
            removeMenuItem.setVisible(true);
            toolbar.setLogo(R.drawable.ic_keyboard_backspace_white_24dp);
//            toolbar.setTitle(getString(R.string.title_plans));
            toolbar.setBackgroundResource(R.color.colorSelect);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorSelectDark));
            }
        }
    }

    private Mode getModeBySelected(List<Plan> plans) {
        if (getSelectedPlanCount(plans) == 0) {
            startMode(Mode.NORMAL);
            toolbar.setTitle(getString(R.string.title_plans));
        } else {
            if (getSelectedPlanCount(plans) == 1) {
                startMode(Mode.EDIT);
            } else {
                startMode(Mode.REMOVE);
                toolbar.setTitle(String.valueOf(getSelectedPlanCount(plans)));
            }
        }

    }

    public void planSelect(View view, int position) {

        List<Plan> plans = planDao.getAll();
        Plan plan = plans.get(position);
        plan.setSelected(!plan.isSelected());
        planDao.save(plan);
        AvatarLetterView avatarLetterView = (AvatarLetterView) view.findViewById(R.id.avatarLetter);
        avatarLetterView.AnimationSwitchSelectedState();


//        toolbar.setLogo(R.drawable.ic_keyboard_backspace_white_24dp);
//        toolbar.setBackgroundResource(R.color.colorSelect);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        editMenuItem = menu.findItem(R.id.action_edit);
        removeMenuItem = menu.findItem(R.id.action_remove);

        startMode(Mode.NORMAL);

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
