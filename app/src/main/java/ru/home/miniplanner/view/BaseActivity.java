package ru.home.miniplanner.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Domain;

/**
 * Created by Леонид on 25.02.2017.
 */
public abstract class BaseActivity <T extends Domain>  extends AppCompatActivity {
    protected final Class<? extends Activity> editActivityClass;

    protected Dao<T> dao;
    //    protected BaseAdapter<? extends BaseAdapter.ViewHolder, T> adapter;
    protected int request_code_edit;

    public BaseActivity(Class<? extends Activity> editActivityClass) {
        this.editActivityClass = editActivityClass;
    }

    protected abstract T newEntityInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        if (null != recyclerView) {
////            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
////            recyclerView.setHasFixedSize(true);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(layoutManager);
////            recyclerView.setItemAnimator(new SlideInLeftAnimator());
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startEditActivity(newEntityInstance());

                }
            });
        }
    }

    protected Intent getEditActivityIntent(T entity) {
        Intent intent = new Intent(BaseActivity.this, editActivityClass);
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        return  intent;
    }

    protected void startEditActivity(T entity) {
        Intent intent = getEditActivityIntent(entity);
        startActivityForResult(intent, request_code_edit);
    }
}
