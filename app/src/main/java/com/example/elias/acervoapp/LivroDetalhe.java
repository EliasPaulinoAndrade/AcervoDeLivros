package com.example.elias.acervoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elias.acervoapp.fragmentos.DefaultLivroFragment;
import com.example.elias.acervoapp.fragmentos.EditLivroFragment;
import com.example.elias.acervoapp.fragmentos.RemoveLivroFragment;
import com.example.elias.acervoapp.fragmentos.StatusLivroFragment;
import com.example.elias.acervoapp.interfaces.BitmapListener;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;

import java.io.IOException;
import java.util.HashMap;

public class LivroDetalhe extends AppCompatActivity implements ServerListener, BitmapListener {
    private int mostrandoFragment;
    Server serverManager;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);
        TextView titulo = (TextView) findViewById(R.id.titleLivroDetail);
        TextView descricao = (TextView) findViewById(R.id.descricaoLivroDetail);
        TextView estado = (TextView) findViewById(R.id.estado);
        img = (ImageView)findViewById(R.id.imageView);
        titulo.setText(getIntent().getStringExtra("titulo"));
        descricao.setText(getIntent().getStringExtra("descricao"));
        estado.setText(getIntent().getStringExtra("descricaoFisica"));

        serverManager = new Server();
        serverManager.setListener(this);

        serverManager.setBitListener(this);
        serverManager.getBitmapFromUrl("http://192.168.1.103/testeCI/assets/imgs/"+getIntent().getIntExtra("idFisico", -1)+".jpg", 1);
        getSupportActionBar().setElevation(0);

        this.mostrandoFragment = 0;//0 default, 1 edit, 2 remove, 3 status
    }
    private void setButton(View v, int colorBack, int imageIcon, int textColor){
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
    }
    private void hideDetail(final RelativeLayout detail){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.diminuiwidth);
        detail.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                for(int i=0; i<detail.getChildCount(); i++){
                    detail.getChildAt(i).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params =(RelativeLayout.LayoutParams) detail.getLayoutParams();
                params.height = 0;
                detail.setLayoutParams(params);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
    private void showDetail(final RelativeLayout detail){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aumentawidth);
        detail.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                detail.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                 for(int i=0; i<detail.getChildCount(); i++){
                    detail.getChildAt(i).setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
    private void setActionBarTitulo(RelativeLayout detail){
        TextView titleDetail = (TextView) detail.findViewById(R.id.titleLivroDetail);
        getSupportActionBar().setTitle(titleDetail.getText());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
    private void unsetActionBarTitulo(){
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    private void trocarFragment(Fragment novoFragment){
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.setCustomAnimations(R.anim.aumentaalpha, R.anim.diminuialpha);
        fragTran.replace(R.id.fragmentoDetail, novoFragment);
        fragTran.commit();
    }
    public void editLivroClick(View v){
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        if(mostrandoFragment>1){
            setButton(findViewById(R.id.statusButton), R.color.firstGray, R.drawable.status, R.color.white);
            setButton(findViewById(R.id.removeButton), R.color.firstGray, R.drawable.lixo, R.color.white);
        }
        else if(mostrandoFragment==0){
            hideDetail(detail);
            setActionBarTitulo(detail);
        }
        setButton(v, R.color.firstYellow, R.drawable.grayedit, R.color.secGray);
        trocarFragment(new EditLivroFragment());
        this.mostrandoFragment = 1;
    }
    public void removeLivroClick(View v){
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        if(mostrandoFragment==1 || mostrandoFragment==3){
            setButton(findViewById(R.id.statusButton), R.color.firstGray, R.drawable.status, R.color.white);
            setButton(findViewById(R.id.editButton), R.color.firstGray, R.drawable.lapis, R.color.white);
        }
        else if(mostrandoFragment==0){
            hideDetail(detail);
            setActionBarTitulo(detail);
        }
        setButton(v, R.color.firstGreen, R.drawable.graylixo, R.color.secGray);
        trocarFragment(new RemoveLivroFragment());
        this.mostrandoFragment=2;
    }
    public void statusLivroClick(View v){
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        if(mostrandoFragment==1 || mostrandoFragment==2){
            setButton(findViewById(R.id.removeButton), R.color.firstGray, R.drawable.lixo, R.color.white);
            setButton(findViewById(R.id.editButton), R.color.firstGray, R.drawable.lapis, R.color.white);
        }
        else if(mostrandoFragment==0){
            hideDetail(detail);
            setActionBarTitulo(detail);
        }
        setButton(v, R.color.firstRed, R.drawable.statusgray, R.color.secGray);
        trocarFragment(new StatusLivroFragment());
        this.mostrandoFragment=3;
    }
    public void imageClick(View v){
        RelativeLayout detail = (RelativeLayout) findViewById(R.id.livroDetail);
        if(mostrandoFragment>0){
            setButton(findViewById(R.id.statusButton), R.color.firstGray, R.drawable.status, R.color.white);
            setButton(findViewById(R.id.removeButton), R.color.firstGray, R.drawable.lixo, R.color.white);
            setButton(findViewById(R.id.editButton), R.color.firstGray, R.drawable.lapis, R.color.white);
            showDetail(detail);
            unsetActionBarTitulo();
        }
        DefaultLivroFragment df = new DefaultLivroFragment();
        trocarFragment(df);
        this.mostrandoFragment=0;
    }

    public void removerLivro(View v){
        Log.d("ID", "removerLivro: "+Integer.toString(getIntent().getIntExtra("idFisico", -1)));
        HashMap<String, String> hs = new HashMap<>();
        hs.put("livroId", Integer.toString(getIntent().getIntExtra("idFisico", -1)));
        serverManager.sendServer("livro", "removeLivroById", hs, 1);

        Intent it = new Intent(this, Livros.class);
        startActivity(it);
    }
    public void editarLivro(View v){
        HashMap<String, String> hs = new HashMap<>();
        Spinner edt = (Spinner) findViewById(R.id.spinnerEdit);
        hs.put("livroId", Integer.toString(getIntent().getIntExtra("idFisico", -1)));
        hs.put("descricao", edt.getSelectedItem().toString());
        Log.d("DESC", "editarLivro: " + edt.getSelectedItem().toString());
        serverManager.sendServer("livro", "editarLivroById", hs, 2);
    }
    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        Toast.makeText(this, "Alterações Feitas...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void retorno(Bitmap bitmap, Integer postId) throws IOException {
        if(bitmap == null)
            return ;
        img.setImageBitmap(bitmap);
    }
}
