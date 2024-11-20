package com.tondz.nhandienbenhcaytrong;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KetQuaActivity extends AppCompatActivity {

    TextView txtKetQua;
    ImageView imgKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);
        txtKetQua = findViewById(R.id.txtKetQua);
        imgKetQua = findViewById(R.id.imgKetQua);
        if (Common.result.isEmpty()) {
            txtKetQua.setText("Khoẻ mạnh");
        } else {
            txtKetQua.setText(Common.result);
        }

        imgKetQua.setImageBitmap(Common.bitmap);

    }
}