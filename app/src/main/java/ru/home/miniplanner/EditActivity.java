package ru.home.miniplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.ParseException;

import ru.home.miniplanner.domain.Plan;
import ru.home.miniplanner.domain.SimpleCalendar;

public class EditActivity extends AppCompatActivity {

    final String LOG_TAG = "EditActivity";

    EditText nameEditText;
    EditText dateRegEditText;
    EditText costExpectEditText;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
        costExpectEditText = (EditText) findViewById(R.id.costExpectEditText);
        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan plan = new Plan();
                plan.setName(nameEditText.getText().toString());
                try {
                    plan.setDateReg(new SimpleCalendar(dateRegEditText.getText().toString()));
                } catch (ParseException e) {
                    Log.e(LOG_TAG, e.getMessage());
                    // TODO make error message on screen
                    return;
                }
                plan.setCostExpect(new BigDecimal(costExpectEditText.getText().toString()));

                Intent intent = new Intent();
                intent.putExtra("plan", plan);
                setResult(RESULT_OK, intent);
            }
        });
    }
}
