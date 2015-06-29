package cn.zhaoyb.zlibrary.demo;

import cn.zhaoyb.zlibrary.ZBitmap;
import cn.zhaoyb.zlibrary.core.BitmapCallBack;
import cn.zhaoyb.zlibrary.utils.FileUtils;
import cn.zhaoyb.zlibrary.utils.ZLoger;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
/**
 *
 * 验证Bitmap显示相关
 * 可以直接给ImageView设置一张网络图片
 * 也可以直接给某个view(View/LinearLayout等）设置一张图片
 * 
 * @author zhaoyb (http://www.zhaoyb.cn)
 *
 */
public class BitmapActivity extends Activity {

	private static final String image_path = "http://img22.mtime.cn/up/2011/02/24/125528.81634936_o.jpg";
	private static final String image_path2 = "http://img22.mtime.cn/up/2010/07/09/163350.97431046_o.jpg";
	private static final String image_path3 = "http://img22.mtime.cn/up/2011/08/13/210347.56452129_o.jpg";
	
	private static final String save_image_path3 = "http://pic70.nipic.com/file/20150618/9885883_094734544000_2.jpg";
	
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;
    private View mImg4;
    private ZBitmap zb = new ZBitmap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.bitmap);
    	zb = new ZBitmap();
    	initWidget();
    }

    //TODO 可提取到基类activity中
    private void initWidget() {
        mImg1 = (ImageView) findViewById(R.id.imageView1);
        mImg2 = (ImageView) findViewById(R.id.imageView2);
        mImg3 = (ImageView) findViewById(R.id.imageView3);
        mImg4 = findViewById(R.id.imageView4);
    }

    //TODO 可提取到基类activity中
    public void widgetClick(View v) {
        switch (v.getId()) {
        case R.id.imageView1:
            display1();
            break;
        case R.id.imageView2:
            display2();
            break;
        case R.id.imageView3:
            display3();
            break;
        case R.id.imageView4:
        	display4();
        	break;
        case R.id.button:
            save();
            Toast.makeText(getApplicationContext(), "图片将会出现在SD卡根目录zhaoyb.png", Toast.LENGTH_LONG).show();
            break;
        case R.id.button2:
        	removeCache();
        	break;
        }
    }

	private void save() {
		zb.saveImage(this, save_image_path3, FileUtils.getSDCardPath()
				+ "/zhaoyb.png");
	}

	private void removeCache() {
		// 移除指定图片缓存
		//zb.removeCache(image_path);
		// 清空缓存
		zb.cleanCache();
	}

	private void display1() {
		zb.display(mImg1, image_path);
	}

	private void display2() {
		zb.display(mImg2, image_path2, 0, 0);
	}

	private void display3() {
		zb.display(mImg3, image_path3, new BitmapCallBack() {
			@Override
			public void onPreStart() {
				super.onPreStart();
				ZLoger.debug("即将开始下载");
			}

			@Override
			public void onSuccess(Bitmap bitmap) {
				super.onSuccess(bitmap);
				ZLoger.debug("加载成功");
			}

			@Override
			public void onFailure(String strMsg) {
				super.onFailure(strMsg);
				ZLoger.debug("加载失败--" + strMsg);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				ZLoger.debug("加载完成");
			}
		});
	}

	private void display4() {
		zb.display(mImg4, image_path3);
	}
}
