package com.makers.lemoncook.src.myPage.adpaters;

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
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;
import com.makers.lemoncook.src.recipe.RecipeActivity;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mImageView.setClipToOutline(true);
        holder.mImageView.setBackgroundResource(R.drawable.round_image_view);
        Glide.with(holder.itemView.getContext()).load(mData.get(position).getRecipeImage()).into(holder.mImageView);
        holder.mTvTitle.setText(mData.get(position).getRecipeTilte());
        holder.mTvName.setText(mData.get(position).getRecipeName());
        holder.mTvHashTag.setText(mData.get(position).getRecipeHashTag());
        holder.mTvDate.setText(mData.get(position).getRecipeCreatedAt());
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
        TextView mTvTitle, mTvName, mTvHashTag, mTvDate;
        ConstraintLayout mClItem;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_my_page_iv_main);
            mTvTitle = itemView.findViewById(R.id.item_my_page_tv_title);
            mTvName = itemView.findViewById(R.id.item_my_page_tv_name);
            mTvHashTag = itemView.findViewById(R.id.item_my_page_tv_hash_tag);
            mTvDate = itemView.findViewById(R.id.item_my_page_tv_date);
            mClItem = itemView.findViewById(R.id.item_my_page_cl_item);
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
