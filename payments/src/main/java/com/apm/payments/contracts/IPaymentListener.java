package com.apm.payments.contracts;

import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 26/06/18.
 */
public interface IPaymentListener<T> {

    void onPaymentRequestStart();
    void onPaymentRequestSuccess(T paymentResponse);
    void onPaymentRequestSuccess(List<T> paymentResponse);
    void onPaymentRequestFailure(Response response);
    void onPaymentRequestFailure(Exception e);
    void onPaymentRequestFailure(ExceptionManager exceptionManager);
}
