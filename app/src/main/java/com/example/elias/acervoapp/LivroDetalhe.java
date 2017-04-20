package com.example.elias.acervoapp;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.fragmentos.DefaultLivroFragment;
import com.example.elias.acervoapp.fragmentos.EditLivroFragment;
import com.example.elias.acervoapp.fragmentos.RemoveLivroFragment;
import com.example.elias.acervoapp.fragmentos.StatusLivroFragment;

public class LivroDetalhe extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.secGray)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setClickedButton(View v, int colorBack, int imageIcon, int textColor, Fragment novoFragment){
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
        params.height = 0;
        detail.setLayoutParams(params);


        TextView titleDetail = (TextView) detail.findViewById(R.id.titleLivroDetail);
        getSupportActionBar().setTitle(titleDetail.getText());
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.fragmentoDetail, novoFragment);
        fragTran.commit();
    }
    private void unsetClickedButton(FrameLayout button, int imageIcon){
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        detail.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Fragment defaultFrag = new DefaultLivroFragment();
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.fragmentoDetail, defaultFrag);
        fragTran.commit();

        button.setBackgroundColor(getResources().getColor(R.color.firstGray));
        for(int i=0; i<button.getChildCount(); i++){
            if(button.getChildAt(i) instanceof ImageView){
                ((ImageView) button.getChildAt(i)).setImageResource(imageIcon);
            }
            if(button.getChildAt(i) instanceof TextView){
                ((TextView) button.getChildAt(i)).setTextColor(getResources().getColor(R.color.white));
            }
        }
    }
    public void editLivroClick(View v){
        FrameLayout statusFrame = (FrameLayout) findViewById(R.id.statusButton);
        FrameLayout removeFrame = (FrameLayout) findViewById(R.id.removeButton);
        unsetClickedButton(removeFrame, R.drawable.lixo);
        unsetClickedButton(statusFrame, R.drawable.status);
        setClickedButton(v, R.color.firstYellow, R.drawable.grayedit, R.color.secGray, new EditLivroFragment());
    }
    public void removeLivroClick(View v){
        FrameLayout statusFrame = (FrameLayout) findViewById(R.id.statusButton);
        FrameLayout editFrame = (FrameLayout) findViewById(R.id.editButton);
        unsetClickedButton(editFrame, R.drawable.lapis);
        unsetClickedButton(statusFrame, R.drawable.status);
        setClickedButton(v, R.color.firstGreen, R.drawable.graylixo, R.color.secGray, new RemoveLivroFragment());
    }
    public void statusLivroClick(View v){
        FrameLayout removeFrame = (FrameLayout) findViewById(R.id.removeButton);
        FrameLayout editFrame = (FrameLayout) findViewById(R.id.editButton);
        unsetClickedButton(removeFrame, R.drawable.lixo);
        unsetClickedButton(editFrame, R.drawable.lapis);
        setClickedButton(v, R.color.firstRed, R.drawable.statusgray, R.color.secGray, new StatusLivroFragment());
    }
    public void imageClick(View v){
        FrameLayout editFrame = (FrameLayout) findViewById(R.id.editButton);
        FrameLayout removeFrame = (FrameLayout) findViewById(R.id.removeButton);
        FrameLayout statusFrame = (FrameLayout) findViewById(R.id.statusButton);
        unsetClickedButton(editFrame, R.drawable.lapis);
        unsetClickedButton(removeFrame, R.drawable.lixo);
        unsetClickedButton(statusFrame, R.drawable.status);
    }
}
