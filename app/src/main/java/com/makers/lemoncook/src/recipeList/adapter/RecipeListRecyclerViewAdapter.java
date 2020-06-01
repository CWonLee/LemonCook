package com.makers.lemoncook.src.recipeList.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.recipe.RecipeActivity;
import com.makers.lemoncook.src.recipeList.interfaces.RecipeListActivityView;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import java.util.ArrayList;

public class RecipeListRecyclerViewAdapter extends RecyclerView.Adapter<RecipeListRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ResponseGetRecipe.Result> mData;
    private ArrayList<Integer> mZZim;
    private RecipeListActivityView mRecipeListActivityView;
    private Context mContext;

    public RecipeListRecyclerViewAdapter(ArrayList<ResponseGetRecipe.Result> arrayList, RecipeListActivityView recipeListActivityView, Context context, ArrayList<Integer> zzim) {
        this.mData = arrayList;
        this.mRecipeListActivityView = recipeListActivityView;
        this.mContext = context;
        this.mZZim = zzim;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right,holder.mSwipeLayout.findViewWithTag("Right"));
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Left,holder.mSwipeLayout.findViewWithTag("Left"));
        if (mZZim.get(position) == 0) {
            holder.mClForward.setBackgroundResource(R.drawable.round_swipe_right_background_gray);
            holder.mBackView.setBackgroundResource(R.drawable.round_swipe_right_background_gray);
        }
        else {
            holder.mClForward.setBackgroundResource(R.drawable.round_swipe_right_background);
            holder.mBackView.setBackgroundResource(R.drawable.round_swipe_right_background);
        }
        holder.mImageView.setClipToOutline(true);
        holder.mImageView.setBackgroundResource(R.drawable.round_image_view);
        Glide.with(holder.itemView.getContext()).load(mData.get(position).getRecipeImage()).into(holder.mImageView);
        holder.mTvTitle.setText(mData.get(position).getRecipeTilte());
        holder.mTvName.setText(mData.get(position).getRecipeName());
        holder.mTvHashTag.setText(mData.get(position).getRecipeHashTag());
        holder.mTvDate.setText(mData.get(position).getRecipeCreatedAt());

        holder.mClForward.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mZZim.get(position) == 0) {
                    mRecipeListActivityView.postZZim(mData.get(position).getRecipeNo(), position);
                }
                else {
                    mRecipeListActivityView.deleteZZim(mData.get(position).getRecipeNo(), position);
                }
            }
        });
        holder.mClDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mRecipeListActivityView.deleteRecipe(mData.get(position).getRecipeNo(), position);
            }
        });
        holder.mClItem.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("recipeNo", mData.get(position).getRecipeNo());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        SwipeLayout mSwipeLayout;
        TextView mTvTitle, mTvName, mTvHashTag, mTvDate;
        ConstraintLayout mClDelete, mClItem, mClForward;
        View mBackView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_recipe_list_iv_main);
            mSwipeLayout = itemView.findViewById(R.id.item_recipe_list_sl);
            mTvTitle = itemView.findViewById(R.id.item_recipe_list_tv_title);
            mTvName = itemView.findViewById(R.id.item_recipe_list_tv_name);
            mTvHashTag = itemView.findViewById(R.id.item_recipe_list_tv_hash_tag);
            mTvDate = itemView.findViewById(R.id.item_recipe_list_tv_date);
            mClDelete = itemView.findViewById(R.id.item_recipe_list_cl_delete);
            mClItem = itemView.findViewById(R.id.item_recipe_cl_item);
            mClForward = itemView.findViewById(R.id.item_recipe_list_forward_swipe);
            mBackView = itemView.findViewById(R.id.item_recipe_list_back_swipe);
        }
    }

    public abstract class OnSingleClickListener implements View.OnClickListener {
        //중복클릭시간차이
        private static final long MIN_CLICK_INTERVAL=600;

        //마지막으로 클릭한 시간
        private long mLastClickTime;

        public abstract void onSingleClick(View v);

        @Override
        public final void onClick(View v) {
            //현재 클릭한 시간
            long currentClickTime= SystemClock.uptimeMillis();
            //이전에 클릭한 시간과 현재시간의 차이
            long elapsedTime=currentClickTime-mLastClickTime;
            //마지막클릭시간 업데이트
            mLastClickTime=currentClickTime;

            //내가 정한 중복클릭시간 차이를 안넘었으면 클릭이벤트 발생못하게 return
            if(elapsedTime<=MIN_CLICK_INTERVAL)
                return;
            //중복클릭시간 아니면 이벤트 발생
            onSingleClick(v);
        }
    }
}
