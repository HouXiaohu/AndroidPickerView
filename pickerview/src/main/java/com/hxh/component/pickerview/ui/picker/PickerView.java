package com.hxh.component.pickerview.ui.picker;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxh.component.common.PickerStyle;
import com.hxh.component.common.listener.OnPickViewListener;
import com.hxh.component.common.model.OneLevel_Bean;
import com.hxh.component.common.model.ThreeLevel_Bean;
import com.hxh.component.common.model.TwoLevel_Bean;
import com.hxh.component.common.whellview.OnSimpleWheelChangedListener;
import com.hxh.component.common.whellview.SimpleWheelView;
import com.hxh.component.common.whellview.adapters.SimpleArraySimpleWheelAdapterSimpleSimple;
import com.hxh.component.pickerview.R;
import com.hxh.component.pickerview.ui.picker.common.PickerShowType;
import com.hxh.component.pickerview.ui.picker.widget.CanShow;
import com.hxh.component.utils.AddressDataProvider;
import com.hxh.component.utils.BaseDataProvider;
import com.hxh.component.utils.CustomDataProvider;
import com.hxh.component.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 省市区三级选择
 * 作者：liji on 2015/12/17 10:40
 * 邮箱：lijiwork@sina.com
 */
public class PickerView implements CanShow, OnSimpleWheelChangedListener {

    private PickerView() {
    }


    private String TAG = "pickerview";
    private PopupWindow popwindow;
    private View popview;
    private SimpleWheelView mViewProvince;
    private SimpleWheelView mViewCity;
    private SimpleWheelView mViewDistrict;
    private RelativeLayout mRelativeTitleBg;
    private LinearLayout mLinearLayout_main;
    private TextView mTvOK;
    private TextView mTvTitle;
    private TextView mTvCancel;
    private OnPickViewListener mBaseListener;
    private BaseDataProvider parseHelper;
    private PickerView.Builder mBuildConfig;
    private Context context;

    private OneLevel_Bean mCurrentOneLevelBean;
    private TwoLevel_Bean mCurrentTwoLevelBean;
    private ThreeLevel_Bean mCurrentThreeLevelBean;

    /**
     * 初始化，默认解析城市数据，提交加载速度
     */
    public void init(Context context) {
        this.context = context;

        if (null != mBuildConfig.mOneLevelbeans) {
            parseHelper = new CustomDataProvider();
            parseHelper.setmOnes(mBuildConfig.mOneLevelbeans);
        } else if (this.mBuildConfig.isUseAddressPicker) {
            parseHelper = new AddressDataProvider();
        }


        if (null == parseHelper) {
            throw new IllegalArgumentException("no Data Provider,if you use AdressPicker,you can call useAddressPicker()，If not, then you need to provide your data source and call it showData(...) method go setter.... ");
        }

        if (null == mBuildConfig.mStyle) {
            mBuildConfig.mStyle = new PickerStyle().getDefaultStyle();
        }
        mBaseListener = mBuildConfig.mListener;

        //解析初始数据
        if (parseHelper.getmOnes().isEmpty()) {
            parseHelper.initData(context);
            if (mBuildConfig.mOnlyShowData != null) {
                if (mBuildConfig.mOnlyShowData_andAutoFillData) {

                    for (int i = 0; i < parseHelper.getmOnes().size(); i++) {
                        OneLevel_Bean bean = parseHelper.getmOnes().get(i);
                        for (OneLevel_Bean item1 : mBuildConfig.mOnlyShowData) {
                            if(!bean.getName().contains(item1.getName()))
                            {
                                parseHelper.getmOnes().set(i,null);
                                continue;
                            }
                        }
                    }
                    parseHelper.getmOnes().removeAll(Collections.singleton(null));
                } else {
                    parseHelper.setmOnes(mBuildConfig.mOnlyShowData);
                }
            }
        }
        initCityPickerPopwindow();
    }

