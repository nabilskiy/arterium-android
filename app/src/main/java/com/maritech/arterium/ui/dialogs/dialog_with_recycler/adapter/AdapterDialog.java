//package com.maritech.arterium.ui.dialogs.dialog_with_recycler.adapter;
//
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.maritech.arterium.R;
//import com.maritech.arterium.ui.dialogs.dialog_with_recycler.data.DialogContent;
//
//import java.util.ArrayList;
//
//public class AdapterDialog extends RecyclerView.Adapter<AdapterDialog.ViewHolder> {
//
//    ArrayList<DialogContent> localDataSet;
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        final TextView tvName;
//        final TextView tvHint;
//        final ImageView ivBtnCheck;
//        final View viewLine;
//
//        public ViewHolder(View view) {
//            super(view);
//
//            tvName = view.findViewById(R.id.tvName);
//            tvHint = view.findViewById(R.id.tvHint);
//            ivBtnCheck = view.findViewById(R.id.ivBtnCheck);
//            viewLine = view.findViewById(R.id.viewLine);
//        }
//    }
//
//    public AdapterDialog(ArrayList<DialogContent> dataSet) {
//        localDataSet = dataSet;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.item_dialog_recycler, viewGroup, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//
//        viewHolder.ivBtnCheck.setActivated(localDataSet.get(position).isActive());
//        viewHolder.tvName.setText(localDataSet.get(position).getIdTittle());
//        viewHolder.tvName.setTextColor(localDataSet.get(position).getIdTextColor());
//        viewHolder.tvHint.setText(localDataSet.get(position).getIdHint());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return localDataSet.size();
//    }
//
//}
