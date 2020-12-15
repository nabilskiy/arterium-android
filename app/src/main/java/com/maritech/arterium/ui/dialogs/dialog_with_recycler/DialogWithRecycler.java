package com.maritech.arterium.ui.dialogs.dialog_with_recycler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.adapter.AdapterDialog;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.data.DialogContent;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class DialogWithRecycler extends DialogFragment {


    ArrayList<DialogContent> dataList;
    RecyclerView mRecyclerView;
    AdapterDialog mAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_with_recycler, null);
        builder.setView(view);

        dataList = new ArrayList<>();
        prepareList(dataList);
        mAdapter = new AdapterDialog(dataList);

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_with_recycler, container, false);



        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void prepareList(ArrayList<DialogContent> dataList) {
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, true, R.color.purple));
        dataList.add(new DialogContent(R.string.ukrainian, R.string.ukrainian, false, R.color.black));

    }


}
