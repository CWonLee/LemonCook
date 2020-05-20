package com.makers.lemoncook.src.recipeList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import java.util.ArrayList;

public class RecipeListRecyclerViewAdapter extends RecyclerView.Adapter<RecipeListRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ResponseGetRecipe.Result> mData;

    public RecipeListRecyclerViewAdapter(ArrayList<ResponseGetRecipe.Result> arrayList) {
        mData = arrayList;
    }

    @NonNull
    @Override
    public RecipeListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_recipe_list_recycler_view, parent, false) ;
        RecipeListRecyclerViewAdapter.ViewHolder vh = new RecipeListRecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right,holder.mSwipeLayout.findViewWithTag("Right"));
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Left,holder.mSwipeLayout.findViewWithTag("Left"));

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
        SwipeLayout mSwipeLayout;
        TextView mTvTitle, mTvName, mTvHashTag, mTvDate;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_recipe_list_iv_main);
            mSwipeLayout = itemView.findViewById(R.id.item_recipe_list_sl);
            mTvTitle = itemView.findViewById(R.id.item_recipe_list_tv_title);
            mTvName = itemView.findViewById(R.id.item_recipe_list_tv_name);
            mTvHashTag = itemView.findViewById(R.id.item_recipe_list_tv_hash_tag);
            mTvDate = itemView.findViewById(R.id.item_recipe_list_tv_date);
        }
    }
}
