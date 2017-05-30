package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.edit.editoraction.OnEditorActionListener;

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
//        Dao<Bay> bayDao = HelperFactory.getHelper().getBayDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_party, layout, true);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);

        nameEditText.setText(entity.getName());

        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionListener(null, doneListener));
    }
}
