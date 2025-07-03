package com.example.painttool;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

        // カラー選択ビューのID一覧
        int[] colorViewIds = {
                R.id.colorRed, R.id.colorGreen, R.id.colorBlue,
                R.id.colorYellow, R.id.colorBlack
        };

        // 共通のカラー選択リスナー
        View.OnClickListener colorClickListener = v -> {
            Object tag = v.getTag();
            if (tag != null) {
                String colorHex = tag.toString();
                defaultColor = Color.parseColor(colorHex);
                paintView.setColor(defaultColor);
            }
        };

        // 各カラーViewにリスナーを設定
        for (int id : colorViewIds) {
            View colorView = findViewById(id);
            colorView.setOnClickListener(colorClickListener);
        }

        // カラーピッカーの呼び出し
        ImageButton paletteBtn = findViewById(R.id.PaletteBtn);
        paletteBtn.setOnClickListener(v -> {
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
        ImageButton eraserBtn = findViewById(R.id.EraserBtn);
        eraserBtn.setOnClickListener(v -> paintView.setEraserMode(true));

        // 操作ボタン
        findViewById(R.id.UndoBtn).setOnClickListener(v -> paintView.undo());
        findViewById(R.id.RedoBtn).setOnClickListener(v -> paintView.redo());
        findViewById(R.id.ClearBtn).setOnClickListener(v -> paintView.clear());
        findViewById(R.id.SaveBtn).setOnClickListener(v -> paintView.SaveFiles());
    }
}
