package com.example.elias.acervoapp.uiimplementacoes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        float ang = 0;
        GraficoSecao secaoAux;
        for(int i=0; i< getChildCount(); i++){
            secaoAux = (GraficoSecao) getChildAt(i);
            secaoAux.setAnguloInit(ang);
            ang+=secaoAux.getQntNum()*36/10;
            secaoAux.setAnguloEnd(secaoAux.getQntNum()*36/10);
        }
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
}
