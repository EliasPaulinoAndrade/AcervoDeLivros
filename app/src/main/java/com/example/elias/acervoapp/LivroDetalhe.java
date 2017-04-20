package com.example.elias.acervoapp;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.fragmentos.DefaultLivroFragment;
import com.example.elias.acervoapp.fragmentos.EditLivroFragment;

import org.w3c.dom.Text;

public class LivroDetalhe extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.secGray)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setClickedButton(View v, int colorBack, int imageIcon, int textColor){
        v.setBackgroundColor(getResources().getColor(colorBack));
        ViewGroup it = (ViewGroup)v;
        for(int i=0; i<it.getChildCount(); i++){
            if(it.getChildAt(i) instanceof ImageView){
                ((ImageView) it.getChildAt(i)).setImageResource(imageIcon);
            }
            if(it.getChildAt(i) instanceof TextView){
                ((TextView) it.getChildAt(i)).setTextColor(getResources().getColor(textColor));
            }
        }
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        RelativeLayout.LayoutParams params =(RelativeLayout.LayoutParams) detail.getLayoutParams();
        TextView titleDetail = (TextView) detail.findViewById(R.id.titleLivroDetail);
        getSupportActionBar().setTitle(titleDetail.getText());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        params.height = 0;
        detail.setLayoutParams(params);

        Fragment editFrag = new EditLivroFragment();
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.fragmentoDetail, editFrag);
        fragTran.commit();
    }
    public void editLivroClick(View v){
        setClickedButton(v, R.color.firstYellow, R.drawable.grayedit, R.color.secGray);
    }
    public void removeLivroClick(View v){
        setClickedButton(v, R.color.firstRed, R.drawable.graylixo, R.color.secGray);
    }
    public void imageClick(View v){
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        detail.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Fragment defaultFrag = new DefaultLivroFragment();
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.fragmentoDetail, defaultFrag);
        fragTran.commit();

        FrameLayout editFrame = (FrameLayout) findViewById(R.id.editButton);
        editFrame.setBackgroundColor(getResources().getColor(R.color.firstGray));
        for(int i=0; i<editFrame.getChildCount(); i++){
            if(editFrame.getChildAt(i) instanceof ImageView){
                ((ImageView) editFrame.getChildAt(i)).setImageResource(R.drawable.lapis);
            }
            if(editFrame.getChildAt(i) instanceof TextView){
                ((TextView) editFrame.getChildAt(i)).setTextColor(getResources().getColor(R.color.white));
            }
        }
    }
}
