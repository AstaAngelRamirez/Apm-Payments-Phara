package com.apm.payments.http.controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.apm.payments.abstracts.BaseController;
import com.apm.payments.abstracts.CardListener;
import com.apm.payments.contracts.ICardListener;
import com.apm.payments.models.Card;
import com.apm.payments.models.PaymentMethod;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 25/06/18.
 */
public class CardController extends BaseController {

    private CardListener mCardListener;
    private ICardListener mICardListener;

    @Override
    public CardController with(@NonNull Context context) {
        return (CardController) super.with(context);
    }

    @Override
    public CardController with(@NonNull Context context, int requestConfiguration) {
        return (CardController) super.with(context, requestConfiguration);
    }

    @Override
    public CardController with(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration) {
        return (CardController) super.with(context, requestConfiguration);
    }

    @Override
    public CardController webService(int webService) {
        return (CardController) super.webService(webService);
    }

    @Override
    public CardController webService(@NonNull String webService) {
        return (CardController) super.webService(webService);
    }

    @Override
    public CardController addOptionalParam(String paramName, String paramValue) {
        return (CardController) super.addOptionalParam(paramName, paramValue);
    }

    /**
     * Initialize listener
     *
     * @param cardListener {@link CardListener} listener
     * @return {@link CardController} instance
     */
    public CardController listener(CardListener cardListener) {
        this.mCardListener = cardListener;
        return this;
    }

    /**
     * Initialize listener
     *
     * @param cardListener {@link ICardListener} listener
     * @return {@link CardController} instance
     */
    public CardController listener(ICardListener cardListener) {
        this.mICardListener = cardListener;
        return this;
    }

    /**
     * Create a new card from {@link Card} information
     *
     * @param paymentMethodID   {@link PaymentMethod} id
     * @param cardToken         {@link mx.openpay.android.Openpay} generated card token
     * @param deviceSessionID   {@link mx.openpay.android.Openpay} generated device session ID
     * @param isDefault         Determine if the card is default one
     */
    public void create(int paymentMethodID, String cardToken, String deviceSessionID, boolean isDefault){
        webService(getWebService() == null ? "cards" : getWebService())
                .ofType(WebServiceType.CREATE);

        RequestBuilder requestBuilder = buildRequest();

        requestBuilder
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .addParam("method", String.valueOf(paymentMethodID))
                .addParam("token", cardToken)
                .addParam("device_session_id", deviceSessionID)
                .addParam("is_default", String.valueOf(isDefault ? 1 : 0))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mCardListener != null)
                            mCardListener.onCardRequestStart();
                        if (mICardListener != null)
                            mICardListener.onCardRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(e);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(requestResponse);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(em);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        Card card = null;

                        try {
                            card = new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("cards").toString(), Card.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mCardListener != null)
                            mCardListener.onCardRequestSuccess(card);
                        if (mICardListener != null)
                            mICardListener.onCardRequestSuccess(card);
                    }
                });
        new Response().buildRequest(requestBuilder).save();
    }


    /**
     * Delete an existing {@link Card}
     * @param card      {@link Card} that will be deleted
     */
    public void delete(Card card){
        webService(getWebService() == null ? "cards" : getWebService())
                .ofType(WebServiceType.DELETE);

        RequestBuilder requestBuilder = buildRequest(String.valueOf(card.getId()));

        requestBuilder
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mCardListener != null)
                            mCardListener.onCardRequestStart();
                        if (mICardListener != null)
                            mICardListener.onCardRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(e);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(requestResponse);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(em);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        boolean deleted = false;
                        try {
                            deleted = response.getData(JSONObject.class)
                                    .getBoolean("deleted");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mCardListener != null)
                            mCardListener.onCardRequestSuccess(deleted);
                        if (mICardListener != null)
                            mICardListener.onCardRequestSuccess(deleted);
                    }
                });
        new Response().buildRequest(requestBuilder).delete();
    }

    /**
     * Get a list of available {@link Card}
     */
    public void get(){
        webService(getWebService() == null ? "cards" : getWebService())
                .ofType(WebServiceType.GET);

        RequestBuilder requestBuilder = buildRequest();

        requestBuilder
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mCardListener != null)
                            mCardListener.onCardRequestStart();
                        if (mICardListener != null)
                            mICardListener.onCardRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(e);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(requestResponse);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(em);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        List<Card> cardList = null;

                        try {
                            cardList = Arrays.asList(new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("cards").toString(), Card[].class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mCardListener != null)
                            mCardListener.onCardRequestSuccess(cardList);
                        if (mICardListener != null)
                            mICardListener.onCardRequestSuccess(cardList);
                    }
                });
        new Response().buildRequest(requestBuilder).get();
    }

    /**
     * Get information about selected card
     * @param cardId        Selected card id
     */
    public void get(int cardId){
        webService(getWebService() == null ? "cards" : getWebService())
                .ofType(WebServiceType.GET);

        RequestBuilder requestBuilder = buildRequest("/" + String.valueOf(cardId));

        requestBuilder
                .requestToken(TokenUtils.GetUserToken(getContext()))
                .listener(new RequestListener<Response>() {
                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();

                        if (mCardListener != null)
                            mCardListener.onCardRequestStart();
                        if (mICardListener != null)
                            mICardListener.onCardRequestStart();
                    }

                    @Override
                    public void onRequestError(Exception e) {
                        super.onRequestError(e);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(e);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(e);
                    }

                    @Override
                    public void onRequestError(Response requestResponse) {
                        super.onRequestError(requestResponse);

                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(requestResponse);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(requestResponse);
                    }

                    @Override
                    public void onRequestError(ExceptionManager em) {
                        super.onRequestError(em);
                        if (mCardListener != null)
                            mCardListener.onCardRequestFailure(em);
                        if (mICardListener != null)
                            mICardListener.onCardRequestFailure(em);
                    }

                    @Override
                    public void onRequestEnd(Response response, String currentToken) {
                        super.onRequestEnd(response, currentToken);

                        List<Card> cardList = null;

                        try {
                            cardList = Collections.singletonList(new Gson().fromJson(response.getData(JSONObject.class)
                                    .getJSONArray("cards").toString(), Card.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (mCardListener != null)
                            mCardListener.onCardRequestSuccess(cardList);
                        if (mICardListener != null)
                            mICardListener.onCardRequestSuccess(cardList);
                    }
                });
        new Response().buildRequest(requestBuilder).get();
    }
}
