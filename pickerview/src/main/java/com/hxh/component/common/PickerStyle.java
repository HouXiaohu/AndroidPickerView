package com.hxh.component.common;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by hxh on 2018/2/3.
 */

public class PickerStyle {
    private String mTitle;//标题
    private int mTitleColor = -1;//标题颜色
    private int mLeftButtonTitleColor = -1; //左边的按钮颜色
    private String mLeftButtonTitleText; //左边的按钮文字
    private int mRightButtonTitleColor = -1 ;  //右边的按钮颜色
    private String mRightButtonTitleText;//右边的按钮文字

    private int mHeadBackGroundColor;
    private Drawable mHeadBackGroundDrawable;

    private int mBackGroundColor = -1; //背景颜色
    private Drawable mBackGroundDrawable;//背景drawable（渐变之类）

    private View mHeadView;//头部布局

    private int mItemColor_Selected = -1; //item选中颜色
    private int mItemColor = -1;   //item未选中颜色
    private int mItemDiverHeight;  //item间隔高度

    private boolean isShowParcelLine=true;
    private int mItemLayoutResoureId=-1; // 你可以自定义item布局中文件
    private int mItemLayoutTextViewResourceId=-1;//布局文件中那个控件的id用于显示

    private boolean isDefault;
    public PickerStyle getDefaultStyle()
    {
        isDefault = true;
        int blue = Color.parseColor("#3795ff");
        mTitleColor = blue;
        mLeftButtonTitleColor = blue;
        mLeftButtonTitleText = "取消";
        mRightButtonTitleColor = blue;
        mRightButtonTitleText = "确定";

        mHeadBackGroundColor = Color.WHITE;
        mBackGroundColor = Color.WHITE;
        mItemColor_Selected = Color.parseColor("#252525");
        mItemColor = Color.parseColor("#999999");
        isShowParcelLine=true;
        return this;
    }

    public PickerStyle(Builder builder)
    {
        this.mTitle = builder.mTitle;
        this.mLeftButtonTitleColor = builder.mLeftButtonTitleColor;
        this.mTitleColor = builder.mTitleColor;
        this.mLeftButtonTitleText = builder.mLeftButtonTitleText;
        this.mRightButtonTitleColor = builder.mRightButtonTitleColor;
        this.mRightButtonTitleText = builder.mRightButtonTitleText;
        this.mBackGroundColor = builder.mBackGroundColor;
        this.mBackGroundDrawable = builder.mBackGroundDrawable;
        this.mHeadView = builder.mView;

    }

    public PickerStyle() {
    }

    public static class Builder{
        private int mBackGroundColor = -1; //背景颜色
        private Drawable mBackGroundDrawable;//背景drawable（渐变之类）
        private int mHeadBackGroundColor;
        private Drawable mHeadBackGroundDrawable;
        private String mTitle;//标题
        private int mTitleColor;//标题颜色

        private int mLeftButtonTitleColor; //左边的按钮颜色
        private String mLeftButtonTitleText; //左边的按钮文字
        private int mRightButtonTitleColor;  //右边的按钮颜色
        private String mRightButtonTitleText;//右边的按钮文字

        private View mView;

        private int mItemLayoutResoureId = -1; // 你可以自定义item布局中文件
        private int mItemLayoutTextViewResourceId = -1;//布局文件中那个控件的id用于显示

        //region         背景
        public Builder background(int color) {
            this.mBackGroundColor = color;
            return this;
        }

        public Builder background(Drawable drawable) {
            this.mBackGroundDrawable = drawable;
            return this;
        }

        public Builder headBackground(int color) {
            this.mHeadBackGroundColor = color;
            return this;
        }

        public Builder headBackground(Drawable drawable) {
            this.mHeadBackGroundDrawable = drawable;
            return this;
        }
        //endregion

        //region 左边
        public Builder leftButton(String title) {
            this.mLeftButtonTitleText = title;
            return this;
        }

        public Builder leftButton(String title, int color) {
            this.mLeftButtonTitleText = title;
            this.mLeftButtonTitleColor = color;
            return this;
        }

        public Builder rightButton(String title) {
            this.mRightButtonTitleText = title;
            return this;
        }

        public Builder rightButton(String title, int color) {
            this.mRightButtonTitleText = title;
            this.mRightButtonTitleColor = color;
            return this;
        }
        //endregion

        public Builder title(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder title(String title, int color) {
            this.mTitleColor = color;
            this.mTitle = title;
            return this;
        }

        public Builder headview(View view)
        {
            this.mView = view;
            return this;
        }

        public Builder itemLayoutId(int layoutid)
        {
            this.mItemLayoutResoureId = layoutid;
            return this;
        }

        public Builder itemLayoutTextViewId(int textviewid)
        {
            this.mItemLayoutTextViewResourceId = textviewid;
            return this;
        }

        private boolean isShowParcelLine=true;
        /**
         * 不显示文字上下的两条线
         * 
         * @time 2018/2/5 18:25
         * 
         * @author 
         */
        public Builder noShowParcelLine()
        {
            this.isShowParcelLine = false;
            return this;
        }

        public PickerStyle build()
        {
            return new PickerStyle(this);
        }

    }


    public int getmHeadBackGroundColor() {
        return mHeadBackGroundColor;
    }

    public Drawable getmHeadBackGroundDrawable() {
        return mHeadBackGroundDrawable;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmTitleColor() {
        return mTitleColor;
    }

    public int getmLeftButtonTitleColor() {
        return mLeftButtonTitleColor;
    }

    public String getmLeftButtonTitleText() {
        return mLeftButtonTitleText;
    }

    public int getmRightButtonTitleColor() {
        return mRightButtonTitleColor;
    }

    public String getmRightButtonTitleText() {
        return mRightButtonTitleText;
    }

    public int getmBackGroundColor() {
        return mBackGroundColor;
    }

    public Drawable getmBackGroundDrawable() {
        return mBackGroundDrawable;
    }

    public View getmHeadView() {
        return mHeadView;
    }

    public int getmItemColor_Selected() {
        return mItemColor_Selected;
    }

    public int getmItemColor() {
        return mItemColor;
    }

    public int getmItemDiverHeight() {
        return mItemDiverHeight;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public int getmItemLayoutResoureId() {
        return mItemLayoutResoureId;
    }

    public int getmItemLayoutTextViewResourceId() {
        return mItemLayoutTextViewResourceId;
    }

    public boolean isShowParcelLine() {
        return isShowParcelLine;
    }
}
