package com.app.devpai.meupedido.ui.address;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.callbacks.CallbackAlertDialog;
import com.app.devpai.meupedido.models.Address;
import com.app.devpai.meupedido.services.address.ViaCepClient;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.ui.paymanent.PaymentActivity;
import com.app.devpai.meupedido.utils.MaskEditable;
import com.app.devpai.meupedido.utils.Mask;
import com.app.devpai.meupedido.utils.Panel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.btn_continue_screen_address)
    Button btnContinueToPaymentScreen;
    @BindView(R.id.register_address_cep)
    EditText edRegisterAddress;
    @BindView(R.id.register_address_street)
    EditText edRegisterStreet;
    @BindView(R.id.register_address_number)
    EditText edRegisterNumber;
    @BindView(R.id.register_address_complement)
    EditText getEdRegisterComplement;
    @BindView(R.id.register_address_neighbor)
    EditText getEdRegisterNeighbor;
    @BindView(R.id.register_address_city)
    EditText getEdRegisterCity;
    @BindView(R.id.register_address_state)
    EditText getEdRegisterState;
    @BindView(R.id.btn_cep_validation)
    Button btnCepValidation;
    ProgressBar progressBar;
    private  Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_register);
        ButterKnife.bind(this);
        this.edRegisterAddress.addTextChangedListener(MaskEditable.insert(this.edRegisterAddress, Mask.CEP));

        this.getSupportActionBar().setTitle("Endereço");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar_address_screen);
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

    @OnClick(R.id.btn_continue_screen_address)
    public void onClickBtnContinueToPaymentScreen(View view ){
        startActivity(new Intent(getApplicationContext(),PaymentActivity.class ));
    }

    @OnClick(R.id.btn_cep_validation)
    public void onClickBtnCepValidation(View view){
        String st = this.edRegisterAddress.getText().toString().replace("-","");
        if (st.length() != 8){
            Panel.alertPanelSingleButton(this, "Ops!", "O CEP inserido é invalido, por favor tente novamente.", "OK", new CallbackAlertDialog() {
                @Override
                public void onPositiveButtonPressed() {
                    clearEditTextAddress();
                }

                @Override
                public void onNegativeButtonPressed() {

                }
            }).show();
        } else{
            int i = Integer.parseInt(st);
            this.getAddressByCep(i);
        }
    }

    private void getAddressByCep(int cep) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Validando...");

        ViaCepClient.getAddressService(cep, new ApiMpCallback.CallbackRequestAddress() {
            @Override
            public void onPreExecution() {
                progressDialog.show();
            }

            @Override
            public void onSuccess(Address response) {
                runAltoCompleteScreen(response);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(String e) {
                showPanelFailure(e);
                progressDialog.dismiss();
            }

            @Override
            public void onResponseUnexpected(String response) {
                showPanelUnexpectedResponse(response);
                progressDialog.dismiss();
            }
        });
    }

    private void runAltoCompleteScreen(Address address) {
        this.address = address;
        this.edRegisterStreet.setText(this.address.getLogradouro());
        if (this.address.getComplement() != null) this.getEdRegisterComplement.setText(this.address.getComplement());
        this.getEdRegisterNeighbor.setText(this.address.getNeighbor());
        this.getEdRegisterCity.setText(this.address.getCity());
        this.getEdRegisterState.setText(this.address.getUf());
    }

    private void showPanelUnexpectedResponse(String response) {
        Panel.alertPanelSingleButton(this, "Ops!", "Cep inexistente, por favor informe um cep valido.", "OK", new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {
                clearEditTextAddress();
            }

            @Override
            public void onNegativeButtonPressed() {

            }
        }).show();
    }

    private void showPanelFailure(String e) {
        Panel.alertPanelSingleButton(this, "Erro de acesso.", "Não foi possivel obter conexão com servidor.",
                "OK", new CallbackAlertDialog() {
            @Override
            public void onPositiveButtonPressed() {

            }

            @Override
            public void onNegativeButtonPressed() {

            }
        }).show();
    }

    private void clearEditTextAddress() {
        this.edRegisterAddress.setText("");
    }

    private void backToHome() {
        super.onBackPressed();
    }
}
