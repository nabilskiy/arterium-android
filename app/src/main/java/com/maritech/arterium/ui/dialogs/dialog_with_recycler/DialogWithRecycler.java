package com.maritech.arterium.ui.dialogs.dialog_with_recycler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemDialogRecyclerBinding;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.data.DialogContent;

import java.util.ArrayList;

public class DialogWithRecycler extends DialogFragment {


    ArrayList<DialogContent> dataList;
    RecyclerView mRecyclerView;
    BaseAdapter mAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_with_recycler, null);
        builder.setView(view);

       // dataList = new ArrayList<>();
        //prepareList(dataList);
        //mAdapter = new AdapterDialog(dataList);




       // mAdapter = new BaseAdapter(ItemDialogRecyclerBinding.class, DialogContent.class);



        return builder.create();
//
//        FragmentActivity activity = getActivity();
//
//        ItemDialogRecyclerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
//                R.layout.dialog_with_recycler, null, false);
//        dataList = new ArrayList<>();
//        prepareList(dataList);
//
//       // binding.setData();
//
//        return new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_Dialog)
//                .setView(binding.getRoot())
//                .create();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.dialog_with_recycler, container, false);
//
//        adapter = new BaseAdapter(ItemDialogRecyclerBinding.class, DialogContent.class);
//
////        mRecyclerView = view.findViewById(R.id.recyclerView);
////        mRecyclerView.setAdapter(mAdapter);
//
//        RecyclerView rcv = (RecyclerView) view.findViewById(R.id.recyclerView);
//
//        rcv.setAdapter(adapter);
//
//        adapter.setDataList(dataList);
//
//        return view;
//    }

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
