package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Party;

public class PartyEditActivity extends EditActivity<Party> {
    static final String LOG_TAG = PartyEditActivity.class.getSimpleName();

    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                entity.setName(viewService.textViewGetString(nameEditText));

                Intent intent = new Intent();
                intent.putExtra("" +
                        "party", entity);
                setResult(RESULT_OK, intent);
                finish();            }
        };

        nameEditText = (EditText) findViewById(R.id.nameEditText);

        viewService.textViewSetText(nameEditText, entity.getName());

        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
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

//    @Override
//    protected EditText[] getArrayEdits() {
//        nameEditText = (EditText) findViewById(R.id.nameEditText);
////        depositEditText = (EditText) findViewById(R.id.depositEditText);
//
//        return new EditText[] {nameEditText, depositEditText};
//    }
//
//    @Override
//    protected void editResultOk() {
//        entity.setName(viewService.textViewGetString(nameEditText));
//        entity.setDeposit(viewService.textViewGetDecimal(depositEditText));
//
//        Intent intent = new Intent();
//        intent.putExtra("plan", entity);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
}
