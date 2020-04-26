package com.makers.lemoncook.src.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makers.lemoncook.R;

import java.util.ArrayList;

public class MainRecyclerViewAdapterMy extends RecyclerView.Adapter<MainRecyclerViewAdapterMy.ViewHolder> {
    private ArrayList<Integer> mImageArraylist;
    private ArrayList<Integer> mTextArraylist;

    public MainRecyclerViewAdapterMy() {
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mText;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.iv_main_recycler_view_item);
            mText = itemView.findViewById(R.id.tv_main_recycler_view_item);
        }
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapterMy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_main_recycler, parent, false) ;
        double height = parent.getMeasuredWidth() / 3.75;
        view.setMinimumHeight((int)height);
        view.setMinimumWidth((int)height);
        MainRecyclerViewAdapterMy.ViewHolder vh = new MainRecyclerViewAdapterMy.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapterMy.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

}
