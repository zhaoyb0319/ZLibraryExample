package cn.zhaoyb.zlibrary.demo;

import java.io.File;
import java.util.Map;

import cn.zhaoyb.zlibrary.ZHttp;
import cn.zhaoyb.zlibrary.core.ZCallBack;
import cn.zhaoyb.zlibrary.http.HttpParams;
import cn.zhaoyb.zlibrary.utils.FileUtils;
import cn.zhaoyb.zlibrary.utils.ZLoger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 *
 * 验证http相关请求
 * get/post/upload/download
 * 主要为测试，所以重点看下打印的log信息
 * 
 * @author zhaoyb (http://www.zhaoyb.cn)
 *
 */
public class HttpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.http);
    }

    public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.button0:
			get();
			break;
		case R.id.button1:
			getWithHeader();
			break;
		case R.id.button2:
			post();
			break;
		case R.id.button3:
			postWithFile();
			break;
		case R.id.button4:
			jsonRequest();
			break;
		case R.id.button5:
			cleanCache();
			break;
		case R.id.button6:
			removeCache();
			break;
		case R.id.button7:
			readCache();
			break;
		case R.id.button8:
			pause();
			break;
		case R.id.button9:
			download();
			break;
		}
    }

    /** ============== 以下为get、post相关逻辑处理======================*/
    private static final String domain = "http://api.36wu.com/Weather/GetWeatherByIp";
    private static final String requestUrl = domain + "?format=json&ip=118.26.16.189";
	private final ZHttp zh = new ZHttp();
    /** 清空全部缓存 */
    private void cleanCache() {
    	zh.cleanCache();
    }
    /** 移除指定key的缓存（注意，get请求的时候，记得把参数附带上） */
    private void removeCache() {
        zh.removeCache(requestUrl);
    }
    /** 读取一条指定缓存，没有会返回空字符串 */
    private void readCache() {
        toast(new String(zh.getCache(requestUrl)));
    }

    private void get() {
        HttpParams params = new HttpParams();
        params.put("ip", "118.26.16.189");
        params.put("format", "json");
        zh.get(domain, params,
                new ZCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        toast(t);
                    }
                });
    }

    /** 附带有Http请求头的get请求 */
    private void getWithHeader() {
    	HttpParams params = new HttpParams();
        params.put("ip", "118.26.16.189");
        params.put("format", "json");
        params.putHeaders("cookie", "cookie不能告诉你");
        zh.get(domain, params,
                new ZCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        toast(t);
                    }
                });
    }

    private static final String downmain2 = "http://op.juhe.cn/trainTickets/cityCode";
    private void post() {
        HttpParams params = new HttpParams();
        params.put("stationName", "北京");
        params.put("key", "ZT2995212680");
        zh.post(downmain2, params,
                new ZCallBack() {
                    @Override
                    public void onSuccess(Map<String, String> headers, byte[] t) {
                        super.onSuccess(headers, t);
                        // 获取cookie
                        ZLoger.debug("===" + headers.get("Set-Cookie"));
                        ZLoger.debug(new String(t));
                    }
                });
    }

    /**
     * 附带有文件的post请求
     */
    private void postWithFile() {
        HttpParams params = new HttpParams();
        params.put("stationName", "北京");
        params.put("key", "ZT2995212680");
        params.put("img", new File("/storage/emulated/0/zhaoyb.png"));
        params.putHeaders("Cookie", "cookie不能告诉你");
        zh.post(downmain2, params,
                new ZCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        toast(t);
                    }
                });
    }

    /** 使用JSON提交参数而不是Form表单 */
    private void jsonRequest() {
        HttpParams params = new HttpParams();
        params.putHeaders("Cookie", "cookie不能告诉你");
        params.putJsonParams("{\"header\":{\"token\":\"b2a974e7bab36210bd79bde7b25d1017\"},\"body\": {\"mobile\": \"123456\"}}");
        zh.jsonPost(downmain2,
                params, new ZCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        toast(t);
                    }
                });
    }

    /** ============== 以下为download相关逻辑处理======================*/
	private static final String wandoujia_apk_name = "/wandoujia-wandoujia_web_4.51.1.8053.apk";
	private static final String wandoujia_apk_path = "http://m.fallback.wdjcdn.com/release/files/phoenix/4.51.1.8053"
			+ wandoujia_apk_name;
	/** 下载方法和暂停方法 的ZHttp对象必须为同一个对象 */
	private final ZHttp zDownload = new ZHttp();

	/** 暂停下载 */
	private void pause() {
		// 获取DownloadController控制器,方可暂停
		zDownload.getDownloadController(
				FileUtils.getSDCardPath() + wandoujia_apk_name,
				wandoujia_apk_path).pause();
	}

	/** 下载 */
	private void download() {
		zDownload.download(FileUtils.getSDCardPath() + wandoujia_apk_name,
				wandoujia_apk_path, new ZCallBack() {
					@Override
					public void onLoading(long count, long current) {
						super.onLoading(count, current);
						ZLoger.debug(String.format("总大小%d,已下载%d", count, current));
					}

					@Override
					public void onSuccess(byte[] t) {
						super.onSuccess(t);
						toast("完成");
					}
				});
	}

	private void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		ZLoger.debug(text);
	}
}
