package com.makers.lemoncook.src.editRecipe.adapters;

import android.content.Context;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.models.EditRecipeViewpagerItem;

import java.util.ArrayList;

public class EditRecipeViewPagerAdapter extends PagerAdapter {
    private Context mContext = null ;
    private EditRecipeActivityView mEditRecipeActivityView;
    private ArrayList<EditRecipeViewpagerItem> editRecipeViewpagerItems = new ArrayList<>();

    public EditRecipeViewPagerAdapter() {
    }
    public EditRecipeViewPagerAdapter(Context context, EditRecipeActivityView editRecipeActivityView) {
        mEditRecipeActivityView = editRecipeActivityView;
        mContext = context ;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null ;
        if (mContext != null) {
            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);
        }
        final ImageView mImageView = view.findViewById(R.id.fm_edit_recipe_iv);
        final TextView mTvNumber = view.findViewById(R.id.fm_edit_recipe_tv_img_num);
        final EditText mEtContent = view.findViewById(R.id.fm_edit_recipe_et_content);
        final ImageView mIvDelete = view.findViewById(R.id.fm_edit_recipe_iv_delete_btn);
        final EditRecipeViewpagerItem editRecipeViewpagerItem = editRecipeViewpagerItems.get(position);
        mEtContent.setText(editRecipeViewpagerItem.getmContent());
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editRecipeViewpagerItem.setmContent(mEtContent.getText().toString());
            }
        });
//        Log.e("editRecipeViewpagerItem",editRecipeViewpagerItem.getmContent());
        Glide.with(mContext).load(editRecipeViewpagerItem.getmUri().toString()).into(mImageView);
        //Glide.with(getContext()).load(new File(mUri.getPath())).into(mImageView);
        mTvNumber.setText(String.valueOf(editRecipeViewpagerItem.getmNumber()));

        mIvDelete.setOnClickListener(new EditRecipeViewPagerAdapter.OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEditRecipeActivityView.deleteImg();
            }
        });
        // 뷰페이저에 추가.
        container.addView(view) ;
        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return editRecipeViewpagerItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
    public boolean isContentValid(){
        for(int i = 0 ; i < editRecipeViewpagerItems.size(); i++){
            if(editRecipeViewpagerItems.get(i).getmContent().equals("")) return false;
        }
        return true;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addItem(EditRecipeViewpagerItem editRecipeViewpagerItem){
        editRecipeViewpagerItems.add(editRecipeViewpagerItem);
    }

    public void removeItem(int idx){
        editRecipeViewpagerItems.remove(idx);
    }

    public EditRecipeViewpagerItem getEditRecipeViewpagerItemByIdx(int idx){
        return editRecipeViewpagerItems.get(idx);
    }



    public abstract class OnSingleClickListener implements View.OnClickListener {
        //중복클릭시간차이
        private static final long MIN_CLICK_INTERVAL=1000;

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