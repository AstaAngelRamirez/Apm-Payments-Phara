package com.apm.payments.models;

import java.io.Serializable;

/**
 * Created by Ing. Oscar G. Medina Cruz on 26/06/18.
 */
public class PaymentResultData implements Serializable {

    private int id;
    private int payment_methods_id;
    private String payment_order_id;
    private float amount;
    private String origin;
    private int customers_id;
    private String updated_at;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaymentMethodId() {
        return payment_methods_id;
    }

    public void setPaymentMethodId(int paymentMethodsid) {
        this.payment_methods_id = paymentMethodsid;
    }

    public String getPaymentOrderId() {
        return payment_order_id;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.payment_order_id = paymentOrderId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getCustomersId() {
        return customers_id;
    }

    public void setCustomersId(int customersId) {
        this.customers_id = customersId;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updated_at = updatedAt;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }
}
