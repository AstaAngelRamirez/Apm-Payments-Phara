package com.apm.example_payments.clases;

import com.apm.payments.models.PaymentResultData;

import java.io.Serializable;

/**
 * Created by Ing. Oscar G. Medina Cruz on 26/06/18.
 */
public class VyzvaSubscriptionResult extends PaymentResultData implements Serializable {

    private int causes_id;
    private int donated_percent;

    public int getCausesId() {
        return causes_id;
    }

    public void setCausesId(int causesId) {
        this.causes_id = causesId;
    }

    public int getDonatedPercent() {
        return donated_percent;
    }

    public void setDonatedPercent(int donatedPercent) {
        this.donated_percent = donatedPercent;
    }
}
