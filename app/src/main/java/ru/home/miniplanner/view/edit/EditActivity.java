package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.model.Domain;

/**
 * Created by privod on 27.10.2015.
 */
public abstract class EditActivity<T extends Domain> extends AppCompatActivity {

    protected Class<T> tClass;
    protected Dao<T> dao;
    protected T entity;

    protected OnEditorActionDoneListener doneListener;

    protected Button okButton;
    protected Button cancelButton;

    public abstract void changeEntity();                    // TODO change method name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = this.getIntent();
        entity = tClass.cast(intent.getSerializableExtra(tClass.getSimpleName()));

        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                changeEntity();
                dao.save(entity);

                Intent intent = new Intent();
                intent.putExtra(entity.getClass().getSimpleName(), entity);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        okButton = (Button) findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneListener.onActionDone();
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
}
