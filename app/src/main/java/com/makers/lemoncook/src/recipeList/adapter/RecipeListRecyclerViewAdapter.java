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

import java.util.ArrayList;

public class RecipeListRecyclerViewAdapter extends RecyclerView.Adapter<RecipeListRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> mUrlData;

    public RecipeListRecyclerViewAdapter(ArrayList<String> arrayList) {
        mUrlData = arrayList;
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
        Glide.with(holder.itemView.getContext()).load(mUrlData.get(position)).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mUrlData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        SwipeLayout mSwipeLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_recipe_list_iv_main);
            mSwipeLayout = itemView.findViewById(R.id.item_recipe_list_sl);
        }
    }
}
