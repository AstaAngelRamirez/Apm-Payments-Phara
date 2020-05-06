package com.apm.payments.abstracts;

import com.apm.payments.contracts.ICardListener;
import com.apm.payments.models.Card;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 25/06/18.
 */
public abstract class CardListener implements ICardListener {
    @Override
    public void onCardRequestStart() {

    }

    @Override
    public void onCardRequestFailure(Exception e) {

    }

    @Override
    public void onCardRequestFailure(Response response) {

    }

    @Override
    public void onCardRequestFailure(ExceptionManager exceptionManager) {

    }

    @Override
    public void onCardRequestSuccess(Card card) {

    }

    @Override
    public void onCardRequestSuccess(List<Card> cardList) {

    }

    @Override
    public void onCardRequestSuccess(boolean deleted) {

    }
}
