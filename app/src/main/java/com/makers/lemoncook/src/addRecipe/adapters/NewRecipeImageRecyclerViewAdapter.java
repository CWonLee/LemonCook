package com.makers.lemoncook.src.addRecipe.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.addRecipe.interfaces.AddRecipeActivityView;

import java.io.File;
import java.util.ArrayList;

public class NewRecipeImageRecyclerViewAdapter extends RecyclerView.Adapter<NewRecipeImageRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Uri> mData;
    private AddRecipeActivityView mAddRecipeActivityView;
    private Context mContext;

    public NewRecipeImageRecyclerViewAdapter(ArrayList<Uri> arrayList, AddRecipeActivityView addRecipeActivityView, Context context) {
        mData = arrayList;
        this.mAddRecipeActivityView = addRecipeActivityView;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewRecipeImageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_new_recipe_image_recycler_view, parent, false) ;
        NewRecipeImageRecyclerViewAdapter.ViewHolder vh = new NewRecipeImageRecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull NewRecipeImageRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load(mData.get(position).toString()).centerCrop().into(holder.mImageView);
        //Glide.with(mContext).load(mData.get(position)).centerCrop().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_new_recipe_image_rv_iv);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mAddRecipeActivityView.removeImage(pos);
                }
            });
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
