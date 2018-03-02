package com.hxh.component.common.listener;

import android.widget.PopupWindow;

import com.hxh.component.common.model.OneLevel_Bean;
import com.hxh.component.common.model.ThreeLevel_Bean;
import com.hxh.component.common.model.TwoLevel_Bean;

/**
 * 标题: OnHeadViewListener.java
 * 作者: hxh
 * 日期: 2018/2/3 14:41
 * 描述: 默认的头部布局的点击监听器
 */
public class OnPickViewListener
{

   public void onLeftButtonClick(PopupWindow popupWindow){
        popupWindow.dismiss();
   }

    public void onRightButtonClick(PopupWindow popupWindow){
        popupWindow.dismiss();
    }

    public void onTitleButtonClick(PopupWindow popupWindow){
        popupWindow.dismiss();
    }


    public void onSelect(OneLevel_Bean bean, TwoLevel_Bean two, ThreeLevel_Bean three,PopupWindow popupWindow,int... position){
        popupWindow.dismiss();
    }

}
