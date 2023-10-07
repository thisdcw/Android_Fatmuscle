package com.maxsella.cw.fatmuscle.common.base;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;

    protected abstract void initView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
    }

    public Context getmContext() {
        return mContext;
    }


    public void navTo(Class<?> cls){
        Intent intent = new Intent(mContext,cls);
        startActivity(intent);
    }
}
