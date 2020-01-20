package com.gioppl.fruitmanor.tool;

import android.graphics.drawable.GradientDrawable;

public class RectBackGrand extends GradientDrawable {
    public static GradientDrawable kaleidoscope(float radius, int color){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        return drawable;
    }
}
