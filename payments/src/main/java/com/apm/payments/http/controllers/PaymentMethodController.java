package com.apm.payments.http.controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.apm.payments.abstracts.BaseController;
import com.apm.payments.abstracts.PaymentMethodListener;
import com.apm.payments.contracts.IPaymentMethodListener;
import com.apm.payments.models.PaymentMethod;
import com.apm.request.abstracts.RequestListener;
import com.apm.request.enums.WebServiceType;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.RequestBuilder;
import com.apm.request.models.Response;
import com.apm.request.utils.RequestConfiguration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/06/18.
 */
public class PaymentMethodController extends BaseController {

    private PaymentMethodListener mPaymentMethodListener;
    private IPaymentMethodListener mIPaymentMethodListener;

    @Override
    public PaymentMethodController with(@NonNull Context context) {
        return (PaymentMethodController) super.with(context);
    }

    @Override
    public PaymentMethodController with(@NonNull Context context, int requestConfiguration) {
        return (PaymentMethodController) super.with(context, requestConfiguration);
    }

    @Override
    public PaymentMethodController with(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration) {
        return (PaymentMethodController) super.with(context, requestConfiguration);
    }

    @Override
    public PaymentMethodController webService(int webService) {
        return (PaymentMethodController) super.webService(webService);
    }

    @Override
    public PaymentMethodController webService(@NonNull String webService) {
        return (PaymentMethodController) super.webService(webService);
    }

    @Override
    public PaymentMethodController addOptionalParam(String paramName, String paramValue) {
        return (PaymentMethodController) super.addOptionalParam(paramName, paramValue);
    }

    /**
     * Initialize listener
     *
     * @param paymentMethodListener {@link PaymentMethodListener} listener
     * @return {@link PaymentMethodController} instance
     */
    public PaymentMethodController listener(PaymentMethodListener paymentMethodListener) {
        this.mPaymentMethodListener = paymentMethodListener;
        return this;
    }

    /**
     * Initialize listener
     *
     * @param paymentMethodListener {@link IPaymentMethodListener} listener
     * @return {@link PaymentMethodController} instance
     */
    public PaymentMethodController listener(IPaymentMethodListener paymentMethodListener) {
        this.mIPaymentMethodListener = paymentMethodListener;
        return this;
    }

    /**
     * Get a list of available {@link PaymentMethod}
     */
    public void get() {
        webService(getWebService() == null ? "methods" : getWebService())
                .ofType(WebServiceType.GET);

        RequestBuilder requestBuilder = buildRequest();

        requestBuilder
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mPaymentMethodListener != null)
                            mPaymentMethodListener.onPaymentMethodRequestStart();
                        if (mIPaymentMethodListener != null)
                            mIPaymentMethodListener.onPaymentMethodRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mPaymentMethodListener != null)
                            mPaymentMethodListener.onPaymentMethodRequestFailure(e);
                        if (mIPaymentMethodListener != null)
                            mIPaymentMethodListener.onPaymentMethodRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mPaymentMethodListener != null)
                            mPaymentMethodListener.onPaymentMethodRequestFailure(requestResponse);
                        if (mIPaymentMethodListener != null)
                            mIPaymentMethodListener.onPaymentMethodRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mPaymentMethodListener != null)
                            mPaymentMethodListener.onPaymentMethodRequestFailure(em);
                        if (mIPaymentMethodListener != null)
                            mIPaymentMethodListener.onPaymentMethodRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        List<PaymentMethod> paymentMethodList = null;
                        try {
                            paymentMethodList = Arrays.asList(new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("methods").toString(), PaymentMethod[].class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mPaymentMethodListener != null)
                            mPaymentMethodListener.onPaymentMethodRequestSuccess(paymentMethodList);
                        if (mIPaymentMethodListener != null)
                            mIPaymentMethodListener.onPaymentMethodRequestSuccess(paymentMethodList);
                    }
                });
        new Response().buildRequest(requestBuilder).get();
    }
}
