package com.app.devpai.meupedido.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Pablo on 26/03/2017.
 */

public class Product implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("descricao")
    private String description;

    @SerializedName("tipo_unidade")
    private String type;

    @SerializedName("preco_unitario")
    private float price;

    @SerializedName("promocao")
    private boolean onSale;

    @SerializedName("foto")
    private String productImage;

    private float onCart;

    public Product(int id, String description, String type, double price, boolean onSale, String productImage) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.price = (float) price;
        this.onSale = onSale;
        this.productImage = productImage;
        this.onCart = 0;
    }

    public Product(int id, String description, String type, double price, boolean onSale, String productImage, double onCart) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.price = (float) price;
        this.onSale = onSale;
        this.productImage = productImage;
        this.onCart = (float) onCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = (float) price;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public float getQtdOnCart() {
        return onCart;
    }

    public void setQtdOnCart(double onCart) {
        this.onCart = (float) onCart;
    }

    public double priceTotal() {
        return this.price * this.onCart;
    }

    @Override
    public boolean equals(Object objeto) {
        Product o = (Product) objeto;
        try {
            return this.id == o.getId()
                    &&
                    this.description.equals(o.getDescription())
                    &&
                    this.type.equals(o.getType())
                    &&
                    this.onSale == o.isOnSale()
                    &&
                    this.productImage.equals(o.productImage);

        }catch (Exception e) {
            return false;
        }
    }
}
