package com.tondz.nhandienbenhcaytrong;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tondz.nhandienbenhcaytrong.service.ApiService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuaCamActivity extends AppCompatActivity {
    private TextureView textureView;
    private FloatingActionButton btnCapture;

    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private ImageReader imageReader;
    private CaptureRequest.Builder captureRequestBuilder;
    ImageButton btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qua_cam);
        textureView = findViewById(R.id.textureView);
        btnCapture = findViewById(R.id.btnCapture);
        btnImage = findViewById(R.id.btnImage);
        onClick();
    }

    private void onClick() {


        // Kiểm tra TextureView đã sẵn sàng
        textureView.setSurfaceTextureListener(textureListener);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private static final int SELECT_IMAGE = 456;

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
                File file = new File(imagePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                ApiService.api.predictQuaCam(imagePart).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String result = response.body() != null ? response.body().string() : "Khoẻ mạnh";
                                Common.bitmap = bitmap;
                                Common.result = result;
                                startActivity(new Intent(getApplicationContext(), KetQuaActivity.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("Upload", "Failed: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload", "onFailure: " + t.getMessage());
                    }
                });


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private final TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            Size[] jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    .getOutputSizes(ImageFormat.JPEG);

            int width = 640; // Kích thước ảnh mặc định
            int height = 480;
            if (jpegSizes != null && jpegSizes.length > 0) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }

            // Thiết lập ImageReader
            imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            imageReader.setOnImageAvailableListener(imageAvailableListener, null);

            // Yêu cầu quyền truy cập camera
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);
                return;
            }

            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
        }
    };

    // Tạo preview camera
    private void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(640, 480);
            Surface surface = new Surface(texture);

            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            if (cameraDevice == null) return;
                            captureSession = session;
                            updatePreview();
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                            Toast.makeText(QuaCamActivity.this, "Configuration change", Toast.LENGTH_SHORT).show();
                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (cameraDevice == null) return;
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            captureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // Chụp ảnh
    private void takePicture() {
        if (cameraDevice == null) return;
        try {
            CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            captureSession.capture(captureBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // Lưu ảnh
    private final ImageReader.OnImageAvailableListener imageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireLatestImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            File file = new File(getExternalFilesDir(null), "picture.jpg");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                ApiService.api.predictQuaCam(imagePart).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String result = response.body() != null ? response.body().string() : "Khoẻ mạnh";
                                Common.bitmap = textureView.getBitmap();
                                Common.result = result;
                                startActivity(new Intent(getApplicationContext(), KetQuaActivity.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("Upload", "Failed: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload", "onFailure: " + t.getMessage());
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                image.close();
            }
        }
    };

}