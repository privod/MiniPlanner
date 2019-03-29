package ru.bespalov.miniplanner.view.edit;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.math.BigDecimal;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Party;

public class PartyEditActivity extends EditActivity<Party> {

    private EditText nameEditText;
    private EditText shareEditText;

    public PartyEditActivity() {
        super(Party.class);
    }

    @Override
    public void changeEntity() {
        entity.setName(nameEditText.getText().toString());
        entity.setShare(new BigDecimal(shareEditText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPartyDao();

        getLayoutInflater().inflate(R.layout.edit_party, layout, true);

        TextInputLayout nameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        TextInputLayout shareInputLayout = (TextInputLayout) findViewById(R.id.input_layout_share);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        shareEditText = (EditText) findViewById(R.id.edit_text_share);

        nameEditText.setText(entity.getName());
        shareEditText.setText(entity.getShare().toPlainString());

        setFocus(nameEditText);
        editTextSetListeners(nameEditText, shareEditText, nameInputLayout);
        editTextSetListeners(shareEditText, null, shareInputLayout);
    }
}
