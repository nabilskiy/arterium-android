package com.maritech.arterium.ui.dashboard.doctor.levels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maritech.arterium.BuildConfig;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentProgramsContainerBinding;
import com.maritech.arterium.ui.dashboard.doctor.levels.adapter.LevelsContainerAdapter;
import java.util.ArrayList;
import java.util.List;

public class LevelsContainerDialog extends BottomSheetDialogFragment {

    private static final String DRUG_PROGRAMS_KEY = "levelsKey";
    List<DrugProgramModel> models = new ArrayList<>();

    private FragmentProgramsContainerBinding binding;
    private LevelsContainerAdapter containerAdapter;
    private final List<Integer> programIdList = new ArrayList<>();
    
    final int PROGRAM_RENIAL = 1;
    final int PROGRAM_GLIPTAR = 2;
    final int PROGRAM_SAGRADA = BuildConfig.DEBUG ? 4 : 3;;

    public static LevelsContainerDialog getInstance(ArrayList<DrugProgramModel> models) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DRUG_PROGRAMS_KEY, models);
        LevelsContainerDialog fragment = new LevelsContainerDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ChooseProgramDialog);

        if (getArguments() != null) {
            models = getArguments().getParcelableArrayList(DRUG_PROGRAMS_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_programs_container,
                container,
                false
        );

        initPrograms();

        initTabsClickListener();

        return binding.getRoot();
    }

    private void initTabsClickListener() {
        binding.ccTab.findViewById(R.id.tvOne).setOnClickListener(onClickListener);
        binding.ccTab.findViewById(R.id.tvTwo).setOnClickListener(onClickListener);
        binding.ccTab.findViewById(R.id.tvThree).setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = v -> {
        setTabActivate(v.getId());

    };

    private void initPrograms() {
        int selectedId = Pref.getInstance().getDrugProgramId(requireContext());

        int size = models.size();

        String name1 = "";
        String name2 = "";
        String name3 = "";
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                name1 = models.get(i).getTitle();
            }
            if (i == 1) {
                name2 = models.get(i).getTitle();
            }
            if (i == 2) {
                name3 = models.get(i).getTitle();
            }

            programIdList.add(models.get(i).getId());
        }
        binding.ccTab.setTabs(size, name1, name2, name3);

        if (selectedId == PROGRAM_RENIAL) {
            setTabActivate(R.id.tvOne);
        }
        if (selectedId == PROGRAM_GLIPTAR) {
            setTabActivate(R.id.tvTwo);
        }
        if (selectedId == PROGRAM_SAGRADA) {
            setTabActivate(R.id.tvThree);
        }

        containerAdapter = new LevelsContainerAdapter(requireActivity(), programIdList);
        binding.container.setAdapter(containerAdapter);
        binding.container.setUserInputEnabled(false);
    }

    private void setPagerPosition(int position) {
        binding.container.setCurrentItem(position);
    }

    private void setTabActivate(int viewId) {
        binding.ccTab.findViewById(R.id.tvOne).setActivated(false);
        binding.ccTab.findViewById(R.id.tvTwo).setActivated(false);
        binding.ccTab.findViewById(R.id.tvThree).setActivated(false);
        binding.ccTab.findViewById(viewId).setActivated(true);

        if (viewId == R.id.tvOne) {
            setPagerPosition(0);
        } else if (viewId == R.id.tvTwo) {
            setPagerPosition(1);
        } else {
            setPagerPosition(2);
        }
    }


}
