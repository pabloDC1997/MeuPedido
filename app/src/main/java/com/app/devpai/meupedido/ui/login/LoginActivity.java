package com.app.devpai.meupedido.ui.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.models.Login;
import com.app.devpai.meupedido.models.User;
import com.app.devpai.meupedido.services.authenticate.AuthenticateClient;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.ui.singup.SingupActivity;
import com.app.devpai.meupedido.utils.EncryptUtil;
import com.app.devpai.meupedido.utils.Keys;
import com.app.devpai.meupedido.utils.TinyDB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements ApiMpCallback.CallbackRequestAuthenticate {

    private final String TAG = LoginActivity.class.getName();
    @BindView(R.id.btn_make_register)
    CardView btnMakeRegister;
    @BindView(R.id.input_user)
    EditText inputUser;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login_validation)
    Button btnLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.getSupportActionBar().setTitle("Login");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.btn_make_register)
    public void onClickMakeRegister(View view) {
        startActivity(new Intent(getApplicationContext(), SingupActivity.class));
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

    @OnClick(R.id.btn_login_validation)
    public void onClickBtnLogin(View view){
        try {

            String userLogin = inputUser.getText().toString();
            String userPassword = inputPassword.getText().toString();

            if (userLogin.length() <= 0) {
                throw  new Exception("User invalido.");
            } else if (userPassword.length() <= 0) {
                throw  new Exception("Senha invalida.");
            }

            User user = new User();

            user.setLogin(userLogin);
            user.setPassword(EncryptUtil.encrypt(userPassword));
            AuthenticateClient.autenticar(user, this);

        } catch (Exception e){
            Log.i(TAG, e.getMessage());
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Ops!")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
    }

    private void backToHome() {
        super.onBackPressed();
    }

    @Override
    public void onPreExecution() {
        progressDialog.setTitle("Aguarde!!!");
        progressDialog.setMessage("Autenticando usuario...");
        progressDialog.show();
    }

    @Override
    public void onSuccess(Login response) {
        checkIfAuthenticated(response);
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(String e) {
         new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Ops!")
                .setMessage(e)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
        progressDialog.dismiss();
    }

    @Override
    public void onResponseUnexpected(String response) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Ops!")
                .setMessage(response)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
        progressDialog.dismiss();
    }

    private void checkIfAuthenticated(Login response) {
        if (response.isStatus()){
            Toast.makeText(this,response.getMsg(),Toast.LENGTH_SHORT).show();
            TinyDB sharePreference = new TinyDB(this);
            sharePreference.putObject(Keys.USER_STORED,response.getUser());
            sharePreference.putBoolean(Keys.USER_IS_LOGGED,true);
            Intent intent = new Intent();
            intent.putExtra(Keys.RESPONSE_BY_RESULT, true);
            setResult(Keys.RESULT_CODE, intent);
            finish();
        } else {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Ops!")
                    .setMessage(response.getMsg())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }
}
