package com.apm.payments.http.controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.apm.payments.abstracts.BaseController;
import com.apm.payments.abstracts.PaymentListener;
import com.apm.payments.contracts.IPaymentListener;
import com.apm.request.abstracts.RequestListener;
import com.apm.request.enums.WebServiceType;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.RequestBuilder;
import com.apm.request.models.Response;
import com.apm.request.utils.RequestConfiguration;
import com.apm.request.utils.TokenUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 26/06/18.
 */
public class PaymentController<T> extends BaseController<T> {

    private PaymentListener<T> mPaymentListener;
    private IPaymentListener<T> mIPaymentListener;

    @Override
    public PaymentController<T> with(@NonNull Context context) {
        return (PaymentController<T>) super.with(context);
    }

    @Override
    public PaymentController<T> with(@NonNull Context context, int requestConfiguration) {
        return (PaymentController<T>) super.with(context, requestConfiguration);
    }

    @Override
    public PaymentController<T> with(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration) {
        return (PaymentController<T>) super.with(context, requestConfiguration);
    }

    @Override
    public PaymentController<T> webService(int webService) {
        return (PaymentController<T>) super.webService(webService);
    }

    @Override
    public PaymentController<T> webService(@NonNull String webService) {
        return (PaymentController<T>) super.webService(webService);
    }

    @Override
    public PaymentController<T> addOptionalParam(String paramName, String paramValue) {
        return (PaymentController<T>) super.addOptionalParam(paramName, paramValue);
    }

    /**
     * Initialize listener
     *
     * @param paymentListener {@link PaymentListener} listener
     * @return {@link PaymentController} instance
     */
    public PaymentController<T> listener(PaymentListener<T> paymentListener) {
        this.mPaymentListener = paymentListener;
        return this;
    }

    /**
     * Initialize listener
     *
     * @param paymentListener {@link IPaymentListener} listener
     * @return {@link PaymentController} instance
     */
    public PaymentController<T> listener(IPaymentListener<T> paymentListener) {
        this.mIPaymentListener = paymentListener;
        return this;
    }

    /**
     * Make a new payment with the desired data
     *
     * @param paymentData          Payment data to the user want to pay
     * @param paymentResponseClass Payment data that the user want the response
     */
    public void pay(Object paymentData, final Class<T> paymentResponseClass) {
        webService(getWebService() == null ? "payment" : getWebService())
                .ofType(WebServiceType.CREATE);

        RequestBuilder requestBuilder = buildRequest();

        requestBuilder
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .jsonParams(new Gson().toJson(paymentData))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestStart();
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(e);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(requestResponse);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(em);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        T paymentResponse = null;

                        try {
                            paymentResponse = new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("cards").toString(), paymentResponseClass);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestSuccess(paymentResponse);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestSuccess(paymentResponse);
                    }
                });
        new Response().buildRequest(requestBuilder).save();
    }

    /**
     * Get a list of finished payments
     *
     * @param paymentResponseClass Generic class of response as list
     */
    public void get(final Class<T> paymentResponseClass) {
        webService(getWebService() == null ? "payment" : getWebService())
                .ofType(WebServiceType.GET);

        RequestBuilder requestBuilder = buildRequest();

        requestBuilder
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestStart();
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(e);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(requestResponse);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(em);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        List<T> paymentResponseList = null;

                        try {
                            paymentResponseList = Arrays.asList(new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("cards").toString(), paymentResponseClass));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestSuccess(paymentResponseList);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestSuccess(paymentResponseList);
                    }
                });
        new Response().buildRequest(requestBuilder).get();
    }

    /**
     * Get a list of finished payments
     *
     * @param paymentID            Payment id that the user wants
     * @param paymentResponseClass Generic class of response as list
     */
    public void get(int paymentID, final Class<T> paymentResponseClass) {
        webService(getWebService() == null ? "payment" : getWebService())
                .ofType(WebServiceType.GET);

        RequestBuilder requestBuilder = buildRequest();

        requestBuilder
                .requestTo(getWebService() + "/" + paymentID)
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestStart();
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(e);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(requestResponse);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestFailure(em);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        List<T> paymentResponseList = null;

                        try {
                            paymentResponseList = Arrays.asList(new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("cards").toString(), paymentResponseClass));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mPaymentListener != null)
                            mPaymentListener.onPaymentRequestSuccess(paymentResponseList);
                        if (mIPaymentListener != null)
                            mIPaymentListener.onPaymentRequestSuccess(paymentResponseList);
                    }
                });
        new Response().buildRequest(requestBuilder).get();
    }
}
