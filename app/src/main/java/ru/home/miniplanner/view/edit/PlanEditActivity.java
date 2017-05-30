package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.edit.editoraction.EditorAction;
import ru.home.miniplanner.view.edit.editoraction.NextEditorAction;
import ru.home.miniplanner.view.edit.editoraction.OnEditorActionListener;

public class PlanEditActivity extends EditActivity<Plan> {

    private EditText nameEditText;
    private EditText dateRegEditText;

    public PlanEditActivity() {
        super(Plan.class);
    }

    @Override
    public void changeEntity() {
        entity.setName(nameEditText.getText().toString());
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPlanDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);

        nameEditText.setText(entity.getName());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));

        nameEditText.requestFocus();
        nameEditText.selectAll();
//        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneAction));
//        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneAction));
        nameEditText.setOnEditorActionListener(new OnEditorActionListener(new NextEditorAction(dateRegEditText), doneAction, goAction));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionListener(null, doneAction, goAction));
    }
}
