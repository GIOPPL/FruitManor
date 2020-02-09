package com.gioppl.fruitmanor.view.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BananaRefreshLayout extends BaseBananaRefreshLayout {
    public BananaRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public BananaRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean isReachBottom() {
        return false;
    }

    @Override
    protected void showLoadView(boolean isShow) {

    }
}
