package ru.bespalov.miniplanner.view.edit;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Party;

public class PartyEditActivity extends EditActivity<Party> {

    private EditText nameEditText;

    public PartyEditActivity() {
        super(Party.class);
    }

    @Override
    public void changeEntity() {
        entity.setName(nameEditText.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPartyDao();

        getLayoutInflater().inflate(R.layout.edit_party, layout, true);

        TextInputLayout nameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_name);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);

        nameEditText.setText(entity.getName());

        nameEditText.requestFocus();
        nameEditText.selectAll();
        editTextSetListeners(nameEditText, null, nameInputLayout);
    }
}
