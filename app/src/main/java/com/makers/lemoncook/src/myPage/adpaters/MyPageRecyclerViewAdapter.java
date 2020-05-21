package com.makers.lemoncook.src.myPage.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;

import java.util.ArrayList;

public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<MyPageRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ResponseGetMyPage.Result.RecipeInfo> mData;
    private Context mContext;

    public MyPageRecyclerViewAdapter(ArrayList<ResponseGetMyPage.Result.RecipeInfo> arrayList, Context context) {
        this.mData = arrayList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyPageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_my_page_recycler_view, parent, false) ;
        MyPageRecyclerViewAdapter.ViewHolder vh = new MyPageRecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageView.setClipToOutline(true);
        holder.mImageView.setBackgroundResource(R.drawable.round_image_view);
        Glide.with(holder.itemView.getContext()).load(mData.get(position).getRecipeImage()).into(holder.mImageView);
        holder.mTvTitle.setText(mData.get(position).getRecipeTilte());
        holder.mTvName.setText(mData.get(position).getRecipeName());
        holder.mTvHashTag.setText(mData.get(position).getRecipeHashTag());
        holder.mTvDate.setText(mData.get(position).getRecipeCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTvTitle, mTvName, mTvHashTag, mTvDate;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_my_page_iv_main);
            mTvTitle = itemView.findViewById(R.id.item_my_page_tv_title);
            mTvName = itemView.findViewById(R.id.item_my_page_tv_name);
            mTvHashTag = itemView.findViewById(R.id.item_my_page_tv_hash_tag);
            mTvDate = itemView.findViewById(R.id.item_my_page_tv_date);
        }
    }
}
