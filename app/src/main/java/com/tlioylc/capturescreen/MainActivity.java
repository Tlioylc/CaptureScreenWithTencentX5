package com.tlioylc.capturescreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView webView;
    private Button  captureBtn;
    private Bitmap bitmap;
    private final String url = "http://v.pptv.com/show/hly7OaEHd7WiaFX7kVA.html?rcc_id=wap_144";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置为无标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置为竖屏
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        initView();
        loadURL(url);
    }

    private void initView(){
        webView = (WebView) findViewById(R.id.webview);
        captureBtn = (Button) findViewById(R.id.button);
        captureBtn.setOnClickListener(this);
    }

    private void loadURL(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setLayerType(WebView.LAYER_TYPE_NONE, null);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                Log.i("app","网页加载失败");
            }
        });
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    Log.i("app","加载完成");
                }
            }
        });
    }
////ScrollView截图
//    public Bitmap captureScreen(NestedScrollView scrollView) {
//        int h = 0;
//        Bitmap bitmap = null;
//        // 获取scrollview实际高度
//        for (int i = 0; i <  scrollView.getChildCount(); i++) {
//            h  = scrollView.getChildAt(i).getHeight();
//            scrollView.getChildAt(i).setBackgroundColor(
//                    Color.parseColor("#212932"));
//        }
//        // 创建对应大小的bitmap
//        bitmap = Bitmap.createBitmap(ScrollView.getWidth(), h,
//                Bitmap.Config.RGB_565);
//        final Canvas canvas = new Canvas(bitmap);
//        scrollView.draw(canvas);
//        return bitmap;
//    }
    //webView 截图    
    private Bitmap captureScreenforRecord(){

        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(),
                webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();

        Bitmap bm = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.RGB_565);

        Canvas bigcanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigcanvas.drawBitmap(bm, 0, iHeight, paint);
        webView.draw(bigcanvas);
        return  bm;
    }

    @Override
    public void onClick(View v) {
        bitmap = captureScreenforRecord();
        if(bitmap != null){
            Intent intent = new Intent();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte [] bitmapByte =baos.toByteArray();
            intent.putExtra("bitmap", bitmapByte);
            intent.setClass(MainActivity.this,pictureAcitivity.class);
            startActivity(intent);
        }

    }
}
