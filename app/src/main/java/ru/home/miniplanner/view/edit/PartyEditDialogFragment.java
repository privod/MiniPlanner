package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Party;

/**
 * Created by privod on 31.10.2015.
 */
public class PartyEditDialogFragment extends EditDialogFragment<Party> {

    EditText nameEditText;

    public PartyEditDialogFragment() {
        super(R.layout.activity_party_edit, R.string.action_party_edit, R.string.action_party_edit_mess);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setOnActionDoneListener(new OnActionDoneListener() {
            @Override
            public void onActionDone() {
                getEntity().setName(getViewService().textViewGetString(nameEditText));
            }
        });

        setEntity((Party) savedInstanceState.getSerializable("party"));
        if (null == getEntity()) {
            Log.e(LOG_TAG, "Entity can not be null, Use the setEntity() method in the constructor inherited class");
            return;
        }

        View view = this.getView();
        if (null == view) {
            Log.e(LOG_TAG, "View can not be null, maybe error inflate...");
            return;
        }

        nameEditText = (EditText)(view.findViewById(R.id.nameEditText));
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new EditTabBehavior(null, getOnActionDoneListener()));
        getViewService().textViewSetText(nameEditText, getEntity().getName());
    }
}
