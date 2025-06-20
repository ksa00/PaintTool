package com.example.painttool;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class PaintView extends View {
    private Context _context;
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Line> redoLines = new ArrayList<>();
    private Line currentLine;
    private int currentColor = Color.BLACK;
    private boolean isEraser = false;
    private final int eraserColor = Color.WHITE;

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
        for(Line line : lines){
            //線の色情報を取得（読み込み）
            paint.setColor(line.getColor());
            //線の幅を読み込み(デフォルトも可能です)
            paint.setStrokeWidth(10);
            //線クラスから点情報を取得して描画
            ArrayList<Point> pts = line.getPoints();
            for(int i = 0; i < pts.size() - 1; i++){
                //現在の点から次の点情報までの線を引く
                Point start = pts.get(i);
                Point end = pts.get(i + 1);
                canvas.drawLine(start.x, start.y, end.x, end.y, paint);
            }
        }
    }

    //タッチイベントの実装
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x,y;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x=(int)event.getX();
                y=(int)event.getY();
                //線ー点の集合ータップ辞典の押したイベントは線の始点
                //線クラス新規作成して始点情報として保存
                currentLine = new Line(isEraser ? eraserColor : currentColor);
                currentLine.addPoint(new Point(x, y));
                //作成した線情報をリスト（コンテナ）に追加
                lines.add(currentLine);
                redoLines.clear(); // 新しい線を描いたら redo は無効化
                break;
            case MotionEvent.ACTION_MOVE:
                x=(int)event.getX();
                y=(int)event.getY();
                //線クラスに点情報（座標）を追加
                if (currentLine != null) {
                    currentLine.addPoint(new Point(x, y));
                }
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
        if (!lines.isEmpty()) {
            Line last = lines.remove(lines.size() - 1);
            redoLines.add(last);
        }
        //再描画
        invalidate();
    }

    //やり直し機能の実装
    public void redo(){
        if (!redoLines.isEmpty()) {
            Line restored = redoLines.remove(redoLines.size() - 1);
            lines.add(restored);
        }
        invalidate();
    }

    //画面消去機能実装
    public void clear(){
        //全ての線情報の消去
        lines.clear();
        redoLines.clear();
        //再描画
        invalidate();
    }

    //線の色を更新するセッタ
    public void setColor(int color){
        //色情報を線に設定
        currentColor = color;
        isEraser = false; // 色を選んだら消しゴム解除
    }

    //消しゴムモードの切り替え
    public void setEraserMode(boolean eraser){
        isEraser = eraser;
    }

    //保存機能の実装
    public void SaveFiles(){
        //bitmapオブジェクトの初期化
        Bitmap bitmap=Bitmap.createBitmap(this.getWidth(),this.getHeight(),Bitmap.Config.ARGB_8888);
        //描画対象のキャンバスにbitmap描画情報を設定
        Canvas bitmapCanvas=new Canvas(bitmap);
        //全ての線情報を今回用意したCanvasに描画＝onDraw()と処理が被ります
        this.draw(bitmapCanvas);

        //MediaStoreAPIを使用
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
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
