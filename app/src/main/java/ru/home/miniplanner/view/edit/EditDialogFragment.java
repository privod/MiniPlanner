package ru.home.miniplanner.view.edit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 31.10.2015.
 */
public abstract class EditDialogFragment<T extends Domain> extends DialogFragment implements DialogInterface.OnClickListener {
    static final String LOG_TAG = PlanEditActivity.class.getSimpleName();
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    protected ViewService viewService;
    protected T entity;
    View view;
    protected
    @LayoutRes
    int layoutResID;
    protected
    @LayoutRes
    int titleResID;
    protected
    @LayoutRes
    int messageResID;
    OnEditorActionDoneListener onActionDoneListener;


    protected Button okButton;

    protected Button cancelButton;

    public EditDialogFragment(int layoutResID, int titleResID, int messageResID) {
        this.viewService = new ViewService();
        this.layoutResID = layoutResID;
        this.titleResID = titleResID;
        this.messageResID = messageResID;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getTitleResID());
        builder.setMessage(getMessageResID());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(getLayoutResID(), null);
        builder.setView(view);

        builder.setPositiveButton(R.string.ok, this);
        builder.setNegativeButton(R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                getOnActionDoneListener().onActionDone();
                break;
            case Dialog.BUTTON_NEGATIVE:
                dismiss();
                break;
        }
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void setOnActionDoneListener(OnEditorActionDoneListener l) {
        this.onActionDoneListener = l;
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public ViewService getViewService() {
        return viewService;
    }

    public T getEntity() {
        return entity;
    }

    public int getLayoutResID() {
        return layoutResID;
    }

    public int getMessageResID() {
        return messageResID;
    }

    public int getTitleResID() {
        return titleResID;
    }

    public OnEditorActionDoneListener getOnActionDoneListener() {
        return onActionDoneListener;
    }
}
