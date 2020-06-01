package com.makers.lemoncook.src.recipe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makers.lemoncook.R;

import java.util.ArrayList;

public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.ViewHolder>  {
    private ArrayList<String> mData = new ArrayList<>();

    public MaterialRecyclerViewAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public MaterialRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_material_fm_recycler, parent, false) ;
        MaterialRecyclerViewAdapter.ViewHolder vh = new MaterialRecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_material_tv);
        }
    }

}
