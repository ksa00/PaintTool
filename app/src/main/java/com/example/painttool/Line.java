package com.example.painttool;

import android.graphics.Point;
import java.util.ArrayList;

public class Line {
    //点情報の集合
    private ArrayList<Point> points;
    //自分が描画された時の色情報
    private int _color;

    //コンストラクタ（引数として色情報
    // 点情報の初期化
    public Line(int color) {
        _color = color;
        points = new ArrayList<>();
    }

    //点情報のコンテナへの要素追加（セッタ）
    public void addPoint(Point p) {
        points.add(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getColor() {
        return _color;
    }
}
