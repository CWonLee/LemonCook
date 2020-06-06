package com.makers.lemoncook.src.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.main.MainActivity;
import com.makers.lemoncook.src.modifyRecipe.ModifyActivity;
import com.makers.lemoncook.src.recipe.adapters.RecipeRecyclerViewAdapter;
import com.makers.lemoncook.src.recipe.adapters.RecipeViewPagerAdapter;
import com.makers.lemoncook.src.recipe.interfaces.RecipeActivityView;
import com.makers.lemoncook.src.recipe.interfaces.RecipeRecyclerViewInterface;
import com.makers.lemoncook.src.recipe.models.RequestZZim;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.http.Url;

public class RecipeActivity extends BaseActivity implements RecipeActivityView {

    ImageView mIvBackBtn, mIvZZim;
    RecyclerView mRecyclerView;
    RecipeRecyclerViewAdapter mRecipeRecyclerViewAdapter;
    RecipeRecyclerViewInterface mRecipeRecyclerViewInterface;
    ViewPager mViewPager;
    ResponseRecipe.Result mResult = new ResponseRecipe.Result();
    RecipeViewPagerAdapter mRecipeViewPagerAdapter;
    ArrayList<String> mUrl = new ArrayList<>();
    ArrayList<Integer> mFragmentNo = new ArrayList<>();
    ArrayList<ArrayList<String>> mMaterial = new ArrayList<>();
    ConstraintLayout mClSaveBtn, mClCapture, mClDelete, mClModify, mClShared;
    CustomDialogDelete mCustomDialogDelete;
    CustomDialogModify mCustomDialogModify;
    CustomDialogShared mCustomDialogShared;
    CustomDialogDeleteShare mCustomDialogDeleteShare;

    int mStartRecipeIdx = 1;
    int mZZim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mIvBackBtn = findViewById(R.id.recipe_iv_back_btn);
        mIvZZim = findViewById(R.id.recipe_iv_lemon);
        mRecyclerView = findViewById(R.id.recipe_rv);
        mViewPager = findViewById(R.id.recipe_vp);
        mClSaveBtn = findViewById(R.id.recipe_cl_save_img);
        mClCapture = findViewById(R.id.recipe_cl_capture);
        mClDelete = findViewById(R.id.recipe_cl_delete);
        mClModify = findViewById(R.id.recipe_cl_modify);
        mClShared = findViewById(R.id.recipe_cl_share);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        Init();

