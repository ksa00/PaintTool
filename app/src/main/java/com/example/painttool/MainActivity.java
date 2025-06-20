package com.example.painttool;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    private PaintView paintView;
    private int defaultColor = Color.BLACK;

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

        // PaintViewの取得
        paintView = findViewById(R.id.PaintView);

        // カラーパレットの色選択処理（個別に記述）
        View colorRed = findViewById(R.id.colorRed);
        colorRed.setOnClickListener(v -> {
            defaultColor = Color.parseColor("#F44336");
            paintView.setColor(defaultColor);
        });

        View colorGreen = findViewById(R.id.colorGreen);
        colorGreen.setOnClickListener(v -> {
            defaultColor = Color.parseColor("#4CAF50");
            paintView.setColor(defaultColor);
        });

        View colorBlue = findViewById(R.id.colorBlue);
        colorBlue.setOnClickListener(v -> {
            defaultColor = Color.parseColor("#2196F3");
            paintView.setColor(defaultColor);
        });

        View colorYellow = findViewById(R.id.colorYellow);
        colorYellow.setOnClickListener(v -> {
            defaultColor = Color.parseColor("#FFEB3B");
            paintView.setColor(defaultColor);
        });

        View colorBlack = findViewById(R.id.colorBlack);
        colorBlack.setOnClickListener(v -> {
            defaultColor = Color.parseColor("#000000");
            paintView.setColor(defaultColor);
        });

        // カラーピッカーの呼び出し
        Button colorPickerBtn = findViewById(R.id.colorPickerBtn);
        colorPickerBtn.setOnClickListener(v -> {
            AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    defaultColor = color;
                    paintView.setColor(color);
                }

                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                    // キャンセル時の処理（必要なら）
                }
            });
            dialog.show();
        });

        // 消しゴムボタン
        Button eraserBtn = findViewById(R.id.EraserBtn);
        eraserBtn.setOnClickListener(v -> paintView.setEraserMode(true));

        // Undoボタン
        findViewById(R.id.UndoBtn).setOnClickListener(v -> paintView.undo());

        // Redoボタン
        findViewById(R.id.RedoBtn).setOnClickListener(v -> paintView.redo());

        // Clearボタン
        findViewById(R.id.ClearBtn).setOnClickListener(v -> paintView.clear());

        // Saveボタン
        findViewById(R.id.SaveBtn).setOnClickListener(v -> paintView.SaveFiles());
    }
}
