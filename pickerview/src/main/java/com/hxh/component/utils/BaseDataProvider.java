package com.hxh.component.utils;

import android.content.Context;
import android.util.ArrayMap;

import com.hxh.component.common.model.OneLevel_Bean;
import com.hxh.component.common.model.ThreeLevel_Bean;
import com.hxh.component.common.model.TwoLevel_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxh on 2018/2/5.
 */

public abstract class BaseDataProvider {


    public abstract void initData(Context context);

    /**
     * 1级数据
     */
    protected ArrayList<OneLevel_Bean> mOnes = new ArrayList<>();

    private ArrayList<ArrayList<TwoLevel_Bean>> mTwos;


    protected ArrayMap<String, List<TwoLevel_Bean>> mOnes_map = new ArrayMap<>();



    public ArrayList<OneLevel_Bean> getmOnes() {
        return mOnes;
    }

    public void setmOnes(ArrayList<OneLevel_Bean> mOnes)
    {
        this.mOnes = mOnes;
        convertDatas();
    }


    public ArrayMap<String, List<TwoLevel_Bean>> getmOnes_map() {
        return mOnes_map;
    }

    public void setmOnes_map(ArrayMap<String, List<TwoLevel_Bean>> mOnes_map) {
        this.mOnes_map = mOnes_map;
    }

    public TwoLevel_Bean getTwoLevelBean(int oneIndex,int twoIndex)
    {
        return  mTwos.get(oneIndex).get(twoIndex);
    }

    public ThreeLevel_Bean getThreeLevelBean(int oneIndex,int twoIndex,int threeIndex)
    {
        return  mTwos.get(oneIndex).get(twoIndex).getData().get(threeIndex);
    }

    //核心转换方法
    private void convertDatas() {

        mTwos = new ArrayList<>();
        for (OneLevel_Bean item : mOnes) {
            mTwos.add(item.getData());
        }

//        ArrayList<ArrayList<ThreeLevel_Bean>> beans = new ArrayList<>();
//        for (ArrayList<TwoLevel_Bean> item : mTwos) {
//            for (TwoLevel_Bean twoLevel_bean : item) {
//                beans.add(twoLevel_bean.getData());
//            }
//        }
//        mThrees.add(beans);
        convertToMap();
        //*/ 初始化默认选中的省、市、区，默认选中第一个省份的第一个市区中的第一个区县
//        if (mOnes != null && !mOnes.isEmpty()) {
//            mOneLevel_Bean = mOnes.get(0);
//            List<TwoLevel_Bean> cityList = mOneLevel_Bean.getData();
//            if (cityList != null && !cityList.isEmpty() && cityList.size() > 0) {
//                mTwoLevel_Bean = cityList.get(0);
//                List<ThreeLevel_Bean> districtList = mTwoLevel_Bean.getData();
//                if (districtList != null && !districtList.isEmpty() && districtList.size() > 0) {
//                    mThreeLevel_Bean = districtList.get(0);
//                }
//            }
//        }


    }

    private void convertToMap() {

        for (OneLevel_Bean item : mOnes) {
            mOnes_map.put(item.getName(),item.getData());
        }



    }


}
