package com.app.devpai.meupedido.ui.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.models.Product;
import com.app.devpai.meupedido.utils.FormatterUtil;

import java.util.List;

/**
 * Created by Pablo on 26/03/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Product> productList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView quantity;
        public TextView price;

        public MyViewHolder( View view ) {
            super(view);
            this.description = (TextView) view.findViewById(R.id.item_cart_description);
            this.quantity = (TextView) view.findViewById(R.id.item_cart_quantity);
            this.price = (TextView) view.findViewById(R.id.item_cart_price);
        }
    }

    public CartAdapter(List<Product> ProductList, Context context) {
        this.productList = ProductList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate(R.layout.item_rv_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product.getQtdOnCart() > 0) {
            String desc = product.getDescription();
            String qtd = Float.toString(product.getQtdOnCart());
            qtd += " " + product.getType().toLowerCase();
            String price = FormatterUtil.getFormatMoneyFromDouble(product.getPrice() * product.getQtdOnCart());

            holder.description.setText(desc);
            holder.quantity.setText(qtd);
            holder.price.setText(price);
        } else {
            productList.remove(position);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