    /**
     * 初始化popWindow
     */
    private void initCityPickerPopwindow() {

        if (mBuildConfig == null) {

            throw new IllegalArgumentException("please set mBuildConfig first...");
        }

        // 初始化view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popview = layoutInflater.inflate(R.layout.layout_mypicker_view, null);
        mLinearLayout_main = (LinearLayout) popview.findViewById(R.id.ll_title);
        mViewProvince = (SimpleWheelView) popview.findViewById(R.id.id_province);
        mViewCity = (SimpleWheelView) popview.findViewById(R.id.id_city);
        mViewDistrict = (SimpleWheelView) popview.findViewById(R.id.id_district);

        if(mBuildConfig.mStyle.isShowParcelLine())
        {
            mViewCity.setIsShowTop_BottomLine(true);
            mViewDistrict.setIsShowTop_BottomLine(true);
            mViewProvince.setIsShowTop_BottomLine(true);
        }else {
            mViewCity.setIsShowTop_BottomLine(false);
            mViewDistrict.setIsShowTop_BottomLine(false);
            mViewProvince.setIsShowTop_BottomLine(false);
        }

        if (this.mBuildConfig.mShowType == PickerShowType.ONE) {
            mViewCity.setVisibility(View.GONE);
            mViewDistrict.setVisibility(View.GONE);
        } else if (this.mBuildConfig.mShowType == PickerShowType.ONE_TOW) {
            mViewDistrict.setVisibility(View.GONE);
        }
        //初始化头部
        initHeadView();
        //初始化popwindow
        initPopWindow();

        if (mBuildConfig.mStyle != null) {
            setPickerStyle(mBuildConfig.mStyle);
        } else {
            setPickerStyle(new PickerStyle().getDefaultStyle());
        }

        setListener();

        //显示省市区数据
        setUpData();
    }

