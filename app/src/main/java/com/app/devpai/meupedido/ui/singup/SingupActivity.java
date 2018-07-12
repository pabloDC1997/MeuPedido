package com.app.devpai.meupedido.ui.singup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.user.UserClient;
import com.app.devpai.meupedido.ui.address.AddressActivity;
import com.app.devpai.meupedido.utils.ActivityUtil;
import com.app.devpai.meupedido.utils.DataUtil;
import com.app.devpai.meupedido.utils.Mask;
import com.app.devpai.meupedido.utils.MaskEditable;
import com.app.devpai.meupedido.utils.TinyDB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingupActivity extends AppCompatActivity {

    public static final String TAG = SingupActivity.class.getName();

    @BindView(R.id.btn_continue_screen_register)
    Button btnContinueToNextScreen;

    @BindView(R.id.register_passoword)
    EditText registerPassoword;

    @BindView(R.id.register_confirm_passoword)
    EditText registerConfirmPassword;

    @BindView(R.id.register_name)
    EditText registerName;

    @BindView(R.id.register_email)
    EditText registerEmail;

    EditText registerCellPhone;

    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        ButterKnife.bind(this);
        tinyDB = new TinyDB(this);

        this.registerCellPhone = (EditText) findViewById(R.id.register_phone);
        this.registerCellPhone.addTextChangedListener(MaskEditable.insert(this.registerCellPhone,Mask.PHONE_9));
        this.registerEmail.setFilters(new InputFilter[] { ActivityUtil.removeWhiteSpaceFilterET() });
        this.registerPassoword.setFilters(new InputFilter[] { ActivityUtil.removeWhiteSpaceFilterET() });

        this.getSupportActionBar().setTitle("Nova Conta");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @OnClick(R.id.btn_continue_screen_register)
    public void onClickContinueToNextScreen(View view ) {
        try {
            this.validateRegisterDatas();
            checkIfEmailExists(this.registerEmail.getText().toString());
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Ops!")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog d = builder.create();
            d.show();
        }
    }

    private void validateRegisterDatas() throws Exception {

        if (this.registerName.getText().toString().length() <= 0 ) {
            throw new Exception("Campo de nome é obrigatório.");
        }

        if (!DataUtil.validateIfEmailIsValid(this.registerEmail.getText().toString())){
            throw  new Exception("E-mail inserido é inválido.");
        }

        if (this.registerPassoword.getText().toString().length() < 4) {
            throw new Exception("Senha deve conter no minimo quatro digitos.");
        }

        if (!(this.registerPassoword.getText().toString().equals(this.registerConfirmPassword.getText().toString()))){
            throw new Exception("Senhas inseridas não correspondem.");
        }

        if (this.registerCellPhone.getText().toString().length() < 16){
            throw new Exception("Telefone inválido.");
        }
    }

    private void checkIfEmailExists(String s) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Validando dados...");

        UserClient.hasUser(s, new ApiMpCallback.CallbackRequestHasUser() {
            @Override
            public void onPreExecution() {
                pDialog.show();
            }

            @Override
            public void onSuccess(boolean response) {
                if (!response) {
                    storageAllData();
                } else {
                    new AlertDialog.Builder(SingupActivity.this)
                            .setTitle("Ops!")
                            .setMessage("E-mail já está sendo usado por outro usuario.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_LONG);
                Log.e(TAG, e);
                pDialog.dismiss();
            }

            @Override
            public void onResponseUnexpected(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
                Log.e(TAG, response);
                pDialog.dismiss();
            }
        });
    }

    private void storageAllData() {
        //todo - implement this
        openRegisterAddressActivity(/*todo - objNewAccount*/);
    }

    private void openRegisterAddressActivity() {
        startActivity(new Intent(this, AddressActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
