package com.app.devpai.meupedido.ui.companie;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.models.Companie;
import com.app.devpai.meupedido.models.User;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.companie.CompanieClient;
import com.app.devpai.meupedido.ui.adapters.shared.ClickListener;
import com.app.devpai.meupedido.ui.adapters.shared.RecyclerTouchListener;
import com.app.devpai.meupedido.ui.companie.adapter.CompanieAdapter;
import com.app.devpai.meupedido.ui.home.HomeActivity;
import com.app.devpai.meupedido.ui.login.LoginActivity;
import com.app.devpai.meupedido.utils.Keys;
import com.app.devpai.meupedido.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lucas on 17/10/2017.
 */

public class CompaniesActivity extends AppCompatActivity implements ClickListener, ApiMpCallback.CallbackRequestListCompanies, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = CompaniesActivity.class.getName();
    private List<Companie> companiesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CompanieAdapter mAdapter;
    private ProgressDialog progresDialog;
    List<Companie> listCompanies;
    RelativeLayout viewError;
    TextView textViewError;
    EditText editTextSearch;
    LocationManager locationManager;
    Location locationUser;
    private View headerLayout;
    private TextView nameNavHeader;
    private TextView emailNavHeader;
    private TinyDB sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_companies);
        this.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        this.sharePreference = new TinyDB(this);
        this.viewError = (RelativeLayout) findViewById(R.id.view_error);
        this.textViewError = (TextView) findViewById(R.id.text_view_error);
        this.editTextSearch = (EditText) this.findViewById(R.id.edit_text_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_companies);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.viewErroEnable(false,null);

        this.headerLayout = navigationView.getHeaderView(0);
        this.nameNavHeader =(TextView) headerLayout.findViewById(R.id.name_nav_header);
        this.emailNavHeader =(TextView) headerLayout.findViewById(R.id.email_nav_header);

        this.checkIfHasUserLogged();

        this.editTextSearch.setVisibility(View.GONE);
        this.listCompanies = new ArrayList<>();
        this.progresDialog = new ProgressDialog(this);
        this.viewErroEnable(false, null);
        this.getListCompanies();
    }

    private void checkIfHasUserLogged() {
        if (this.sharePreference.getBoolean(Keys.USER_IS_LOGGED)){
            User u = (User) this.sharePreference.getObject(Keys.USER_STORED, User.class);
            if (u == null){
                Log.e(TAG, "Objeto usuario logado é igual a null");
                return;
            }
            this.nameNavHeader.setText(u.getName());
            this.emailNavHeader.setText(u.geteMail());
            this.emailNavHeader.setClickable(false);
        } else {
            this.emailNavHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getApplicationContext(),LoginActivity.class),Keys.REQUEST_CODE);
                }
            });
        }
    }

    public void getListCompanies() {
        this.viewError.setVisibility(View.GONE);
        CompanieClient.getListCompanies(this);
    }

    private void startRecycleView(List<Companie> list) {
        companiesList.addAll(list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_companies);
        mAdapter = new CompanieAdapter(companiesList,this.locationUser, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, this));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_companies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                this.switchSearchViewLayout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchSearchViewLayout() {
        if (editTextSearch.getVisibility() == View.GONE) {
            editTextSearch.setVisibility(View.VISIBLE);
        } else if (editTextSearch.getVisibility() == View.VISIBLE) {
            editTextSearch.setVisibility(View.GONE);
        } else {
            Log.e("ERROR: ", HomeActivity.class.getName());
        }
    }

    @Override
    public void onClick(View view, int position) {
        Companie companie = companiesList.get(position);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Keys.COMPANIES, companie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {
        Toast.makeText(getApplicationContext(), companiesList.get(position).getNome(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreExecution() {
        progresDialog.setTitle("Aguarde.");
        progresDialog.setMessage("Buscando empresas.");
        progresDialog.show();
    }

    @Override
    public void onSuccess(List<Companie> response) {
        listCompanies.clear();
        listCompanies.addAll(response);
        startRecycleView(listCompanies);
        progresDialog.dismiss();
        this.viewErroEnable(false, null);
    }

    @Override
    public void onFailure(String e) {
        progresDialog.dismiss();
        this.viewErroEnable(true, e);
    }

    @Override
    public void onResponseUnexpected(String response) {
        progresDialog.dismiss();
        this.viewErroEnable(true, response);
    }

    public void viewErroEnable(boolean b, String t) {
        if (b) {
            this.viewError.setVisibility(View.VISIBLE);
            this.textViewError.setText(t);
        } else {
            this.viewError.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_refresh)
    public void onClickBtnRefresh() {
        this.getListCompanies();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.nav_profile_companies:
                //todo - open profile screen
                break;
            case R.id.nav_my_orders_companies:
                //todo - open order companies
                break;
            case R.id.nav_contact_companies:

                break;
            case R.id.nav_about_companies:
                break;
            default:
                //todo - make an log here
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_companies);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_companies);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sair?")
                    .setMessage("Tem certeza que deseja sair?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Keys.REQUEST_CODE) {
            if (resultCode == Keys.RESULT_CODE) {
                checkIfHasUserLogged();
            }
        }
    }
}