        if (getIntent().getStringExtra("tab") != null && getIntent().getStringExtra("tab").equals("share")) {
            mIvZZim.setVisibility(View.GONE);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRecipeRecyclerViewInterface.changeCurNum(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIvBackBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mIvZZim.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mZZim == 0) {
                    postZZim(mResult.getRecipeNo());
                }
                else {
                    deleteZZim(mResult.getRecipeNo());
                }
            }
        });

        mClSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveViewImage(RecipeActivity.this, mClCapture);
                } catch (IOException e) {
                    showCustomToast("이미지 저장에 실패했습니다");
                    e.printStackTrace();
                }
            }
        });

        mClDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mResult.getUserNo() == 0) {
                    showCustomToast("스타레시피는 삭제할 수 없습니다");
                }
                else {
                    if (getIntent().getStringExtra("tab") == null) {
                        mCustomDialogDelete = new CustomDialogDelete(RecipeActivity.this, positiveListener, negativeListener);
                        mCustomDialogDelete.show();
                    }
                    else if (getIntent().getStringExtra("tab").equals("share")) {
                        mCustomDialogDeleteShare = new CustomDialogDeleteShare(RecipeActivity.this, deleteSharePositiveListener, deleteShareNegativeListener);
                        mCustomDialogDeleteShare.show();
                    }
                    else {
                        mCustomDialogDelete = new CustomDialogDelete(RecipeActivity.this, positiveListener, negativeListener);
                        mCustomDialogDelete.show();
                    }
                }
            }
        });

        mClModify.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mResult.getUserNo() == 0) {
                    showCustomToast("스타레시피는 수정할 수 없습니다");
                }
                else {
                    if (getIntent().getStringExtra("tab") == null) {
                        mCustomDialogModify = new CustomDialogModify(RecipeActivity.this, modifyPositiveListener, modifyNegativeListener);
                        mCustomDialogModify.show();
                    }
                    else if (getIntent().getStringExtra("tab").equals("share")) {
                        showCustomToast("공유된 레시피는 수정할 수 없습니다");
                    }
                    else {
                        mCustomDialogModify = new CustomDialogModify(RecipeActivity.this, modifyPositiveListener, modifyNegativeListener);
                        mCustomDialogModify.show();
                    }
                }
            }
        });

        mClShared.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mResult.getUserNo() == 0) {
                    showCustomToast("스타레시피는 공유할 수 없습니다");
                }
                else {
                    mCustomDialogShared = new CustomDialogShared(RecipeActivity.this, sharedPositiveListener, sharedNegativeListener);
                    mCustomDialogShared.show();
                }
            }
        });
    }

    void Init() {
        showProgressDialog();
        int recipeNo = getIntent().getIntExtra("recipeNo", -1);
        RecipeService recipeService = new RecipeService(this);
        System.out.println("recipeNo : " + recipeNo);
        recipeService.getRecipe(recipeNo);
    }

    @Override
    public void getRecipeSuccess(boolean isSuccess, int code, String message, ResponseRecipe.Result result) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            mResult = result;
            mZZim = result.getIsSave();
            if (result.getIsSave() == 1) {
                mIvZZim.setImageResource(R.drawable.ic_lemon_fill);
            }
            else {
                mIvZZim.setImageResource(R.drawable.ic_lemon_emty);
            }
            mUrl.add(result.getImage());
            mFragmentNo.add(1);
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < result.getIngredient().size(); i++) {
                temp.add(result.getIngredient().get(i).getIngredient());
                if (i % 6 == 5) {
                    ArrayList<String> temp2 = new ArrayList<>();
                    temp2.addAll(temp);
                    mStartRecipeIdx++;
                    mMaterial.add(temp2);
                    temp.clear();
                }
                else if (i % 6 != 5 && i == result.getIngredient().size() - 1) {
                    ArrayList<String> temp2 = new ArrayList<>();
                    temp2.addAll(temp);
                    mStartRecipeIdx++;
                    mMaterial.add(temp2);
                }
            }
            for (int i = 0; i < mMaterial.size(); i++) {
                mUrl.add(result.getImage());
                mFragmentNo.add(2);
            }
            for (int i = 0; i < result.getCookingOrder().size(); i++) {
                mUrl.add(result.getCookingOrder().get(i).getCookingOrderImage());
                mFragmentNo.add(3);
            }

            mRecipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mUrl, this, this);
            mRecyclerView.setAdapter(mRecipeRecyclerViewAdapter);

            mRecipeViewPagerAdapter = new RecipeViewPagerAdapter(getSupportFragmentManager(), 0, mUrl, mFragmentNo, mStartRecipeIdx, mResult, mMaterial);
            mViewPager.setAdapter(mRecipeViewPagerAdapter);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void getRecipeFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void getInterface(RecipeRecyclerViewInterface recipeRecyclerViewInterface) {
        mRecipeRecyclerViewInterface = recipeRecyclerViewInterface;
    }

    @Override
    public void change(int idx) {
        mViewPager.setCurrentItem(idx);
    }

    public void postZZim(int recipeNo) {
        showProgressDialog();
        RecipeService recipeService = new RecipeService(this);
        RequestZZim requestZZim = new RequestZZim();
        requestZZim.setRecipeNo(recipeNo);
        recipeService.postZZim(requestZZim);
    }

    public void deleteZZim(int recipeNo) {
        showProgressDialog();
        RecipeService recipeService = new RecipeService(this);
        recipeService.deleteZZim(recipeNo);
    }

    @Override
    public void postZZimSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            mIvZZim.setImageResource(R.drawable.ic_lemon_fill);
            mZZim = 1;
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void postZZimFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void deleteZZimSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            mIvZZim.setImageResource(R.drawable.ic_lemon_emty);
            mZZim = 0;
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void deleteZZimFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void deleteRecipe() {
        showProgressDialog();
        RecipeService recipeService = new RecipeService(this);
        recipeService.deleteRecipe(Integer.toString(mResult.getRecipeNo()));
    }

    public void deleteShare() {
        showProgressDialog();
        RecipeService recipeService = new RecipeService(this);
        recipeService.deleteShare(mResult.getRecipeNo());
    }

    @Override
    public void deleteRecipeSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void deleteRecipeFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void deleteShareSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void deleteShareFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void saveViewImage(Context context, View v) throws IOException {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
        createDirectoryAndSaveFile(b, fileName, context);
    }

    public void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName, Context context) throws IOException {
        String root = Environment.getExternalStorageDirectory().toString();

        File myDir = new File(root + "/lemonCook");
        myDir.mkdir();
        String fname = fileName;
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        file.createNewFile();
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            addPicToGallery(context, file.getPath());
            showCustomToast("이미지를 저장했습니다");
        } catch (Exception e) {
            showCustomToast("이미지 저장에 실패했습니다");
            e.printStackTrace();
        }
    }

    public static void addPicToGallery(Context context, String photoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            deleteRecipe();
            mCustomDialogDelete.dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialogDelete.dismiss();
        }
    };

    private View.OnClickListener modifyPositiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(RecipeActivity.this, ModifyActivity.class);
            intent.putExtra("recipeNo", mResult.getRecipeNo());
            intent.putExtra("category", mResult.getCategoryNo());
            intent.putExtra("title", mResult.getTitle());
            intent.putExtra("name", mResult.getName());
            intent.putExtra("hashTag", mResult.getHashTag());
            intent.putExtra("mainImg", mResult.getImage());
            intent.putExtra("material", mResult.getIngredient());
            HashMap<String, String> map = new HashMap<>();
            ArrayList<String> img = new ArrayList<>();
            ArrayList<String> content = new ArrayList<>();
            for (int i = 0; i < mResult.getCookingOrder().size(); i++) {
                img.add(mResult.getCookingOrder().get(i).getCookingOrderImage());
                content.add(mResult.getCookingOrder().get(i).getContent());
                map.put(mResult.getCookingOrder().get(i).getCookingOrderImage(), mResult.getCookingOrder().get(i).getContent());
            }
            intent.putExtra("cookingOrder", map);
            intent.putExtra("img", img);
            intent.putExtra("content", content);
            startActivity(intent);

            mCustomDialogModify.dismiss();
        }
    };

    private View.OnClickListener modifyNegativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialogModify.dismiss();
        }
    };

    private View.OnClickListener sharedPositiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            System.out.println("공유하기");
            FeedTemplate params = FeedTemplate
                    .newBuilder(ContentObject.newBuilder(mResult.getTitle() + " " + mResult.getName(),
                            mResult.getImage(),
                            LinkObject.newBuilder()
                                    .setMobileWebUrl("https://play.google.com")
                                    .setWebUrl("https://play.google.com")
                                    .setAndroidExecutionParams("recipeNo=" + mResult.getRecipeNo())
                                    .setIosExecutionParams("recipeNo=" + mResult.getRecipeNo())
                                    .build()
                    )
                            .build())
                    .addButton(new ButtonObject("레시피 받기", LinkObject.newBuilder()
                            .setMobileWebUrl("https://play.google.com")
                            .setWebUrl("https://play.google.com")
                            .setAndroidExecutionParams("recipeNo=" + mResult.getRecipeNo())
                            .setIosExecutionParams("recipeNo=" + mResult.getRecipeNo())
                            .build()))
                    .build();

            // 콜백으로 링크 잘갔는지 확인
            Map<String, String> serverCallbackArgs = new HashMap<>();
            serverCallbackArgs.put("user_id", "${current_user_id}");
            serverCallbackArgs.put("product_id", "${shared_product_id}");

            KakaoLinkService.getInstance().sendDefault(RecipeActivity.this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    showCustomToast("공유를 실패했습니다");
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {

                }
            });

            mCustomDialogShared.dismiss();
        }
    };

    private View.OnClickListener sharedNegativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialogShared.dismiss();
        }
    };

    private View.OnClickListener deleteSharePositiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            deleteShare();

            mCustomDialogDeleteShare.dismiss();
        }
    };

    private View.OnClickListener deleteShareNegativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialogDeleteShare.dismiss();
        }
    };
}