    private void initPopWindow() {
        popwindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popwindow.setAnimationStyle(R.style.AnimBottom);
        popwindow.setBackgroundDrawable(new ColorDrawable());
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(false);
        popwindow.setFocusable(true);

        popwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setBackgroundAlpha(context, 1.0f);
            }
        });
    }

    private void setListener() {
        OnClickListener listener = new OnClickListener();
        // 添加onclick事件
        mTvCancel.setOnClickListener(listener);
        mTvTitle.setOnClickListener(listener);

        //确认选择
        mTvOK.setOnClickListener(listener);
    }

    private void setPickerStyle(final PickerStyle mBuildConfig) {
        PickerStyle defaultStyle = new PickerStyle().getDefaultStyle();
        if (mBuildConfig.getmHeadView() != null) {
            mRelativeTitleBg.removeAllViews();
            mRelativeTitleBg.addView(mBuildConfig.getmHeadView());
        } else {
            //region 背景
            if (null != mBuildConfig.getmBackGroundDrawable()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    mRelativeTitleBg.setBackground(mBuildConfig.getmBackGroundDrawable());
                else
                    mRelativeTitleBg.setBackgroundDrawable(mBuildConfig.getmBackGroundDrawable());
            } else {
                if (-1 != mBuildConfig.getmBackGroundColor()) {
                    mRelativeTitleBg.setBackgroundColor(Utils.getColor(context, mBuildConfig.getmBackGroundColor()));
                } else {
                    mRelativeTitleBg.setBackgroundColor(defaultStyle.getmBackGroundColor());
                }
            }


            if (null != mBuildConfig.getmHeadBackGroundDrawable()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    mLinearLayout_main.setBackground(mBuildConfig.getmHeadBackGroundDrawable());
                else
                    mLinearLayout_main.setBackgroundDrawable(mBuildConfig.getmHeadBackGroundDrawable());
            } else {
                if (-1 != mBuildConfig.getmHeadBackGroundColor()) {
                    mLinearLayout_main.setBackgroundColor(Utils.getColor(context, mBuildConfig.getmHeadBackGroundColor()));
                } else {
                    mLinearLayout_main.setBackgroundColor(defaultStyle.getmHeadBackGroundColor());
                }
            }
            //endregion


            //标题
            if (!TextUtils.isEmpty(mBuildConfig.getmTitle())) {
                mTvTitle.setText(mBuildConfig.getmTitle());
            }


            //标题文字颜色
            if (-1 != mBuildConfig.getmTitleColor()) {
                mTvTitle.setTextColor(Utils.getColor(context, mBuildConfig.getmTitleColor()));
            } else {
                mTvTitle.setTextColor(defaultStyle.getmTitleColor());
            }

            //设置确认按钮文字大小颜色
            if (-1 != mBuildConfig.getmRightButtonTitleColor()) {
                mTvOK.setTextColor(Utils.getColor(context, mBuildConfig.getmRightButtonTitleColor()));
            } else {
                mTvOK.setTextColor(defaultStyle.getmRightButtonTitleColor());
            }


            if (!TextUtils.isEmpty(mBuildConfig.getmRightButtonTitleText())) {
                mTvOK.setText(mBuildConfig.getmRightButtonTitleText());
            } else {
                mTvOK.setText(defaultStyle.getmRightButtonTitleText());
            }

            //设置取消按钮文字颜色
            if (-1 != mBuildConfig.getmLeftButtonTitleColor()) {
                mTvCancel.setTextColor(Utils.getColor(context, mBuildConfig.getmLeftButtonTitleColor()));
            } else {
                mTvCancel.setTextColor(defaultStyle.getmLeftButtonTitleColor());
            }

            if (!TextUtils.isEmpty(mBuildConfig.getmLeftButtonTitleText())) {
                mTvCancel.setText(mBuildConfig.getmLeftButtonTitleText());
            } else {
                mTvCancel.setText(defaultStyle.getmLeftButtonTitleText());
            }

            // 添加change事件
            mViewProvince.addChangingListener(this);
            // 添加change事件
            mViewCity.addChangingListener(this);
            // 添加change事件
            mViewDistrict.addChangingListener(this);

        }
    }

    private void initHeadView() {
        mRelativeTitleBg = (RelativeLayout) popview.findViewById(R.id.rl_head);
        mTvOK = (TextView) mRelativeTitleBg.findViewById(R.id.tv_confirm);
        mTvTitle = (TextView) mRelativeTitleBg.findViewById(R.id.tv_title);
        mTvCancel = (TextView) mRelativeTitleBg.findViewById(R.id.tv_cancel);
    }

    /**
     * 显示方法
     *
     * @time 2018/2/5 13:03
     * @author
     */
    public void show() {
        if (!isShow()) {
            Utils.setBackgroundAlpha(context, 0.5f);
            popwindow.showAtLocation(popview, Gravity.BOTTOM, 0, 0);
        }
    }

    public ArrayMap<String, List<TwoLevel_Bean>> getDatasMap()
    {
        if(null == parseHelper)
        {
            throw new IllegalStateException("you no setter data!");
        }

        return parseHelper.getmOnes_map();
    }

    public ArrayList<OneLevel_Bean> getDatas()
    {
        if(null == parseHelper)
        {
            throw new IllegalStateException("you no setter data!");
        }

        return parseHelper.getmOnes();
    }


    class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (null != mBaseListener) {
                if (id == R.id.tv_title) {
                    mBaseListener.onTitleButtonClick(popwindow);
                } else if (id == R.id.tv_cancel) {
                    mBaseListener.onLeftButtonClick(popwindow);
                } else if (id == R.id.tv_confirm) {
                    setSelectResult();
                }
            }
        }
    }

    private void setSelectResult() {
        if (this.mBuildConfig.mShowType == PickerShowType.ONE) {
            mBaseListener.onSelect(mCurrentOneLevelBean, null, null, popwindow,mCurrentOnePosition,mCurrentTwoPosition);
        } else if (this.mBuildConfig.mShowType == PickerShowType.ONE_TOW) {
            mBaseListener.onSelect(mCurrentOneLevelBean, mCurrentTwoLevelBean, null, popwindow,mCurrentOnePosition,mCurrentTwoPosition);
        } else if (this.mBuildConfig.mShowType == PickerShowType.ONE_TOW_THREE) {
            mBaseListener.onSelect(mCurrentOneLevelBean, mCurrentTwoLevelBean, mCurrentThreeLevelBean, popwindow,mCurrentOnePosition,mCurrentTwoPosition);
        }
        mBaseListener.onRightButtonClick(popwindow);
    }

    //region 弃用代码
    /**
     * 根据是否显示港澳台数据来初始化最新的数据
     *
     * @param array
     * @return
     */
    //    private OneLevel_Bean[] getProArrData(OneLevel_Bean[] array) {
    //
    //        List<OneLevel_Bean> provinceBeanList = new ArrayList<>();
    //        for (int i = 0; i < array.length; i++) {
    //            provinceBeanList.add(array[i]);
    //        }
    //
    //        //不需要港澳台数据
    ////        if (!mBuildConfig.isShowGAT()) {
    ////            provinceBeanList.remove(provinceBeanList.size() - 1);
    ////            provinceBeanList.remove(provinceBeanList.size() - 1);
    ////            provinceBeanList.remove(provinceBeanList.size() - 1);
    ////        }
    //
    //        proArra = new OneLevel_Bean[provinceBeanList.size()];
    //        for (int i = 0; i < provinceBeanList.size(); i++) {
    //            proArra[i] = provinceBeanList.get(i);
    //        }
    //
    //        return proArra;
    //    }

    //endregion

    /**
     * 加载数据
     */
    private void setUpData() {

        if (parseHelper == null || mBuildConfig == null) {
            return;
        }

        //根据是否显示港澳台数据来初始化最新的数据
        //  getProArrData(parseHelper.getProvinceBeenArray());
        //是否设置了默认的显示省份，并且查找位置
        int provinceDefault = -1;
        if (!TextUtils.isEmpty(mBuildConfig.mDefaultC)) {
            for (OneLevel_Bean item : parseHelper.getmOnes()) {
                if (item.getName().contains(mBuildConfig.mDefaultC)) {
                    provinceDefault = parseHelper.getmOnes().indexOf(item);
                    break;
                }
            }
        }

        SimpleArraySimpleWheelAdapterSimpleSimple arrayWheelAdapter = new SimpleArraySimpleWheelAdapterSimpleSimple<OneLevel_Bean>(context, parseHelper.getmOnes());
        mViewProvince.setViewAdapter(arrayWheelAdapter);

        //自定义item
        if (mBuildConfig.mStyle.getmItemLayoutResoureId() != -1 && mBuildConfig.mStyle.getmItemLayoutTextViewResourceId() != -1) {
            arrayWheelAdapter.setItemResource(mBuildConfig.mStyle.getmItemLayoutResoureId());
            arrayWheelAdapter.setItemTextResource(mBuildConfig.mStyle.getmItemLayoutTextViewResourceId());
        } else {
            arrayWheelAdapter.setItemResource(R.layout.item_pickeview_city);
            arrayWheelAdapter.setItemTextResource(R.id.item_city_name_tv);
        }

        //获取所设置的省的位置，直接定位到该位置
        if (-1 != provinceDefault) {
            mViewProvince.setCurrentItem(provinceDefault);
        }

        // 设置可见条目数量
        //        mViewProvince.setVisibleItems(mBuildConfig.getVisibleItems());
        //        mViewCity.setVisibleItems(mBuildConfig.getVisibleItems());
        //        mViewDistrict.setVisibleItems(mBuildConfig.getVisibleItems());
        //        mViewProvince.setCyclic(mBuildConfig.isProvinceCyclic());
        //        mViewCity.setCyclic(mBuildConfig.isCityCyclic());
        //        mViewDistrict.setCyclic(mBuildConfig.isDistrictCyclic());

        //显示滚轮模糊效果
        mViewProvince.setDrawShadows(true);
        mViewCity.setDrawShadows(true);
        mViewDistrict.setDrawShadows(true);

        //中间线的颜色及高度
        //        mViewProvince.setLineColorStr(mBuildConfig.getLineColor());
        mViewProvince.setLineWidth(0);
        //        mViewCity.setLineColorStr(mBuildConfig.getLineColor());
        mViewCity.setLineWidth(0);
        //        mViewDistrict.setLineColorStr(mBuildConfig.getLineColor());
        mViewDistrict.setLineWidth(0);

        updateCities();


    }


    private int mCurrentOnePosition,mCurrentTwoPosition;

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {

        if (parseHelper == null || mBuildConfig == null) {
            return;
        }

        //省份滚轮滑动的当前位置
        mCurrentOnePosition = mViewProvince.getCurrentItem();

        //省份选中的名称
        mCurrentOneLevelBean = parseHelper.getmOnes().get(mCurrentOnePosition);

        if(null != mCurrentOneLevelBean.getData() && 0!=mCurrentOneLevelBean.getData().size())
        {
            mCurrentTwoLevelBean = mCurrentOneLevelBean.getData().get(0);
        }


        if (parseHelper.getmOnes_map() == null) {
            return;
        }

        List<TwoLevel_Bean> cities = parseHelper.getmOnes_map().get(mCurrentOneLevelBean.getName());
        if (cities == null) {
            return;
        }

        //设置最初的默认城市
        int cityDefault = -1;
        if (!TextUtils.isEmpty(mBuildConfig.mDefaultC) && cities.size() > 0) {
            for (TwoLevel_Bean item : cities) {
                if (item.getName().contains(mBuildConfig.mDefaultC)) {
                    cityDefault = cities.indexOf(item);
                }
            }
        }

        SimpleArraySimpleWheelAdapterSimpleSimple cityWheel = new SimpleArraySimpleWheelAdapterSimpleSimple<TwoLevel_Bean>(context, cities);

        //自定义item
        if (mBuildConfig.mStyle.getmItemLayoutResoureId() != -1 && mBuildConfig.mStyle.getmItemLayoutTextViewResourceId() != -1) {
            cityWheel.setItemResource(mBuildConfig.mStyle.getmItemLayoutResoureId());
            cityWheel.setItemTextResource(mBuildConfig.mStyle.getmItemLayoutTextViewResourceId());
        } else {
            cityWheel.setItemResource(R.layout.item_pickeview_city);
            cityWheel.setItemTextResource(R.id.item_city_name_tv);
        }

        mViewCity.setViewAdapter(cityWheel);
        if (-1 != cityDefault) {
            mViewCity.setCurrentItem(cityDefault);
        } else {
            mViewCity.setCurrentItem(0);
        }
        if (mViewDistrict.getVisibility() == View.VISIBLE && mBuildConfig.mShowType == PickerShowType.ONE_TOW_THREE) {
            updateAreas();
        }

    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        mCurrentTwoPosition = mViewCity.getCurrentItem();
        mCurrentOnePosition = mViewProvince.getCurrentItem();
        if (parseHelper.getmOnes() == null || parseHelper.getmOnes_map() == null) {
            return;
        }

        mCurrentTwoLevelBean = parseHelper.getmOnes().get(mCurrentOnePosition).getData().get(mCurrentTwoPosition);

        List<ThreeLevel_Bean> areas = mCurrentTwoLevelBean.getData();

        if (areas == null) {
            return;
        }

        int districtDefault = -1;
        if (!TextUtils.isEmpty(mBuildConfig.mDefaultD) && areas.size() > 0) {
            for (int i = 0; i < areas.size(); i++) {
                if (mBuildConfig.mDefaultD.contains(areas.get(i).getName())) {
                    mCurrentThreeLevelBean = areas.get(i);
                    districtDefault = i;
                    break;
                }
            }
        }

        SimpleArraySimpleWheelAdapterSimpleSimple districtWheel = new SimpleArraySimpleWheelAdapterSimpleSimple<ThreeLevel_Bean>(context, areas);

        //自定义item
        if (mBuildConfig.mStyle.getmItemLayoutResoureId() != -1
                && mBuildConfig.mStyle.getmItemLayoutTextViewResourceId() != -1) {
            districtWheel.setItemResource(mBuildConfig.mStyle.getmItemLayoutResoureId());
            districtWheel.setItemTextResource(mBuildConfig.mStyle.getmItemLayoutTextViewResourceId());
        } else {
            districtWheel.setItemResource(R.layout.item_pickeview_city);
            districtWheel.setItemTextResource(R.id.item_city_name_tv);
        }

        mViewDistrict.setViewAdapter(districtWheel);


        if (-1 != districtDefault) {
            mViewDistrict.setCurrentItem(districtDefault);
            //获取第一个区名称

        } else {
            mViewDistrict.setCurrentItem(0);
            if (areas.size() > 0) {
                mCurrentThreeLevelBean = areas.get(0);
            }
        }
    }


    @Override
    public void hide() {
        if (isShow()) {
            popwindow.dismiss();
        }
    }

    @Override
    public boolean isShow() {
        return popwindow.isShowing();
    }

    @Override
    public void onChanged(SimpleWheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentThreeLevelBean = parseHelper.getmOnes().get(mViewProvince.getCurrentItem()).getData().get(mViewCity.getCurrentItem()).getData().get(newValue);
        }
    }


    //构造类
    public static class Builder {
        public Builder(Context context) {
            this.mContext = context;
        }


        private PickerStyle mStyle;

        private OnPickViewListener mListener;//监听器


        private Context mContext;
        //联动的级别设置
        private PickerShowType mShowType;

        //默认的地址
        private String mDefaultP;
        private String mDefaultC;
        private String mDefaultD;

        public Builder style(PickerStyle style) {
            this.mStyle = style;
            return this;
        }


        //region 默认的地址
        public Builder defaultAddress(String p, String c, String d) {
            this.mDefaultP = p;
            this.mDefaultC = c;
            this.mDefaultD = d;
            return this;
        }

        public Builder defaultAddress(String p, String c) {
            this.mDefaultP = p;
            this.mDefaultC = c;
            return this;
        }

        public Builder defaultAddress(String p) {
            this.mDefaultP = p;
            return this;
        }

        //endregion

        public Builder levelType(PickerShowType type) {
            this.mShowType = type;
            return this;
        }

        //region 自定义数据
        private ArrayList<OneLevel_Bean> mOneLevelbeans; //一级数据
        //private ArrayMap<String, ArrayMap<String, List<ThreeLevel_Bean>>> mOneLevelbeans_map;

        /**
         * 显示自定义数据
         *
         * @return
         */
        public Builder setData(ArrayList<OneLevel_Bean> oneLevelbeans) {
            this.mOneLevelbeans = oneLevelbeans;
            return this;
        }

        private ArrayList<OneLevel_Bean> mOnlyShowData;
        private boolean mOnlyShowData_andAutoFillData;

        //        /**
        //         * 显示自定义数据
        //         *
        //         * @time 2018/2/3 15:39
        //         * @author
        //         */
        //        public Builder showData(ArrayMap<String, ArrayMap<String, List<ThreeLevel_Bean>>> threeBeans) {
        //            this.mOneLevelbeans = null;
        //
        //            return this;
        //        }


        private boolean isUseAddressPicker;

        public Builder useAddressPicker() {
            this.isUseAddressPicker = true;
            return this;
        }

        public Builder useAddressPicker(ArrayList<OneLevel_Bean> one) {
            this.isUseAddressPicker = true;
            this.mOnlyShowData = one;
            this.mOnlyShowData_andAutoFillData = false;
            return this;
        }

        public Builder useAddressPicker(ArrayList<OneLevel_Bean> one, boolean autofillData) {
            this.isUseAddressPicker = true;
            this.mOnlyShowData = one;
            this.mOnlyShowData_andAutoFillData = autofillData;
            return this;
        }

        //endregion


        public Builder listener(OnPickViewListener listener) {
            this.mListener = listener;
            return this;
        }


        public PickerView build() {
            PickerView pick = new PickerView();
            pick.mBuildConfig = this;
            pick.init(this.mContext);
            return pick;
        }


    }

}
