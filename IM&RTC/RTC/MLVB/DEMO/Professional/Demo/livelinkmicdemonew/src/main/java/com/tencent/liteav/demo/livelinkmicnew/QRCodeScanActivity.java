package com.tencent.liteav.demo.livelinkmicnew;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.tencent.liteav.demo.livelinkmicnew.settting.AVSettingConfig;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScanActivity extends Activity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "V2-QRCodeScanActivity";
    private ZXingScannerView mScannerView;
    private int mPosition;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        ViewGroup view = (ViewGroup) View.inflate(this, R.layout.live_link_mic_new_activity_qr_code_scan, null);
        mScannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };
        Intent intent = getIntent();
        if (intent != null) {
            mPosition = intent.getIntExtra("position", 0);
            Log.d(TAG, "getIntent position " + mPosition);
        }
        view.addView(mScannerView, 0);
        setContentView(view);

        // 检查权限
        checkPublishPermission();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String url = rawResult.toString();
        Log.d(TAG, "handleResult url " + url);
        if (!TextUtils.isEmpty(url) && AVSettingConfig.getInstance().playerURLList.contains(url)) {
            url = "please check url, the url has repeat!!!";
            Toast.makeText(this, url, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Intent intent = new Intent();
        //把返回数据存入Intent
        if (TextUtils.isEmpty(url)) {
            intent.putExtra(AVSettingConfig.INTENT_SCAN_RESULT, "");
        } else {
            intent.putExtra(AVSettingConfig.INTENT_SCAN_RESULT, url);
        }
        intent.putExtra("position", mPosition);
        //设置返回数据
        setResult(RESULT_OK, intent);
        //关闭Activity
        finish();

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(QRCodeScanActivity.this);
            }
        }, 2000);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.livepusher_ibtn_back) {
            finish();
        }
    }

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
                return false;
            }
        }
        return true;
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "scan url";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }
}
