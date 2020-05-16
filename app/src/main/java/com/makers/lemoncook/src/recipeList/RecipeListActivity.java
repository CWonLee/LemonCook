package com.makers.lemoncook.src.recipeList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.recipeList.adapter.RecipeListRecyclerViewAdapter;

import java.util.ArrayList;

public class RecipeListActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    ArrayList<String> mUrlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Init();

        mRecyclerView = findViewById(R.id.recipe_list_rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecipeListRecyclerViewAdapter recipeListRecyclerViewAdapter = new RecipeListRecyclerViewAdapter(mUrlList);
        mRecyclerView.setAdapter(recipeListRecyclerViewAdapter);

    }

    void Init() {
        for (int i = 0; i < 7; i++) {
            mUrlList.add("https://firebasestorage.googleapis.com/v0/b/lemoncook-80daa.appspot.com/o/unnamed.jpg?alt=media&token=8b5a2de7-9a45-4bf6-bb69-77488a28e08f");
        }
    }
}
