package com.example.painttool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PaintView extends View {
    private Context _context;
    private ArrayList<Line> lines=new ArrayList<>();

    public PaintView(Context context){
        super(context);
        _context=context;
    }
    public PaintView(Context context, AttributeSet attr){
        super(context,attr);
        _context=context;
    }

  //描画メソッドの上書き
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //背景色の設定
        canvas.drawColor(Color.WHITE);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        //全ての線を描画
        for(){
            //線の色情報を取得（読み込み）
            //線の幅を読み込み(デフォルトも可能です)
            //線クラスから点情報を取得して描画
            for(){
                //現在の点から次の点情報までの線を引く
                canvas.drawLines(start.x,start.y,end.x,end.y);
            }
        }

    }


    //タッチイベントの実装
    @Override
    public boolean onTouchEvent( MotionEvent event){
        int x,y;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x=(int)event.getX();
                y=(int)event.getY();
                //線ー点の集合ータップ辞典の押したイベントは線の始点
                //線クラス新規作成して始点情報として保存
                //作成した線情報をリスト（コンテナ）に追加

                break;
            case MotionEvent.ACTION_MOVE:
                x=(int)event.getX();
                y=(int)event.getY();
                //線クラスに点情報（座標）を追加
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        //再描画（画面破棄）
        return true;
    }

}
