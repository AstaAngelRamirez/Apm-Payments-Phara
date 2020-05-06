package com.apm.payments.abstracts;

import com.apm.payments.contracts.IPaymentMethodListener;
import com.apm.payments.models.PaymentMethod;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/06/18.
 */
public abstract class PaymentMethodListener implements IPaymentMethodListener {

    @Override
    public void onPaymentMethodRequestStart() {

    }

    @Override
    public void onPaymentMethodRequestFailure(Exception e) {

    }

    @Override
    public void onPaymentMethodRequestFailure(Response response) {

    }

    @Override
    public void onPaymentMethodRequestFailure(ExceptionManager exceptionManager) {

    }

    @Override
    public void onPaymentMethodRequestSuccess(List<PaymentMethod> paymentMethods) {

    }
}
