package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 27.10.2015.
 */
public abstract class EditActivity<T extends Domain> extends AppCompatActivity {
    static final String LOG_TAG = EditActivity.class.getSimpleName();
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    protected ViewService viewService;
    protected T entity;
    protected OnEditorActionDoneListener doneListener;

    protected Button okButton;
    protected Button cancelButton;

    abstract protected @LayoutRes int getLayoutResID();
    abstract protected T getEntity();
//    abstract protected EditText[] getArrayEdits();
//    abstract protected void editResultOk();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        viewService = new ViewService();

        entity = getEntity();
        if (null == entity) {
            setResult(RESULT_CANCELED);
            finish();
        }

//        setEditsTabBehavior(getArrayEdits());

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

//    private void setEditsTabBehavior(EditText[] arrayViews) {
//        EditText firstView = arrayViews[0];
//        EditText lastView = arrayViews[arrayViews.length - 1];
//
//        firstView.requestFocus();
//
//        for (int i = 0; i < arrayViews.length - 1; i++) {
//            EditText curView = arrayViews[i];
//            final EditText nextView = arrayViews[i + 1];
//
//            curView.selectAll();
//            curView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                        nextView.requestFocus();
//                        nextView.selectAll();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        }
//
//        lastView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    editResultOk();
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
}
