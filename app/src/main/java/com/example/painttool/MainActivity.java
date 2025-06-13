package com.example.painttool;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        //ラジオボタンイベントの実装
        RadioGroup radioGroup=findViewById(R.id.colorGrp);
        RadioGroup.setOnCkeckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                RadioButton checked=findViewById(checkedId);
                //ボタンクラスから選択されている色情報の文字を取得
                String checkedColor=checked.getText().toString();
                if(checkedColor.equals("Red")){
                    //paintviewの色設定機能の呼び出し
                }
            }
        });
    }
    //取り消しボタンイベントの実装
    //Paintviewの取り消し機能の呼び出し
    //消去ボタンイベントの実装
    //Paintviewの消去機能の呼び出し
    //保存ボタンイベントの実装
    //paintview保存機能の呼び出し

}