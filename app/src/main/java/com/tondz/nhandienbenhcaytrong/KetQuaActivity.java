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

    TextView txtKetQua, txtDieuTri;
    ImageView imgKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);
        txtKetQua = findViewById(R.id.txtKetQua);
        imgKetQua = findViewById(R.id.imgKetQua);
        txtDieuTri = findViewById(R.id.txtDieuTri);
        String arr[] = Common.result.split("#");
        if (arr.length > 1) {
            if (arr[0].trim().isEmpty() || arr[1].trim().isEmpty()) {
                txtKetQua.setText("Khoẻ mạnh");
                txtDieuTri.setText("Khoẻ mạnh");
            } else {
                txtKetQua.setText(arr[0].trim());
                txtDieuTri.setText(arr[1].trim());
            }

        } else {
            txtKetQua.setText("Khoẻ mạnh");
            txtDieuTri.setText("Khoẻ mạnh");
        }
        imgKetQua.setImageBitmap(Common.bitmap);

    }
}