package com.example.elias.acervoapp.uiimplementacoes;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.elias.acervoapp.R;

import org.w3c.dom.Text;

/**
 * Created by Elias on 24/04/2017.
 */

public class LinearPickerLayout extends LinearLayout {
    TextView numero;
    Button up;
    Button down;
    public LinearPickerLayout(Context context) {
        super(context);
    }

    public LinearPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        numero = new TextView(getContext());
        numero.setText("0");
        numero.setGravity(Gravity.CENTER_HORIZONTAL);
        numero.setTextSize(30);
        numero.setTextColor(getResources().getColor(R.color.firstYellow));
        up = new Button(getContext()){
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(10, 10);
            }
        };
        down = new Button(getContext());
        up.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
        down.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
        addView(up);
        addView(numero);
        addView(down);
    }
}
