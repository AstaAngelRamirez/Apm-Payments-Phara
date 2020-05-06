package com.apm.payments.models;

import java.io.Serializable;

/**
 * Created by Ing. Oscar G. Medina Cruz on 25/06/18.
 */
public class Card implements Serializable {
    private int id;
    private int customers_id;
    private int payment_methods_id;
    private String payment_source_id;
    private String holder_name;
    private String brand;
    public String brand_logo;
    private String last_4;
    private int exp_month;
    private int exp_year;
    private int is_default;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private PaymentMethod payment_methods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomersId() {
        return customers_id;
    }

    public void setCustomersId(int customersId) {
        this.customers_id = customersId;
    }

    public int getPaymentMethodsId() {
        return payment_methods_id;
    }

    public void setPaymentMethodsId(int paymentMethodsId) {
        this.payment_methods_id = paymentMethodsId;
    }

    public String getPaymentSourceId() {
        return payment_source_id;
    }

    public void setPaymentSourceId(String paymentSourceId) {
        this.payment_source_id = paymentSourceId;
    }

    public String getHolderName() {
        return holder_name;
    }

    public void setHolderName(String holderName) {
        this.holder_name = holderName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandLogo() {
        return brand_logo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brand_logo = brandLogo;
    }

    public String getLast4() {
        return last_4;
    }

    public void setLast4(String last4) {
        this.last_4 = last4;
    }

    public int getExpirationMonth() {
        return exp_month;
    }

    public void setExpirationMonth(int expMonth) {
        this.exp_month = expMonth;
    }

    public int getExpirationYear() {
        return exp_year;
    }

    public void setExpirationYear(int expYear) {
        this.exp_year = expYear;
    }

    public int isDefault() {
        return is_default;
    }

    public void setDefault(int isDefault) {
        this.is_default = isDefault;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updated_at = updatedAt;
    }

    public String getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(String deletedAt) {
        this.deleted_at = deletedAt;
    }

    public PaymentMethod getPaymentMethods() {
        return payment_methods;
    }

    public void setPaymentMethods(PaymentMethod paymentMethods) {
        this.payment_methods = paymentMethods;
    }
}
