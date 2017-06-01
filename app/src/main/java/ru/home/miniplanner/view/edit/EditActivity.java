package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.edit.listeners.OnEditorActionListener;
import ru.home.miniplanner.view.edit.listeners.OnEditorActionListenerNext;
import ru.home.miniplanner.view.edit.listeners.OnFocusChangeListener;

/**
 * Created by privod on 27.10.2015.
 */
public abstract class EditActivity<T extends Domain> extends AppCompatActivity {

    protected Class<T> tClass;
    protected Dao<T> dao;
    protected T entity;

    protected TextView.OnEditorActionListener doneListener;
    protected View.OnFocusChangeListener focusChangeListener;

    protected Button okButton;
    protected Button cancelButton;

    public abstract void changeEntity();                    // TODO change method name

    public EditActivity(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = this.getIntent();
        entity = tClass.cast(intent.getSerializableExtra(tClass.getSimpleName()));

        doneListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onOk();
                return false;
            }
        };

//        goAction = new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//
//                TextInputLayout layout = (TextInputLayout) textView.getParent();
//                if (textView.getText().length() < 1) {
//                    layout.setError("Not empty require");
//                } else {
//                    layout.setError("");
//                }
//
//                return false;
//            }
//        };

        okButton = (Button) findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOk();
            }
        });

        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    protected void onOk() {
        changeEntity();
        dao.save(entity);

//        Intent intent = new Intent();
//        intent.putExtra(entity.getClass().getSimpleName(), entity);
//        setResult(RESULT_OK, intent);
        setResult(RESULT_OK);
        finish();
    }

    protected void editTextSetListeners(EditText view, EditText nextView, final TextInputLayout inputLayout) {

        TextView.OnEditorActionListener nextListener = null;
        if (null != nextView) {
            nextListener = new OnEditorActionListenerNext(nextView);
        }
        view.setOnEditorActionListener(new OnEditorActionListener(nextListener, doneListener));

//        view.setOnFocusChangeListener(new OnFocusChangeListener(inputLayout) {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b && view instanceof EditText) {
//                    EditText editText = (EditText) view;
//                    if (editText.getText().length() < 1) {
//                        inputLayout.setError("Not empty require");   // TODO Hardcode!!! Move to resource.
//                        setFocus(editText);
//                    } else {
//                        inputLayout.setError("");
//                    }
//                }
//            }
//        });
        view.setOnFocusChangeListener(new OnFocusChangeListener(inputLayout) {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    validateEditText(inputLayout);
                }
            }
        });
    }

    protected boolean checkEditText(EditText view) {
        return view.getText().length() > 1;
    }

    protected boolean checkEditTextDate(EditText view) {
        return view.getText().toString().matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4}");
    }

    protected void validateEditText(TextInputLayout inputLayout) {
        EditText editText = inputLayout.getEditText();
        boolean shouldShowError;
        String err;
        if (editText.getInputType() == (InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE)) {
            shouldShowError = !checkEditTextDate(editText);
            err = getString(R.string.error_edit_text_date_format);
        } else {
            shouldShowError = !checkEditText(editText);
            err = getString(R.string.error_edit_text_empty);
        }

        if (shouldShowError) {
            inputLayout.setError(err);
//            setFocus(editText);
        } else {
            inputLayout.setError("");
        }
    }

    protected void setFocus(EditText view) {
        view.requestFocus();
        view.selectAll();
    }
}
