package com.app.devpai.meupedido.ui.cart;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.ui.login.LoginActivity;
import com.app.devpai.meupedido.ui.cart.adapter.CartAdapter;
import com.app.devpai.meupedido.ui.adapters.shared.ClickListener;
import com.app.devpai.meupedido.ui.adapters.shared.DividerItemDecoration;
import com.app.devpai.meupedido.ui.adapters.shared.RecyclerTouchListener;
import com.app.devpai.meupedido.callbacks.CallbackAlertDialog;
import com.app.devpai.meupedido.models.Product;
import com.app.devpai.meupedido.utils.FormatterUtil;
import com.app.devpai.meupedido.utils.Keys;
import com.app.devpai.meupedido.utils.Panel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CartActivity extends AppCompatActivity implements ClickListener {

    private List<Product> productList;
    private RecyclerView recyclerView;
    private CartAdapter mAdapter;

//    @BindView(R.id.btn_calc_shipping_cart)
//    Button btnCalcShipping;
    @BindView(R.id.btn_finish_shopping)
    Button btnFinishBuy;
    @BindView(R.id.tv_price_shipping)
    TextView textViewPriceShipping;
    @BindView(R.id.tv_price_shopping)
    TextView textViewPriceShopping;
    @BindView(R.id.tv_total_price)
    TextView textViewTotalPrice;
    float priceShipping;
    float priceShopping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_cart);
        this.getSupportActionBar().setTitle("Carrinho");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        this.priceShipping = 0 ;
        this.priceShopping =0;
        this.productList = new ArrayList<>();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            List<Product> listAux = (List<Product>) bundle.getSerializable(Keys.LIST_CART);
            if (listAux != null){
                this.productList.addAll(listAux);
            }
        }

        this.inicializeRicycleView();
        this.updatePriceProducts();
        this.updateTotalPrice();
    }

    private void inicializeRicycleView() {

        recyclerView = (RecyclerView) this.findViewById(R.id.rv_list_itens_cart);
        mAdapter = new CartAdapter(productList,  this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,this));
    }
//
//    @OnClick(R.id.btn_calc_shipping_cart)
//    public void onClickCalcShipping( View view ) {
//        //todo -  implementar esse metodo
//    }

    @OnClick(R.id.btn_finish_shopping)
    public void onClickFinishBuy( View view ) {
        //todo -  implementar esse metodo
    }

//    //clicks recycle view
    @Override
    public void onClick(View view, int position) {
        //no implementation
    }

    @Override
    public void onLongClick(View view, int position) {
        final int pos = position;
        AlertDialog dialog = Panel.alertPanelSingleButton(this,
                "Item selecionado!", "Selecione uma opção pra o item \"" + productList.get(pos).getDescription() +"\"",
                "Remover",
                "Editar",
                new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {

            }

            @Override
            public void onNegativeButtonPressed() {
                Toast.makeText(CartActivity.this, "impleentar este metodo",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_finish_shopping)
    public void onClickBtnFinishShopping(View view ){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void backToHome() {
        super.onBackPressed();
    }

    private void updatePriceProducts(){
        float value = 0;
        for (Product p : productList){
            if (p.getQtdOnCart() > 0){
                value += (p.getQtdOnCart() * p.getPrice());
            } else{
                productList.remove(p);
            }
        }
        this.priceShopping = value;
        this.textViewPriceShopping.setText(FormatterUtil.getFormatMoneyFromDouble(value));
    }

    private void updateTotalPrice(){
        float value = this.priceShopping + this.priceShipping;
        this.textViewTotalPrice.setText(FormatterUtil.getFormatMoneyFromDouble(value));
    }

    private void updatePriceShipping(float value){
        this.textViewPriceShipping.setText(FormatterUtil.getFormatMoneyFromDouble(value));
    }
}
