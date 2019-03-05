package com.zjlanyun.lyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.zjlanyun.lyapp.R;

import java.util.List;

public class CaptureActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener{

    private DecoratedBarcodeView barcodeView;
    private ImageButton btn_flash, btn_vibration, btn_volume;
    private Context mContext = this;
    private boolean flash = false;
    private boolean vibrate = true;
    private boolean playBeep = true;
    private BeepManager beepManager;
    private Intent resultIntent;
    private TextView title;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            barcodeView.pause();
            beepManager.playBeepSoundAndVibrate();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    beepManager.close();
                    beepManager.setBeepEnabled(playBeep);
                    beepManager.updatePrefs();
                }
            }, 200);
            if (result.getText() != null) {
//                barcodeView.setStatusText(result.getText());

                Bundle resultbundle = resultIntent.getExtras();
                resultIntent.putExtra("result", result.getText());

                setResult(RESULT_OK, resultIntent);
                finish();

            } else {
                Toast.makeText(CaptureActivity.this, getString(R.string.error_scan), Toast.LENGTH_SHORT).show();
            }
            //Added preview of scanned barcode
//            ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
//            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        resultIntent = getIntent();
        barcodeView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
        barcodeView.setTorchListener(this);
        barcodeView.decodeContinuous(callback);

        //播放声音和震动
        beepManager = new BeepManager(this);
        beepManager.setVibrateEnabled(vibrate);
        beepManager.setBeepEnabled(playBeep);

        btn_flash = (ImageButton) findViewById(R.id.btn_flash);
        btn_vibration = (ImageButton) findViewById(R.id.btn_vibration);
        btn_volume = (ImageButton) findViewById(R.id.btn_volume);

        if (!hasFlash()) {
            btn_flash.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    /**
     * Check if the device's camera has a Flashlight.
     *
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 闪光灯 开关
     *
     * @param view 闪光灯开关按钮
     */
    public void switchFlashlight(View view) {
        if (flash) {
            barcodeView.setTorchOff();
        } else {
            barcodeView.setTorchOn();
        }
    }

    /**
     * 震动 开关
     *
     * @param view 震动开关按钮
     */
    public void switchVibration(View view) {
        if (vibrate) {
            vibrate = false;
            btn_vibration.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_vibration_off_white_36dp));
        } else {
            vibrate = true;
            btn_vibration.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_vibration_white_36dp));
        }
        beepManager.setVibrateEnabled(vibrate);
    }


    /**
     * 播放声音 开关
     *
     * @param view 播放声音开关按钮
     */
    public void switchVolume(View view) {
        if (playBeep) {
            playBeep = false;
            btn_volume.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_volume_off_white_36dp));
            beepManager.setBeepEnabled(playBeep);
            beepManager.close();
        } else {
            playBeep = true;
            btn_volume.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_volume_up_white_36dp));
            beepManager.setBeepEnabled(playBeep);
            beepManager.updatePrefs();
        }
    }


    @Override
    public void onTorchOn() {
        flash = true;
        btn_flash.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_flash_on_white_36dp));
    }

    @Override
    public void onTorchOff() {
        flash = false;
        btn_flash.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_flash_off_white_36dp));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
