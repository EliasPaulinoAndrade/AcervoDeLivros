package com.example.elias.acervoapp.interfaces;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by Elias on 29/06/2017.
 */

public interface BitmapListener {
    void retorno(Bitmap bitmap, Integer postId) throws IOException;
}
