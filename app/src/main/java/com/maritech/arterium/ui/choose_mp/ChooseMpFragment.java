package com.maritech.arterium.ui.choose_mp;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemChooseMpBinding;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;

import java.util.ArrayList;

public class ChooseMpFragment extends BaseFragment {

    private ChooseMpViewModel viewModel;
    ChooseMpNavigator navigator = new ChooseMpNavigator();

    View toolbar;
    TextView toolbarTittle;
    ImageView ivBack;
    EditText etSearch;
    TextView tvBtnAdd;

    private ArrayList<ChooseMpContent> listContent = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_mp, container, false);
        viewModel = new ViewModelProvider(this).get(ChooseMpViewModel.class);

        BaseAdapter adapter = new BaseAdapter(ItemChooseMpBinding.class, ChooseMpContent.class);
        RecyclerView rcv = (RecyclerView) root.findViewById(R.id.rvChoose);

        toolbar = root.findViewById(R.id.toolbar);
        toolbarTittle = toolbar.findViewById(R.id.tvToolbarTitle);
        etSearch = toolbar.findViewById(R.id.etSearch);
        ivBack = toolbar.findViewById(R.id.ivBack);
        tvBtnAdd = root.findViewById(R.id.tvBtnAdd);
        toolbarTittle.setText("Оберiть мед. представника");

            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigator.back();
                    //requireActivity().onBackPressed();
                }
            });

//        tvBtnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle result = new Bundle();
//                result.putString("bundleKey", "result");
//
//                getParentFragmentManager().setFragmentResult("chosenMp", result);
//                getParentFragment().getParentFragmentManager().
//            }
//        });

        rcv.setAdapter(adapter);
        prepareList(listContent);
        adapter.setDataList(listContent);


        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    private void prepareList(ArrayList<ChooseMpContent> dataList) {
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", 0, "+380994192021", "Київська обл."));
    }


}
