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


    public native List<String> predictLaCamImage();

    public native List<String> predictLaCamPath(String filePath);

    public native String predictLaDaoImage();

    public native String predictLaDaoPath(String filePath);

    public native Bitmap getImage();

    static {
        System.loadLibrary("caytrongsdk");
    }
}
