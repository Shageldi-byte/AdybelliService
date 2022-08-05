package com.android.adybelliservice.StoreApplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.R;


public class AppTextView extends AppCompatTextView {
    private Context context;
    private AttributeSet attrs;

    public AppTextView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public AppTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init(attrs);
    }


    public AppTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AppTextView);
        int font = array.getInt(R.styleable.AppTextView_tvFont, -1);
        createView(font);
    }

    public void createView(int font) {
        switch (font) {
            case 1:
                setTypeface(Utils.getBoldFont(context));
                break;
            case 2:
                setTypeface(Utils.getLightFont(context));
                break;
            case 3:
                setTypeface(Utils.getMediumFont(context));
                break;
            case 4:
                setTypeface(Utils.getRegularFont(context));
                break;
        }

    }


}
