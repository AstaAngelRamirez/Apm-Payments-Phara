package com.apm.payments.abstracts;

import com.apm.payments.contracts.IPaymentListener;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 26/06/18.
 */
public abstract class PaymentListener<T> implements IPaymentListener<T> {
    @Override
    public void onPaymentRequestStart() {

    }

    @Override
    public void onPaymentRequestSuccess(T paymentResponse) {

    }

    @Override
    public void onPaymentRequestFailure(Response response) {

    }

    @Override
    public void onPaymentRequestFailure(Exception e) {

    }

    @Override
    public void onPaymentRequestFailure(ExceptionManager exceptionManager) {

    }

    @Override
    public void onPaymentRequestSuccess(List<T> paymentResponse) {

    }
}
