package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.edit.editoraction.EditorAction;

/**
 * Created by privod on 27.10.2015.
 */
public abstract class EditActivity<T extends Domain> extends AppCompatActivity {

    protected Class<T> tClass;
    protected Dao<T> dao;
    protected T entity;

    protected EditorAction doneAction;
    protected EditorAction goAction;

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

        doneAction = new EditorAction() {
            @Override
            public boolean action() {
                onOk();
                return true;
            }
        };

        goAction = new EditorAction() {
            @Override
            public boolean action() {
                // TODO РЕализовать обработку ошибок ввода
                return true;
            }
        };

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

        Intent intent = new Intent();
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
