package cn.zhaoyb.zlibrary.demo;

import cn.zhaoyb.zlibrary.utils.ZConfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	
    	initWidget();
    }

    //TODO 可提取到基类activity中
    private void initWidget() {
        TextView mTvVersion = (TextView) findViewById(R.id.textView1);
        mTvVersion.setText("当前框架版本为:" + ZConfig.VERSION);
    }

    //TODO 可提取到基类activity中
    public void widgetClick(View v) {
        switch (v.getId()) {
        case R.id.button1:
            showActivity(this, BitmapActivity.class);
            break;
        case R.id.button2:
            showActivity(this, HttpActivity.class);
            break;
        }
    }
 
    //TODO 可提取到基类activity中
    private void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }
}
