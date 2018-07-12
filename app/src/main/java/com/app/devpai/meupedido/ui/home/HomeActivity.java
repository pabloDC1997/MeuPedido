package com.app.devpai.meupedido.ui.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.models.Companie;
import com.app.devpai.meupedido.models.Product;
import com.app.devpai.meupedido.models.User;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.product.ProductClient;
import com.app.devpai.meupedido.ui.cart.CartActivity;
import com.app.devpai.meupedido.ui.home.adapter.ProductAdapter;
import com.app.devpai.meupedido.ui.home.adapter.callback.TouchListenerProductCallback;
import com.app.devpai.meupedido.ui.login.LoginActivity;
import com.app.devpai.meupedido.utils.FormatterUtil;
import com.app.devpai.meupedido.utils.Keys;
import com.app.devpai.meupedido.utils.TinyDB;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ApiMpCallback.CallbackRequestListProducts, TouchListenerProductCallback {

    @BindView(R.id.rl_total_price_field)
    RelativeLayout rlPriceTotal;
    @BindView(R.id.text_view_price_total)
    TextView labelTotal;
    @BindView(R.id.text_view_label_quantity)
    TextView labelQtd;
    EditText editTextSearch;
    List<Product> listProducts;
    List<Product> cartProductsList;
    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    private ProgressDialog progresDialog;
    @BindView(R.id.view_error)
    RelativeLayout viewError;
    @BindView(R.id.text_view_error)
    TextView textViewError;
    @BindView(R.id.btn_refresh)
    CircleImageView btnRefresh;
    Companie companie;
    TinyDB sharePreference;
    private View headerLayout;
    private TextView nameNavHeader;
    private TextView emailNavHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.sharePreference = new TinyDB(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.viewErroEnable(false,null);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        editTextSearch = (EditText) this.findViewById(R.id.edit_text_search);
        editTextSearch.setVisibility(View.GONE);
        listProducts = new ArrayList<>();
        cartProductsList = new ArrayList<>();
        progresDialog = new ProgressDialog(this);

        if (bundle != null) {
            companie = (Companie) bundle.getSerializable(Keys.COMPANIES);
            System.out.println(companie.getNome());
            this.getListProdutcts(companie.getId());
        }

        headerLayout = navigationView.getHeaderView(0);
        nameNavHeader =(TextView) headerLayout.findViewById(R.id.name_nav_header);
        emailNavHeader =(TextView) headerLayout.findViewById(R.id.email_nav_header);

        if (this.sharePreference.getBoolean(Keys.USER_IS_LOGGED)){
            User u = (User) this.sharePreference.getObject(Keys.USER_STORED, User.class);
            this.nameNavHeader.setText(u.getName());
            this.emailNavHeader.setText(u.geteMail());
        } else {
            this.emailNavHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch ( id ){
            case R.id.action_search:
                this.switchSearchViewLayout();
                return true;
            case R.id.action_open_cart:
                this.onClickBtnOpenCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

//        switch (id){
//            case R.id.nav_profile:
//                break;
//            case R.id.nav_m_party:
//                break;
//            case R.id.nav_trash:
//                break;
//            case R.id.nav_about:
//                break;
//            case R.id.nav_contact:
//                break;
//            default:
//                //todo - make an log here
//                break;
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getListProdutcts(int idEmp){
        this.viewErroEnable(false,null);
        ProductClient.getListProduct(idEmp,this);
    }

    private void startRecycleView(List<Product> list) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_produtcts);
        mAdapter = new ProductAdapter(list, this,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager mLayoutManager = gridLayoutManager;
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void switchSearchViewLayout( ) {
        if ( editTextSearch.getVisibility() == View.GONE ) {
            editTextSearch.setVisibility(View.VISIBLE);
        } else if ( editTextSearch.getVisibility() == View.VISIBLE ) {
            editTextSearch.setVisibility(View.GONE);
        } else {
            Log.e("ERROR: ", HomeActivity.class.getName());
        }
    }

    public void onClickBtnOpenCart( ) {
        List<Product> listCart = new ArrayList<>();
        for (Product p : listProducts){
            if (p.getQtdOnCart() > 0){
                listCart.add(p);
            }
        }

        if (listCart.size() > 0 ){
            Bundle bundle = new Bundle();
            bundle.putSerializable(Keys.LIST_CART, (Serializable) listCart);
            Intent intent = new Intent(this, CartActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"Carrinho está vazio.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPreExecution() {
        progresDialog.setTitle("Aguarde.");
        progresDialog.setMessage("Buscando produtos.");
        progresDialog.show();
    }

    @Override
    public void onSuccess(List<Product> response) {
        listProducts.addAll(response);
        startRecycleView(response);
        progresDialog.dismiss();
        this.viewErroEnable(false,null);
    }

    @Override
    public void onFailure(String e) {
        progresDialog.dismiss();
        this.viewErroEnable(true,e);
    }

    @Override
    public void onResponseUnexpected(String response) {
        progresDialog.dismiss();
        this.viewErroEnable(true,response);
    }

    public void viewErroEnable(boolean b, String t){
        if(b){
            this.viewError.setVisibility(View.VISIBLE);
            this.textViewError.setText(t);
        } else{
            this.viewError.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_refresh)
    public void onClickBtnRefresh(){
        this.getListProdutcts(companie.getId());
    }

    //---------------fixme: Implementação Callback clicks da recycle view ----------------------------------
    @Override
    public void onClickDescription(View view, int position) {
        showDialogProdutct(view,position);
    }

    @Override
    public void onClickBtnAdd(View view, int position) {
        Product product = listProducts.get(position);

        if (product.getType().toLowerCase().equals("un")){
            addUnityOnProdutct(position);
        } else {
            addKgOnProducty(position);
        }

    }

    @Override
    public void onClickBtnSub(View view, int position) {
        Product product = listProducts.get(position);

        if (product.getType().toLowerCase().equals("un")){
            subUnityOnProdutct(position);
        } else {
            subKgOnProduct(position);
        }
    }

    private void showDialogProdutct(View view, final int position) {
        final Product product = listProducts.get(position);
        System.out.println(position);
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(dialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView description = (TextView) dialog.findViewById(R.id.description_product_dialog);
        ImageView productImage = (ImageView) dialog.findViewById(R.id.image_view_product_dialog);
        TextView price = (TextView) dialog.findViewById(R.id.text_view_price_dialog);
        final TextView qtdItem = (TextView) dialog.findViewById(R.id.text_view_qtd_products_dialog);
        CircleImageView btnAdd = (CircleImageView) dialog.findViewById(R.id.btn_add_dialog);
        CircleImageView btnSub = (CircleImageView) dialog.findViewById(R.id.btn_sub_dialog);

        final String desc = product.getDescription();
        final String uni = "/" + product.getType();
        String priceResponse = FormatterUtil.getFormatMoneyFromDouble(product.getPrice()) + uni;
        description.setText( desc );
        price.setText( priceResponse );

        if ( product.getProductImage() != null ) {
            final Uri uriPhoto = Uri.parse(product.getProductImage());
            Picasso.with(this).load(uriPhoto).into(productImage);
        }
        final boolean isUn = product.getType().toLowerCase().equals("un");

        if (isUn){
            qtdItem.setText(Integer.toString((int)product.getQtdOnCart()));
        } else {
            qtdItem.setText(Float.toString(product.getQtdOnCart()));
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUn){
                    addUnityOnProdutct(position);
                    qtdItem.setText(Integer.toString((int)product.getQtdOnCart()));
                } else {
                    addKgOnProducty(position);
                    qtdItem.setText(String.format("%.2f", product.getQtdOnCart()));
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUn){
                    subUnityOnProdutct(position);
                    qtdItem.setText(Integer.toString((int)product.getQtdOnCart()));
                } else {
                    subKgOnProduct(position);
                    qtdItem.setText(String.format("%.2f", product.getQtdOnCart()));
                }
            }
        });
    }

    private void subKgOnProduct(int position) {
        float old = this.listProducts.get(position).getQtdOnCart();
        if (old > 0.0) {
            this.listProducts.get(position).setQtdOnCart( old - 0.2 );
            mAdapter.notifyDataSetChanged();
            updateCountProductsOnCart();
            updateTotalOnCart();
        }
    }

    private void addKgOnProducty(int position) {
        float old =  this.listProducts.get(position).getQtdOnCart();
        this.listProducts.get(position).setQtdOnCart( old + 0.2  );
        mAdapter.notifyDataSetChanged();
        updateCountProductsOnCart();
        updateTotalOnCart();
    }

    private void subUnityOnProdutct(int position) {
        int old = (int) this.listProducts.get(position).getQtdOnCart();
        if (old > 0) {
            this.listProducts.get(position).setQtdOnCart(old - 1);
            mAdapter.notifyDataSetChanged();
            updateCountProductsOnCart();
            updateTotalOnCart();
        }
    }

    private void addUnityOnProdutct(int position) {
        int old = (int) this.listProducts.get(position).getQtdOnCart();
        this.listProducts.get(position).setQtdOnCart( old + 1 );
        mAdapter.notifyDataSetChanged();
        updateCountProductsOnCart();
        updateTotalOnCart();
    }

    public void updateCountProductsOnCart(){
        int cont = 0;
        for (Product p : listProducts){
            if (p.getQtdOnCart() > 0){
                cont ++;
            }
        }
        this.labelQtd.setText(Integer.toString(cont));
    }

    public void updateTotalOnCart(){
        float value = 0;
        for (Product p : listProducts){
            if (p.getQtdOnCart() > 0){
                value += (p.getQtdOnCart() * p.getPrice());
            }
        }
        this.labelTotal.setText(String .format("%.2f", value));
    }
}
