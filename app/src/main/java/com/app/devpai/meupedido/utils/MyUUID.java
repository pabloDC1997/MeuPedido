package com.app.devpai.meupedido.utils;

import java.util.UUID;

/**
 * Created by Pablo on 17/02/2017.
 */


/**
 * Essa classe gera um id aleatroio e unico para cada uma de suas instancias
 * ex: @{new MyUUID().generate()}
 */
public class MyUUID {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
