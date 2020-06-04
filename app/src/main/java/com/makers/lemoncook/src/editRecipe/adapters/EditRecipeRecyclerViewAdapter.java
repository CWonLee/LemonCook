package com.makers.lemoncook.src.editRecipe.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeRecyclerViewAdapterInterface;

import java.io.File;
import java.util.ArrayList;

public class EditRecipeRecyclerViewAdapter extends RecyclerView.Adapter<EditRecipeRecyclerViewAdapter.ViewHolder> implements EditRecipeRecyclerViewAdapterInterface {
    private ArrayList<Uri> mData;
    private EditRecipeActivityView mEditRecipeActivityView;
    private int mCurNum = 0;
    private Context mContext;

    public EditRecipeRecyclerViewAdapter(ArrayList<Uri> arrayList, EditRecipeActivityView editRecipeActivityView, Context context) {
        mData = arrayList;
        this.mEditRecipeActivityView = editRecipeActivityView;
        this.mContext = context;
        mEditRecipeActivityView.getInterface(this);
    }

    @NonNull
    @Override
    public EditRecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_edit_recipe_recycler_view, parent, false) ;
        EditRecipeRecyclerViewAdapter.ViewHolder vh = new EditRecipeRecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull EditRecipeRecyclerViewAdapter.ViewHolder holder, final int position) {
        //Glide.with(mContext).load(mData.get(position)).centerCrop().into(holder.mImageView);
        //Glide.with(mContext).load(new File(mData.get(position).getPath())).into(holder.mImageView);
        Glide.with(mContext).load(mData.get(position).toString()).into(holder.mImageView);
        holder.mImageView.setBackgroundResource(R.drawable.radius_2dp);
        if (position == mCurNum) {
            holder.mCl.setBackgroundResource(R.drawable.radius_2dp_lemon);
        }
        else {
            holder.mCl.setBackgroundResource(R.drawable.radius_2dp);
        }

        holder.mImageView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEditRecipeActivityView.change(position);

                mCurNum = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void changeCurNum(int num) {
        mCurNum = num;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        ConstraintLayout mCl;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_edit_recipe_rv_iv);
            mCl = itemView.findViewById(R.id.item_edit_recipe_cl);
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
