package com.hxh.component.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.hxh.component.common.model.OneLevel_Bean;

import java.util.ArrayList;

/**
 * Created by hxh on 2018/2/3.
 */

public class AddressDataProvider extends BaseDataProvider {


    /**
     * 初始化数据，解析json数据
     */
    @Override
    public void initData(Context context) {
        parseDataJson(context);
    }

    private void parseDataJson(Context context)
    {
        String cityJson = Utils.getDefaultAddressJson(context);
        mOnes = (ArrayList<OneLevel_Bean>) JSON.parseArray(cityJson,OneLevel_Bean.class);
        if (mOnes == null || mOnes.isEmpty()) {
            return;
        }
        setmOnes(mOnes);
    }
}
