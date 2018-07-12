package com.app.devpai.meupedido.utils;

import com.app.devpai.meupedido.models.Product;

import java.util.List;

/**
 * Created by Pablo on 15/04/2017.
 */

public class Search {

    /**
     *
     * @param list
     * @param object
     * @return true se objete existir na lista e false se objeto não existir na lista
     */
    public static boolean hasIn(List<Product> list, Product object) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).equals(object)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param list
     * @param object
     * @return posição do objeto na lista
     */
    public static int getPosition(List<Product> list, Product object) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).equals(object)){
                return i;
            }
        }
        return -1;
    }
}
