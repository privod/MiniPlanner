package ru.home.miniplanner.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.adapter.BaseAdapter;


public class BaseListFragment  <T extends Domain> extends Fragment {
    private static final String ARG_EDIT_ACTIVITY_CLASS = "edit_activity_class";

    protected Class<? extends Activity> editActivityClass;
    private Class<T> entityClass;
    private Class<? extends Activity> insideActivityClass;

    protected BaseAdapter<? extends BaseAdapter.ViewHolder, T> adapter;
    protected Dao<T> dao;
    protected RecyclerView recyclerView;
    protected int request_code_edit;

    private OnFragmentInteractionListener mListener;    // TODO Возможно достаточно класса Context

    public BaseListFragment() {
        // Required empty public constructor
    }

    public static BaseListFragment newInstance(Class<? extends Activity> editActivityClass) {
        BaseListFragment fragment = new BaseListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EDIT_ACTIVITY_CLASS, editActivityClass);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            editActivityClass = (Class<? extends Activity>) getArguments().getSerializable(ARG_EDIT_ACTIVITY_CLASS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (null != recyclerView) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((Context) mListener);
            recyclerView.setLayoutManager(layoutManager);
        }

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected Intent getEditActivityIntent(T entity) {
        Intent intent = new Intent((Context) mListener, editActivityClass);
        intent.putExtra(entity.getClass().getSimpleName(), entity);
        return  intent;
    }

    protected void startEditActivity(T entity) {
        Intent intent = getEditActivityIntent(entity);
        startActivityForResult(intent, request_code_edit);
    }
}
