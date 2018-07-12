package com.app.devpai.meupedido.ui.paymanent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.devpai.meupedido.R;
import com.app.devpai.meupedido.utils.ActivityUtil;

import butterknife.ButterKnife;


public class PaymentActivity extends AppCompatActivity {

    ActivityUtil startActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);

        this.getSupportActionBar().setTitle("Pagamento");
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
}
