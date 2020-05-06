package com.apm.payments.contracts;

import com.apm.payments.models.Card;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 25/06/18.
 */
public interface ICardListener {
    void onCardRequestStart();
    void onCardRequestSuccess(List<Card> cardList);
    void onCardRequestSuccess(Card card);
    void onCardRequestSuccess(boolean deleted);
    void onCardRequestFailure(Response response);
    void onCardRequestFailure(Exception e);
    void onCardRequestFailure(ExceptionManager exceptionManager);
}
