package com.hxh.component.citypickerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hxh.component.common.listener.OnPickViewListener;
import com.hxh.component.common.model.OneLevel_Bean;
import com.hxh.component.common.model.ThreeLevel_Bean;
import com.hxh.component.common.model.TwoLevel_Bean;
import com.hxh.component.pickerview.ui.picker.PickerView;
import com.hxh.component.pickerview.ui.picker.common.PickerShowType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private  TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);

    }

    public void one(View view)
    {
        PickerView pickerView =new PickerView.Builder(MainActivity.this)
                .levelType(PickerShowType.ONE)
                .useAddressPicker()
                .listener(new OnPickViewListener()
                {
                    @Override
                    public void onSelect(OneLevel_Bean bean, TwoLevel_Bean two, ThreeLevel_Bean three, PopupWindow popupWindow,int... postion) {
                        tv1.setText(bean.getName());
                        Log.d("hxh","坐标"+postion[0]+"--->"+postion[1]);
                        super.onSelect(bean, two, three, popupWindow);
                    }
                })
                .build();

        Log.d("hxh",JSON.toJSONString(pickerView.getDatas()));

        pickerView.show();
    }

    public void one_two(View view)
    {
        PickerView pickerView =new PickerView.Builder(MainActivity.this)
                .levelType(PickerShowType.ONE_TOW)
                .useAddressPicker()
                .listener(new OnPickViewListener()
                {
                    @Override
                    public void onSelect(OneLevel_Bean bean, TwoLevel_Bean two, ThreeLevel_Bean three, PopupWindow popupWindow,int... postion) {
                        tv1.setText(bean.getName()+"-"+two.getName());
                        Log.d("hxh","坐标"+postion[0]+"--->"+postion[1]);
                        super.onSelect(bean, two, three, popupWindow);
                    }
                })
                .build();
        Log.d("hxh",JSON.toJSONString(pickerView.getDatas()));
        pickerView.show();
    }

    public void one_two_three(View view)
    {
        PickerView pickerView =new PickerView.Builder(MainActivity.this)
                .levelType(PickerShowType.ONE_TOW_THREE)
                .useAddressPicker()
                .listener(new OnPickViewListener()
                {
                    @Override
                    public void onSelect(OneLevel_Bean bean, TwoLevel_Bean two, ThreeLevel_Bean three, PopupWindow popupWindow,int... postion) {
                        tv1.setText(bean.getName()+"-"+two.getName()+"-"+three.getName());
                        Log.d("hxh","坐标"+postion[0]+"--->"+postion[1]);
                        super.onSelect(bean, two, three, popupWindow);
                    }
                })
                .build();
        Log.d("hxh",JSON.toJSONString(pickerView.getDatas()));
        pickerView.show();
    }

    public void settingdata(View view)
    {
        ArrayList<OneLevel_Bean> data = new ArrayList<>();
        OneLevel_Bean bean = new OneLevel_Bean();
        bean.setName("北京");
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);

        PickerView pickerView =new PickerView.Builder(MainActivity.this)
                .levelType(PickerShowType.ONE)
                .useAddressPicker(data)
                .listener(new OnPickViewListener()
                {
                    @Override
                    public void onSelect(OneLevel_Bean bean, TwoLevel_Bean two, ThreeLevel_Bean three, PopupWindow popupWindow,int... postion) {
                        tv1.setText(bean.getName()+"-"+two.getName()+"-"+three.getName());
                        Log.d("hxh","坐标"+postion[0]+"--->"+postion[1]);
                        super.onSelect(bean, two, three, popupWindow);
                    }
                })

                .build();
        Log.d("hxh",JSON.toJSONString(pickerView.getDatas()));
        pickerView.show();
    }

    public void settingdata1(View view)
    {
        ArrayList<OneLevel_Bean> data = new ArrayList<>();
        OneLevel_Bean bean = new OneLevel_Bean();
        bean.setName("北京");
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);
        data.add(bean);



        PickerView pickerView =new PickerView.Builder(MainActivity.this)
                .levelType(PickerShowType.ONE_TOW_THREE)
                .useAddressPicker(data,true)
                .listener(new OnPickViewListener()
                {
                    @Override
                    public void onSelect(OneLevel_Bean bean, TwoLevel_Bean two, ThreeLevel_Bean three, PopupWindow popupWindow,int... postion) {
                        tv1.setText(bean.getName()+"-"+two.getName()+"-"+three.getName());
                        Log.d("hxh","坐标"+postion[0]+"--->"+postion[1]);
                        super.onSelect(bean, two, three, popupWindow);


                    }
                })

                .build();

        Log.d("hxh",JSON.toJSONString(pickerView.getDatas()));

        pickerView.show();
    }

}
