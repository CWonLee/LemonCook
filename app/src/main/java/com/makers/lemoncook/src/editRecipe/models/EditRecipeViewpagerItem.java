package com.makers.lemoncook.src.editRecipe.models;

import android.net.Uri;

public class EditRecipeViewpagerItem {
    private Uri mUri;
    private int mNumber;
    private String mContent;

    public Uri getmUri() {
       return mUri;
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
