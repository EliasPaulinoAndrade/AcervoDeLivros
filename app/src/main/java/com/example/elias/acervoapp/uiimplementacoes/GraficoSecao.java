package com.example.elias.acervoapp.uiimplementacoes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.elias.acervoapp.R;

import java.util.ArrayList;

/**
 * Created by Elias Paulino on 18/04/2017.
 */

public class GraficoSecao extends View{
    int color;
    float qntNum;
    float angulo;
    public GraficoSecao(Context context) {
        super(context);
    }

    public GraficoSecao(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GraficoSecao,
                0, 0);
        this.color = a.getColor(R.styleable.GraficoSecao_secaoColor, 1);
        this.qntNum = a.getFloat(R.styleable.GraficoSecao_qntNumber, 1);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pt = new Paint();
        pt.setColor(this.color);
        GraficoPizza gp = (GraficoPizza)getParent();
        ArrayList<GraficoSecao> secoes = gp.getSecoes();
        float initAng = 0;
        for(GraficoSecao secao : secoes){
            initAng+=secao.qntNum*36/10;
        }
        this.angulo = initAng + this.qntNum*36/10;
        this.angulo = this.angulo/2;
        secoes.add(this);
        //canvas.drawCircle(this.getWidth()/2, this.getWidth()/2, gp.getRaioInterno(), pt);
        canvas.drawArc(new RectF(0, 0, this.getWidth(), this.getWidth()),initAng, this.angulo, true,pt);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getQntNum() {
        return qntNum;
    }

    public void setQntNum(float qntNum) {
        this.qntNum = qntNum;
    }

    public float getAngulo() {
        return angulo;
    }

    public void setAngulo(float angulo) {
        this.angulo = angulo;
    }
}
