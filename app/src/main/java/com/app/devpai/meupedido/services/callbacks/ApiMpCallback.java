package com.app.devpai.meupedido.services.callbacks;

import com.app.devpai.meupedido.models.Address;
import com.app.devpai.meupedido.models.Companie;
import com.app.devpai.meupedido.models.Login;
import com.app.devpai.meupedido.models.Product;

import java.util.List;

/**
 * Created by Pablo on 07/07/2017.
 */

public class ApiMpCallback {

    public interface CallbackRequestAddress {
        void onPreExecution();
        void onSuccess(Address response);
        void onFailure(String e);
        void onResponseUnexpected(String response);
    }

    public interface CallbackRequestAuthenticate {
        void onPreExecution();
        void onSuccess(Login response);
        void onFailure(String e);
        void onResponseUnexpected(String response);
    }

    public interface CallbackRequestProducts {
        void onPreExecution();
        void onSuccess(Product response);
        void onFailure(String e);
        void onResponseUnexpected(String response);
    }

    public interface CallbackRequestListProducts {
        void onPreExecution();
        void onSuccess(List<Product> response);
        void onFailure(String e);
        void onResponseUnexpected(String response);
    }

    public interface CallbackRequestListCompanies {
        void onPreExecution();
        void onSuccess(List<Companie> response);
        void onFailure(String e);
        void onResponseUnexpected(String response);
    }

    public interface CallbackRequestHasUser {
        void onPreExecution();
        void onSuccess(boolean response);
        void onFailure(String e);
        void onResponseUnexpected(String response);
    }
}
