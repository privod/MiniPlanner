package ru.bespalov.miniplanner.view.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.bespalov.miniplanner.R;

public class SaveFileDialog extends AlertDialog.Builder {

    public interface SaveDialogListener{
        public void OnInputFileName(String fileName);
    }
    private SaveDialogListener listener;

    EditText userInput;

    public SaveFileDialog(Context context) {
        super(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_file_save, null);

        userInput = (EditText) dialogView.findViewById(R.id.input_text);

        setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (listener != null) {
                    listener.OnInputFileName(userInput.getText().toString());
                }
            }
        });
        setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        setView(dialogView);
        setCancelable(false);
    }

    public SaveFileDialog setOpenDialogListener(SaveDialogListener listener) {
        this.listener = listener;
        return this;
    }

    public SaveFileDialog setInputText(String text) {
        userInput.setText(text);
        return this;
    }
}
