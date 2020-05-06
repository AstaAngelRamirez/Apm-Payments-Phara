package com.apm.payments.contracts;

import com.apm.payments.models.PaymentMethod;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/06/18.
 */
public interface IPaymentMethodListener {

    void onPaymentMethodRequestStart();
    void onPaymentMethodRequestSuccess(List<PaymentMethod> paymentMethods);
    void onPaymentMethodRequestFailure(Response response);
    void onPaymentMethodRequestFailure(Exception e);
    void onPaymentMethodRequestFailure(ExceptionManager exceptionManager);
}
