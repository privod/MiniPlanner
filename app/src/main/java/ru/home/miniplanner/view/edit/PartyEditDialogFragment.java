package ru.home.miniplanner.view.edit;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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

        setOnActionDoneListener(new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                getEntity().setName(getViewService().textViewGetString(nameEditText));
            }
        });

        setEntity((Party) getArguments().getSerializable("party"));
        if (null == getEntity()) {
            Log.e(LOG_TAG, "Entity can not be null, Use the setEntity() method in the constructor inherited class");
            return;
        }

        Dialog dialog = getDialog();
        if (null == dialog) {
            Log.e(LOG_TAG, "View can not be null, maybe error inflate...");
            return;
        }



        nameEditText = (EditText)(view.findViewById(R.id.nameEditText));
        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, getOnActionDoneListener()));
        getViewService().textViewSetText(nameEditText, getEntity().getName());
    }
}
