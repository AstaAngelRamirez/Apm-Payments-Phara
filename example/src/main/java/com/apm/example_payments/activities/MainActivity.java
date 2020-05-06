package com.apm.example_payments.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.apm.example_payments.R;
import com.apm.example_payments.applications.ExampleApplication;
import com.apm.example_payments.clases.VyzvaSubscription;
import com.apm.payments.abstracts.PaymentListener;
import com.apm.payments.abstracts.PaymentMethodListener;
import com.apm.payments.enums.PaymentMethodType;
import com.apm.payments.enums.ProviderType;
import com.apm.payments.http.controllers.PaymentController;
import com.apm.payments.http.controllers.PaymentMethodController;
import com.apm.payments.models.PaymentData;
import com.apm.payments.models.PaymentDataBuilder;
import com.apm.payments.models.PaymentMethod;
import com.apm.payments.models.PaymentResultData;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;
import com.google.gson.Gson;
import com.yuyh.jsonviewer.library.JsonRecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // CONSTANTS
    private static final String TAG = MainActivity.class.getSimpleName();

    private PaymentMethodListener mPaymentMethodListener
            = new PaymentMethodListener() {
        @Override
        public void onPaymentMethodRequestStart() {
            super.onPaymentMethodRequestStart();

            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }

        @Override
        public void onPaymentMethodRequestFailure(ExceptionManager exceptionManager) {
            super.onPaymentMethodRequestFailure(exceptionManager);

            exceptionManager.getRelatedException().printStackTrace();
            findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPaymentMethodRequestFailure(Response response) {
            super.onPaymentMethodRequestFailure(response);

            String[] errors = response.getData(String[].class);
            Log.e(TAG, "You have " + errors.length + " errors!");
            findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

            ((JsonRecyclerView) findViewById(R.id.rv_json))
                    .bindJson(new Gson().toJson(response));
        }

        @Override
        public void onPaymentMethodRequestSuccess(List<PaymentMethod> paymentMethods) {
            super.onPaymentMethodRequestSuccess(paymentMethods);

            Log.i(TAG, paymentMethods.size() + " retrieved elements.");
            findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

            ((JsonRecyclerView) findViewById(R.id.rv_json))
                    .bindJson(new Gson().toJson(paymentMethods));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((JsonRecyclerView) findViewById(R.id.rv_json)).setTextSize(16);

        findViewById(R.id.payment_methods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPaymentMethods();
                doPayment();
            }
        });
    }

    private void requestPaymentMethods() {
        new PaymentMethodController()
                .with(getApplicationContext(), ((ExampleApplication) getApplication()).getRequestConfiguration())
                .listener(mPaymentMethodListener)
                .get();
    }

    private void doPayment(){
        // Fill this class with payment data
        /*PaymentData paymentData = new PaymentData()
                .currency("MXN");*/

        VyzvaSubscription paymentData
                = new PaymentDataBuilder()
                .currency("MXN")
                .deviceSessionId("Dsadsadadsa")
                .method(PaymentMethodType.CARD)
                .paymentMethodId(3)
                .paymentSourceId("321543164631512")
                .provider(ProviderType.OPENPAY)
                .build(VyzvaSubscription.class);

        paymentData.setCausesId(12);

        Log.i(TAG, new Gson().toJson(paymentData));

        new PaymentController<PaymentResultData>()
                .with(getApplicationContext(), R.raw.request)
                .listener(new PaymentListener<PaymentResultData>() {
                    @Override
                    public void onPaymentRequestSuccess(PaymentResultData paymentData) {
                        super.onPaymentRequestSuccess(paymentData);
                    }
                })
                .pay(paymentData, PaymentResultData.class);
    }
}
