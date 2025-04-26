package com.tondz.nhandienbenhcaytrong;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.Surface;

import java.util.List;

public class CayTrongSDK {
    public native boolean loadModel(AssetManager mgr);

    public native boolean openCamera(int facing);

    public native boolean closeCamera();

    public native boolean setOutputWindow(Surface surface);

    public native String predictRubberImage();

    public native String predictRubberPath(String filePath);

    public native List<String> predictCafeImage();

    public native List<String> predictCafePath(String filePath);

    public native List<String> predictSauRiengImage();

    public native List<String> predictSauRiengPath(String filePath);

    public native Bitmap getImage();

    static {
        System.loadLibrary("caytrongsdk");
    }
}
