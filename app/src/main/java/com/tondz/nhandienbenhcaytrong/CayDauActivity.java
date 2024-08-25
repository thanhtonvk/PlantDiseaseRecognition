package com.tondz.nhandienbenhcaytrong;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class CayDauActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    CayTrongSDK cayTrongSDK = new CayTrongSDK();
    private SurfaceView cameraView;
    private static final int REQUEST_CAMERA = 510;
    private static final int SELECT_IMAGE = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cay_dau);
        init();
        reload();
        onClick();
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = Common.decodeUri(selectedImageUri, getApplicationContext());
                String imagePath = FileUtils.getPath(getApplicationContext(), selectedImageUri);
                String result = cayTrongSDK.predictImagePath(imagePath);
                Common.result = predict(result);
                Common.bitmap = bitmap;
                startActivity(new Intent(getApplicationContext(), KetQuaActivity.class));
                Log.e("IMAGE_PATH", imagePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private void onClick() {
        findViewById(R.id.btnCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.bitmap = cayTrongSDK.getImage();
                String result = cayTrongSDK.predictCapture();
                Common.result = predict(result);
                startActivity(new Intent(getApplicationContext(), KetQuaActivity.class));
                Log.e("MainActivity", "result: " + result);
            }
        });
        findViewById(R.id.btnImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }


    private String predict(String result) {
        String[] arr = result.split(",");
        float maxScore = 0;
        int maxIdx = -1;
        for (int i = 0; i < arr.length; i++) {
            if (maxScore < Float.parseFloat(arr[i])) {
                maxScore = Float.parseFloat(arr[i]);
                maxIdx = i;
            }
        }
        if (maxIdx == 2) {
            return Common.cayDau[maxIdx] + " " + (int) (maxScore * 100) + "%";
        } else {
            StringBuilder predictStr = new StringBuilder();
            for (int i = 0; i < arr.length - 1; i++) {
                predictStr.append(Common.cayDau[i]).append(" : ").append((int) (Float.parseFloat(arr[i]) * 100)).append("%").append("\n");
            }
            return predictStr.toString();
        }


    }

    private void init() {
        cameraView = findViewById(R.id.cameraView);

        cameraView.getHolder().addCallback(this);

    }


    private void reload() {
        boolean ret_init = cayTrongSDK.loadModel(getAssets(), 0);
        if (!ret_init) {
            Log.e("MainActivity", "model loadModel failed");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        cayTrongSDK.setOutputWindow(holder.getSurface());


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        cayTrongSDK.openCamera(1);
    }

    @Override
    public void onPause() {
        super.onPause();

        cayTrongSDK.closeCamera();
    }
}