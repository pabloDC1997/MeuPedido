package com.app.devpai.meupedido.ui.home.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.models.Product;
import com.app.devpai.meupedido.ui.home.adapter.callback.TouchListenerProductCallback;
import com.app.devpai.meupedido.utils.FormatterUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pablo on 26/03/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private TouchListenerProductCallback mCallback;
    private List<Product> productList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public ImageView productImage;
        public TextView price;
        public ImageView checkMark;
        public TextView qtdItem;
        public RelativeLayout btnAdd;
        public RelativeLayout btnSub;

        public MyViewHolder( View view ) {
            super(view);
            this.description = (TextView) view.findViewById(R.id.text_view_product_description);
            this.productImage = (ImageView) view.findViewById(R.id.image_view_product_dialog);
            this.price = (TextView) view.findViewById(R.id.text_view_price_iten_rv);
            this.checkMark = (ImageView) view.findViewById(R.id.check_mark);
            this.qtdItem = (TextView) view.findViewById(R.id.text_view_qtd_item_rv);
            this.btnAdd = (RelativeLayout) view.findViewById(R.id.btn_add_item_rv);
            this.btnSub = (RelativeLayout) view.findViewById(R.id.btn_sub_item_rv);

            this.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCallback.onClickDescription(v, position);
                }
            });

            this.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCallback.onClickBtnAdd(v, position);

                }
            });

            this.btnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCallback.onClickBtnSub(v, position);
                }
            });
        }
    }

    public ProductAdapter(List<Product> ProductList, Context context, TouchListenerProductCallback callback) {
        this.productList = ProductList;
        this.mContext = context;
        this.mCallback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_rv_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);
        final String desc = product.getDescription();
        final String uni = "/" + product.getType();
        String priceResponse = FormatterUtil.getFormatMoneyFromDouble(product.getPrice()) + uni;
        holder.description.setText( desc );
        holder.price.setText( priceResponse );

        if ( product.getProductImage() != null ) {
            final Uri uriPhoto = Uri.parse(product.getProductImage());
            Picasso.with(mContext).load(uriPhoto).into(holder.productImage);
        }

        if (product.getQtdOnCart() > 0) {
            holder.checkMark.setVisibility(View.VISIBLE);
        } else {
            holder.checkMark.setVisibility(View.GONE);
        }
        if (product.getType().toLowerCase().equals("un")) {
            holder.qtdItem.setText(Integer.toString((int) product.getQtdOnCart()));
        } else {
            holder.qtdItem.setText(String.format("%.2f", product.getQtdOnCart()) );
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
