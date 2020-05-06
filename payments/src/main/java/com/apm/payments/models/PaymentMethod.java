package com.apm.payments.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/06/18.
 */
public class PaymentMethod implements Serializable {

    private int id;
    private List<Translations> translations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Translations> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translations> translations) {
        this.translations = translations;
    }

    public class Translations implements Serializable{
        private int id;
        private Pivot pivot;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }
    }

    public class Pivot implements Serializable{
        private int payment_methods_id;
        private int langs_id;
        private String method;

        public int getPaymentMethodsId() {
            return payment_methods_id;
        }

        public void setPaymentMethodsId(int paymentMethodsId) {
            this.payment_methods_id = paymentMethodsId;
        }

        public int getLangsId() {
            return langs_id;
        }

        public void setLangsId(int langsId) {
            this.langs_id = langsId;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}
