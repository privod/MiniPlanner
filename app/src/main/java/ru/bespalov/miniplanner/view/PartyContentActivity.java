package ru.bespalov.miniplanner.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Party;

public class PartyContentActivity extends AppCompatActivity {

    ViewPagerAdapter adapter;
    Party party;

    TextView TotalCostByTextView;
    TextView sumOutTextView;
    TextView sumInTextView;
    TextView debtTextView;
    TextView debtLabelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        party = (Party) getIntent().getSerializableExtra(Party.class.getSimpleName());
        HelperFactory.getHelper().getPartyDao().refresh(party);
        HelperFactory.getHelper().getPlanDao().refresh(party.getPlan());

        setContentView(R.layout.activity_base);

        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinator);
        getLayoutInflater().inflate(R.layout.app_bar_toolbar_party_content, layout, true);
        getLayoutInflater().inflate(R.layout.widget_view_pager, layout, true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TotalCostByTextView = (TextView) findViewById(R.id.text_vew_total_sum_by);
        sumOutTextView = (TextView) findViewById(R.id.text_vew_sum_out);
        sumInTextView = (TextView) findViewById(R.id.text_vew_sum_in);
        debtTextView = (TextView) findViewById(R.id.text_vew_debt);
        debtLabelTextView = (TextView) findViewById(R.id.text_vew_label_debt);

        setTitle(party.getName());

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaseListFragment fragment = (BaseListFragment) adapter.getItem(viewPager.getCurrentItem());
                    fragment.newItemEdit();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshSubtitle();
    }

    public void refreshSubtitle() {
        TotalCostByTextView.setText(party.getBaysCostView());
        sumOutTextView.setText(party.getTotalSumOutView());
        sumInTextView.setText(party.getTotalSumInView());
        if (party.getBalance().signum() > 0) {
            debtLabelTextView.setText(R.string.label_overpay);
            debtTextView.setText(party.getOverpayView());
        } else {
            debtLabelTextView.setText(R.string.label_debt);
            debtTextView.setText(party.getDebtView());
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BayFragment(), getString(R.string.title_bays));
        adapter.addFragment(new ContributionsFragment(), getString(R.string.title_contributions));
        viewPager.setAdapter(adapter);
    }
}
