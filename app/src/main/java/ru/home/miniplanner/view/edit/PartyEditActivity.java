package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.db.PartyDao;
import ru.home.miniplanner.service.BayDao;
import ru.home.miniplanner.view.adapter.BayAdapter;

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

        nameEditText = (EditText) findViewById(R.id.nameEditText);

        nameEditText.setText(entity.getName());

        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
