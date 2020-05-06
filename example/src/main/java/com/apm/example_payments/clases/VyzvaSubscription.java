package com.apm.example_payments.clases;

import com.apm.payments.models.PaymentData;

import java.io.Serializable;

/**
 * Created by Ing. Oscar G. Medina Cruz on 26/06/18.
 */
public class VyzvaSubscription extends PaymentData implements Serializable {

    private int causes_id;

    public int getCausesId() {
        return causes_id;
    }

    public void setCausesId(int causesId) {
        this.causes_id = causesId;
    }
}
