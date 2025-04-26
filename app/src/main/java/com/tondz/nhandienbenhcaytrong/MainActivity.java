package com.tondz.nhandienbenhcaytrong;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.tondz.nhandienbenhcaytrong.views.CaPheActivity;
import com.tondz.nhandienbenhcaytrong.views.SauRiengActivity;
import com.tondz.nhandienbenhcaytrong.views.RubberActivity;

public class MainActivity extends AppCompatActivity {
    boolean isPass = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnCafe).setOnClickListener(v -> {
            startActivity(new Intent(this, CaPheActivity.class));

        });
        findViewById(R.id.btnSauRieng).setOnClickListener(v -> {
            startActivity(new Intent(this, SauRiengActivity.class));
        });
        findViewById(R.id.btnCaoSu).setOnClickListener(v -> {
            startActivity(new Intent(this, RubberActivity.class));
        });
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, 100);
        }
    }
}