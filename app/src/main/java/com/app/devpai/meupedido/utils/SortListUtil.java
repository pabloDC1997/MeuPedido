package com.app.devpai.meupedido.utils;

import com.app.devpai.meupedido.models.Product;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pablo on 23/01/2017.
 */

public final class SortListUtil {
    /**
     * Ordena um lista de objetos produtos pela descrição
     * @param productsList
     * @Obs: Alterar implementação deste metodo de acordo com a necessidade
     */
    public static void SORTLIST(List<Product> productsList) {
        Collections.sort(productsList, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                Collator cot = Collator.getInstance(new Locale("pt", "BR"));
                return  cot.compare(product1.getDescription().toUpperCase(), product2.getDescription().toUpperCase());
            }

        });
    }

}
