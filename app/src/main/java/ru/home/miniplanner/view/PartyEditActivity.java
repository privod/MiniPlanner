package ru.home.miniplanner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;

public class PartyEditActivity extends EditActivity<Party> {
    static final String LOG_TAG = PartyEditActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText depositEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_edit);

        viewService.textViewSetText(nameEditText, entity.getName());
        viewService.textViewSetText(depositEditText, entity.getDeposit());
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_party_edit;
    }

    @Override
    protected Party getEntity() {
        Intent intent = getIntent();
        return (Party) intent.getSerializableExtra("party");
    }

    @Override
    protected EditText[] getArrayEdits() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
//        depositEditText = (EditText) findViewById(R.id.depositEditText);

        return new EditText[] {nameEditText, depositEditText};
    }

    @Override
    protected void editResultOk() {
        entity.setName(viewService.textViewGetString(nameEditText));
        entity.setDeposit(viewService.textViewGetDecimal(depositEditText));

        Intent intent = new Intent();
        intent.putExtra("plan", entity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
