package com.example.elias.acervoapp.uiimplementacoes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.elias.acervoapp.R;

import java.util.ArrayList;

/**
 * Created by Elias Paulino on 18/04/2017.
 */

public class GraficoPizza extends FrameLayout {
    int centerColor;
    int raioInterno;
    int num;
    ArrayList<GraficoSecao> secoes;
    public GraficoPizza(Context context) {
        super(context);
    }

    public GraficoPizza(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GraficoPizza,
                0, 0);
        this.centerColor = a.getColor(R.styleable.GraficoPizza_centerColor, 1);
        this.raioInterno = a.getDimensionPixelSize(R.styleable.GraficoPizza_raioInterno, 1);
        this.num = a.getInteger(R.styleable.GraficoPizza_num, 1);
        this.secoes = new ArrayList<GraficoSecao>();
    }
    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pt = new Paint();
        pt.setColor(this.centerColor);
        canvas.drawCircle(this.getWidth()/2, this.getWidth()/2, this.raioInterno, pt);
    }

    public int getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    public int getRaioInterno() {
        return raioInterno;
    }

    public void setRaioInterno(int raioInterno) {
        this.raioInterno = raioInterno;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ArrayList<GraficoSecao> getSecoes() {
        return secoes;
    }

    public void setSecoes(ArrayList<GraficoSecao> secoes) {
        this.secoes = secoes;
    }
}
