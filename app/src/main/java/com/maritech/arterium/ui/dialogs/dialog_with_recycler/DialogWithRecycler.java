package com.maritech.arterium.ui.dialogs.dialog_with_recycler;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.adapter.AdapterDialog;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.data.DialogContent;

import java.util.ArrayList;

public class DialogWithRecycler extends Dialog {

    private static final String NAME = "ThemeColors", KEY = "ThemeColors";

    private OnChooseItem onChooseItem;

    TextView btnClose;
    Toast toast;
    private String data;
    private ArrayList<DialogContent> listContent = new ArrayList<>();

    public DialogWithRecycler(Context context, String data) {
        super(context, R.style.ChooseProgramDialog);
        this.data = data;
        initView(context);
    }

    private void initView(Context context) {
        setContentView(R.layout.dialog_with_recycler);

        AdapterDialog adapter;
        RecyclerView rcv = (RecyclerView) findViewById(R.id.rvStyle);
        prepareList(listContent);

        adapter = new AdapterDialog(listContent, (position, object, tittle) -> {

            for(int i=0; i<listContent.size(); i++){
                listContent.get(i).setActive(false);
            }
            object.setActive(true);


            if(onChooseItem != null) {
                onChooseItem.onChoose(position);
            }


            dismiss();

        });
        rcv.setAdapter(adapter);


        btnClose = ((TextView) findViewById(R.id.tvBack));


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.y = 50;

        getWindow().setAttributes(attributes);
    }
    public void setListener(OnChooseItem onChooseItem) {
        this.onChooseItem = onChooseItem;
    }

    private void prepareList(ArrayList<DialogContent> dataList) {
        dataList.add(new DialogContent(R.string.renial_key_life, R.string.need_more_buy, true, R.color.purple));
        dataList.add(new DialogContent(R.string.gliptar_key_life, R.string.need_more_buy, false, R.color.blue));
        dataList.add(new DialogContent(R.string.sagrada_key_life, R.string.need_more_buy, false, R.color.red));
    }

    public interface OnChooseItem {

        void onChoose(int content);

    }

}
