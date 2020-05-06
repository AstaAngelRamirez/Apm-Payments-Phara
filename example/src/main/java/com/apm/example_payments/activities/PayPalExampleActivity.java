package com.apm.example_payments.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apm.example_payments.R;
import com.apm.payments.models.PayPalPaymentBuilder;
import com.apm.payments.models.PayPalResponse;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

public class PayPalExampleActivity extends AppCompatActivity {

    //region CONSTANTS
    private static final int PAYPAL_PAYMENT_REQUEST_CODE = 1;
    //endregion

    //region VARS
    private PayPalPaymentBuilder mPayPalPaymentBuilder;
    //endregion

    //region EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_example);

        mPayPalPaymentBuilder = new PayPalPaymentBuilder()
                .loadPaymentConfiguration(
                        getApplicationContext(),
                        false,
                        R.string.paypal_client_id);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, mPayPalPaymentBuilder.getPayPalConfiguration());
        startService(intent);

        findViewById(R.id.button_test_paypal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayPalPaymentBuilder
                        .addProduct("Sample product", 2, 45.5, "MXN", "4324321")
                        .addProduct("Sample product 2", 1, 123.2, "MXN", "5435757")
                        .build(0, 0, "MXN", "No description provided");

                Intent intent = new Intent(PayPalExampleActivity.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, mPayPalPaymentBuilder.getPayPalConfiguration());
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, mPayPalPaymentBuilder.getPayPalPayment());
                startActivityForResult(intent, PAYPAL_PAYMENT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                //try {

                    //Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                /*} catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }*/
                PayPalResponse payPalResponse =
                            new Gson().fromJson(confirm.toJSONObject().toString(), PayPalResponse.class);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    //endregion
}
