package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 27.10.2015.
 */
public abstract class EditActivity<T extends Domain> extends AppCompatActivity {
    static final String LOG_TAG = EditActivity.class.getSimpleName();
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    private ViewService viewService;
//    private T entity;

    protected OnEditorActionDoneListener doneListener;

    protected Button okButton;
    protected Button cancelButton;

//    private final String extraName;
    @LayoutRes private final int layoutResID;
//    abstract protected EditText[] getArrayEdits();
//    abstract protected void editResultOk();

    public EditActivity(int layoutResID) {
//        this.extraName = extraName;
        this.layoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        viewService = new ViewService(this);

//        entity = getEntityFromIntent();
//        if (null == entity) {
//            setResult(RESULT_CANCELED);
//            finish();
//        }

        okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneListener.onActionDone();
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

//    public String getExtraName() {
//        return extraName;
//    }

    public int getLayoutResID() {
        return layoutResID;
    }

    public ViewService getViewService() {
        return viewService;
    }
}
