package com.example.painttool;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

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
    //この関数はシステムコントロール専用です
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
        invalidate();

        return true;
    }

    //取り消し機能の実装
    public void undo(){
        //最後に書かれた線の消去
        //再描画
    }
    //画面消去機能実装
    public void clear(){
        //全ての線情報の消去
        //再描画
    }
    //線の色を更新するセッタ
    public void setColor(Color color){
        //色情報を線に設定
    }
    //保存機能の実装
    public void SaveFiles(){
        //bitmapオブジェクトの初期化
        Bitmap bitmap=Bitmap.createBitmap(this.getWidth(),this.getHeight(),Bitmap.Config.ARGB_8888);
        //描画対象のキャンバスにbitmap描画情報を設定
        Canvas bitmapCanvas=new Canvas(bitmap);
        //全ての線情報を今回用意したCanvasに描画＝onDraw()と処理が被ります

        //MediaStoreAPIを使用
        try{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                //ギャラリロールへの画像登録
                ContentResolver resolver=_context.getContentResolver();
                ContentValues values=new ContentValues();

                //ファイル名を決定(管理番号としてミリ秒を使用)
                long milliSec=System.currentTimeMillis();
                String fileName= DateFormat.format("yyyy-MM-dd_kk-mm-ss",milliSec).toString();

                //登録情報の設定
                values.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image_PT_"+fileName+".jpeg");
                values.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+ File.separator+"PaintTool");

                //保存先のuriを取得
                Uri imageURI=resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

                //保存先Uriにファイルを保存
                OutputStream fos=resolver.openOutputStream(Objects.requireNonNull(imageURI));
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                Objects.requireNonNull(fos);

                Toast.makeText(_context,"Save Successful",Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(_context,"Save Failed!!!,Because "+e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
