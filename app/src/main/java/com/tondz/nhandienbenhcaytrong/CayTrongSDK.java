package com.tondz.nhandienbenhcaytrong;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.Surface;

public class CayTrongSDK {
    public native boolean loadModel(AssetManager mgr, int modelid);
    public native boolean openCamera(int facing);
    public native boolean closeCamera();
    public native boolean setOutputWindow(Surface surface);
    public native String predictCapture();
    public native String predictImagePath(String filePath);
    public native Bitmap getImage();

    static {
        System.loadLibrary("caytrongsdk");
    }
}
