package com.makers.lemoncook.src.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.recipeList.RecipeListActivity;

import java.util.ArrayList;

public class MainRecyclerViewAdapterMy extends RecyclerView.Adapter<MainRecyclerViewAdapterMy.ViewHolder> {
    private ArrayList<Integer> mImageArraylist;
    private ArrayList<String> mTextArraylist;
    private Context mContext;

    public MainRecyclerViewAdapterMy(ArrayList<Integer> imageArraylist, ArrayList<String> textArraylist, Context context) {
        this.mImageArraylist = imageArraylist;
        this.mTextArraylist = textArraylist;
        this.mContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mText;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.iv_main_recycler_view_item);
            mText = itemView.findViewById(R.id.tv_main_recycler_view_item);

            itemView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(mContext, RecipeListActivity.class);
                    intent.putExtra("filter", "My Recipe");
                    switch (pos) {
                        case 0:{
                            intent.putExtra("category", "한식");
                            break;
                        }
                        case 1:{
                            intent.putExtra("category", "중식");
                            break;
                        }
                        case 2:{
                            intent.putExtra("category", "일식");
                            break;
                        }
                        case 3:{
                            intent.putExtra("category", "양식");
                            break;
                        }
                        case 4:{
                            intent.putExtra("category", "디저트");
                            break;
                        }
                        case 5:{
                            intent.putExtra("category", "기타");
                            break;
                        }
                        default:
                            break;
                    }
                    mContext.startActivity(intent);
                }
            });
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
        MainRecyclerViewAdapterMy.ViewHolder vh = new MainRecyclerViewAdapterMy.ViewHolder(view);

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapterMy.ViewHolder holder, int position) {
        holder.mImage.setImageResource(mImageArraylist.get(position));
        holder.mText.setText(mTextArraylist.get(position));


    }

    @Override
    public int getItemCount() {
        return 6;
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
