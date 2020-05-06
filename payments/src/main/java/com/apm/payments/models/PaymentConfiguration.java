package com.apm.payments.models;

import java.io.Serializable;

/**
 * Created by Ing. Oscar G. Medina Cruz on 03/07/18.
 */
public class PaymentConfiguration implements Serializable {

    private boolean productionMode = false;
    private OpenPayConfiguration openpay;
    private ConektaConfiguration conekta;
    private PaypalConfiguration paypal;

    public boolean isInProductionMode() {
        return productionMode;
    }

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;
    }

    public OpenPayConfiguration getOpenPayConfiguration() {
        return openpay;
    }

    public void setOpenPayConfiguration(OpenPayConfiguration config) {
        this.openpay = config;
    }

    public ConektaConfiguration getConektaConfiguration() {
        return conekta;
    }

    public void setConektaConfiguration(ConektaConfiguration config) {
        this.conekta = config;
    }

    public PaypalConfiguration getPaypalConfiguration() {
        return paypal;
    }

    public void setPaypalConfiguration(PaypalConfiguration config) {
        this.paypal = config;
    }

    public Configuration getOpenPayConfigurationDetails(){
        return isInProductionMode() ? getOpenPayConfiguration().getProduction() : getOpenPayConfiguration().getSandbox();
    }

    public Configuration getConektaConfigurationDetails(){
        return isInProductionMode() ? getConektaConfiguration().getProduction() : getConektaConfiguration().getSandbox();
    }

    public Configuration getPayPalConfigurationDetails(){
        return isInProductionMode() ? getPaypalConfiguration().getProduction() : getPaypalConfiguration().getSandbox();
    }

    public class OpenPayConfiguration implements Serializable{

        private Configuration production;
        private Configuration sandbox;

        public Configuration getProduction() {
            return production;
        }

        public void setProduction(Configuration production) {
            this.production = production;
        }

        public Configuration getSandbox() {
            return sandbox;
        }

        public void setSandbox(Configuration sandbox) {
            this.sandbox = sandbox;
        }
    }

    public class ConektaConfiguration implements Serializable {
        private Configuration production;
        private Configuration sandbox;

        public Configuration getProduction() {
            return production;
        }

        public void setProduction(Configuration production) {
            this.production = production;
        }

        public Configuration getSandbox() {
            return sandbox;
        }

        public void setSandbox(Configuration sandbox) {
            this.sandbox = sandbox;
        }
    }

    public class PaypalConfiguration implements Serializable{
        private Configuration production;
        private Configuration sandbox;

        public Configuration getProduction() {
            return production;
        }

        public void setProduction(Configuration production) {
            this.production = production;
        }

        public Configuration getSandbox() {
            return sandbox;
        }

        public void setSandbox(Configuration sandbox) {
            this.sandbox = sandbox;
        }
    }

    public class Configuration implements Serializable{
        private String merchantID = null;
        private String apiKey = null;
        private String apiVersion = null;
        private String clientID = null;

        public String getMerchantID() {
            return merchantID;
        }

        public void setMerchantID(String merchantID) {
            this.merchantID = merchantID;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getApiVersion() {
            return apiVersion;
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }

        public String getClientID() {
            return clientID;
        }

        public void setClientID(String clientID) {
            this.clientID = clientID;
        }
    }
}
