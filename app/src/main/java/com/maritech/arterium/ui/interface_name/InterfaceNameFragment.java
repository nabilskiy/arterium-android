package com.maritech.arterium.ui.interface_name;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.maritech.arterium.R;

public class InterfaceNameFragment extends Fragment {

    ConstraintLayout constraintLayout;
    TextView ukrainian;
    TextView russian;
    TextView english;
    CheckBox cbUkrainian;
    CheckBox cbRussian;
    CheckBox cbEnglish;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_interface_name, container, false);

        constraintLayout = root.findViewById(R.id.clNamesList);
        ukrainian = root.findViewById(R.id.ukrainian).findViewById(R.id.tvInterfaceName);
        russian = root.findViewById(R.id.russian).findViewById(R.id.tvInterfaceName);
        english = root.findViewById(R.id.english).findViewById(R.id.tvInterfaceName);

        russian.setText("Росiйська");
        english.setText("Англiйська");

        cbUkrainian = root.findViewById(R.id.ukrainian).findViewById(R.id.cbShowHide);
        cbRussian = root.findViewById(R.id.russian).findViewById(R.id.cbShowHide);
        cbEnglish = root.findViewById(R.id.english).findViewById(R.id.cbShowHide);

        setCheckboxStatus(cbUkrainian);
        changeInterfaceName();
        return root;
    }

    public void setCheckboxStatus(CheckBox view){
        for (int i=0; i<constraintLayout.getChildCount(); i++){
            CheckBox checkBox = constraintLayout.getChildAt(i).findViewById(R.id.cbShowHide);
            if (view.isChecked() && view == checkBox){
                constraintLayout.getChildAt(i).findViewById(R.id.cbShowHide).setVisibility(View.VISIBLE);
            } else{
                constraintLayout.getChildAt(i).findViewById(R.id.cbShowHide).setVisibility(View.INVISIBLE);
                checkBox.setChecked(false);
            }
        }
    }

    public void changeInterfaceName(){
        for (int i=0; i<constraintLayout.getChildCount(); i++){
            constraintLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                CheckBox checkBox = view.findViewById(R.id.cbShowHide);
                checkBox.setChecked(true);
                setCheckboxStatus(checkBox);
                }
            });

        }
    }
}