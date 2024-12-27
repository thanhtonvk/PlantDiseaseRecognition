package com.tondz.nhandienbenhcaytrong;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.Surface;

public class CayTrongSDK {
    public native boolean loadModel(AssetManager mgr, int modelid);
    public native boolean openCamera(int facing);
    public native boolean closeCamera();
    public native boolean setOutputWindow(Surface surface);
    public native String predictBoImage();
    public native String predictBoPath(String filePath);
    public native String predictQueImage();
    public native String predictQuePath(String filePath);
    public native String predictNgoImage();
    public native String predictNgoPath(String filePath);
    public native String predictDauImage();
    public native String predictDauPath(String filePath);
    public native String predictCafeImage();
    public native String predictCafePath(String filePath);
    public native String predictSauRiengImage();
    public native String predictSauRiengPath(String filePath);
    public native Bitmap getImage();

    static {
        System.loadLibrary("caytrongsdk");
    }
}
