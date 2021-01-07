package com.maritech.arterium.ui.choose_doctor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;
import com.maritech.arterium.ui.choose_doctor.holder.ChooseDoctorAdapter;
import com.maritech.arterium.ui.choose_mp.ChooseMpNavigator;
import com.maritech.arterium.ui.choose_mp.ChooseMpViewModel;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;
import com.maritech.arterium.ui.choose_mp.holder.ChooseMpAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChooseDoctorFragment extends BaseFragment {

    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private ChooseDoctorViewModel viewModel;
    ChooseDoctorNavigator navigator = new ChooseDoctorNavigator();

    View toolbar;
    TextView toolbarTittle;
    ImageView ivBack;
    EditText etSearch;
    TextView tvBtnAdd;

    private ArrayList<ChooseDoctorContent> listContent = new ArrayList<>();
    private ChooseDoctorContent selectedObject;
    private ArrayList<ChooseDoctorContent> listSelectedObject = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_mp, container, false);
        viewModel = new ViewModelProvider(this).get(ChooseDoctorViewModel.class);

        ChooseDoctorAdapter adapter;
        RecyclerView rcv = (RecyclerView) root.findViewById(R.id.rvChoose);

        toolbar = root.findViewById(R.id.toolbar);
        toolbarTittle = toolbar.findViewById(R.id.tvToolbarTitle);
        etSearch = toolbar.findViewById(R.id.etSearch);
        ivBack = toolbar.findViewById(R.id.ivRight);
        tvBtnAdd = root.findViewById(R.id.tvBtnAdd);
        toolbarTittle.setText("Оберiть лiкарiв");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        prepareList(listContent);

        adapter = new ChooseDoctorAdapter(listContent, new ChooseDoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, ChooseDoctorContent object) {
                selectedObject = object;
                ArrayList<ChooseDoctorContent> list = new ArrayList<>(listSelectedObject);

                Log.e("!!!!!1", String.valueOf(listSelectedObject.size()));


                if (object.getSelected()) {
                    listSelectedObject.add(object);
                }




            }
        });
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);


        tvBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                ArrayList<ChooseDoctorContent> listTmp = new ArrayList<>(listSelectedObject);

                for(int i=0; i <listTmp.size(); i++ ){
                    if(!listTmp.get(i).getSelected()){
                        listSelectedObject.remove(i);
                    }
                }
                Set<ChooseDoctorContent> set = new HashSet<>(listSelectedObject);
                listSelectedObject.clear();
                listSelectedObject.addAll(set);

                result.putParcelableArrayList(BUNDLE_KEY, listSelectedObject);
                getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
                requireActivity().onBackPressed();
            }
        });


        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    private void prepareList(ArrayList<ChooseDoctorContent> dataList) {
        dataList.add(new ChooseDoctorContent("Евгений1 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений2 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений3 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений4 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений5 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений6 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений7 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений8 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений9 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений10 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений11 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений12 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений13 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений14 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений15 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
    }


}