package ru.home.miniplanner.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;

public class PartyContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinator);
        getLayoutInflater().inflate(R.layout.app_bar_toolbar_tablaout, layout, true);
        getLayoutInflater().inflate(R.layout.view_pager, layout, true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startEditActivity(newEntityInstance());

                }
            });
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BaseListFragment<Contribution>(), Contribution.class.getSimpleName());
        adapter.addFragment(new BaseListFragment<Bay>(), Bay.class.getSimpleName());
        viewPager.setAdapter(adapter);
    }
}
