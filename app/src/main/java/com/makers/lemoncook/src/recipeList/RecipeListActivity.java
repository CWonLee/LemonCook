package com.makers.lemoncook.src.recipeList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;

public class RecipeListActivity extends BaseActivity {

    SwipeLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mSwipeLayout = findViewById(R.id.swipe_sample1);
        mSwipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right,mSwipeLayout.findViewWithTag("HideTag"));
//swipe_layout을 클릭한 경우
        mSwipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecipeListActivity.this, "Click on surface", Toast.LENGTH_SHORT).show();
            }
        });
//star버튼을 클릭한 경우
        mSwipeLayout.findViewById(R.id.magnifier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecipeListActivity.this, "Star", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
