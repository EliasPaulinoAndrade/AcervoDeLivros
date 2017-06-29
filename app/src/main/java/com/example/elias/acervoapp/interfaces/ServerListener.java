package com.example.elias.acervoapp.interfaces;

import java.io.IOException;

/**
 * Created by Elias on 10/05/2017.
 */

public interface ServerListener {
    void retorno(String resultado, Integer postId) throws IOException;

}
