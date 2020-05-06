package com.apm.payments.models;

import java.io.Serializable;

public class PayPalResponse implements Serializable {

    //region VARS
    private Client client;
    private Response response;
    private String response_type;
    //endregion

    //region GETTERS & SETTERS
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getResponseType() {
        return response_type;
    }

    public void setResponseType(String responseType) {
        this.response_type = responseType;
    }
    //endregion

    //region INTERNAL CLASSES
    public class Client implements Serializable {
        private String environment;
        private String paypal_sdk_version;
        private String platform;
        private String product_name;

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getPayPalSDKVersion() {
            return paypal_sdk_version;
        }

        public void setPayPalSDKVersion(String payPalSDKVersion) {
            this.paypal_sdk_version = payPalSDKVersion;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getProductName() {
            return product_name;
        }

        public void setProductName(String productName) {
            this.product_name = productName;
        }
    }

    public class Response implements Serializable{
        private String create_time;
        private String id;
        private String intent;
        private String state;

        public String getCreateTime() {
            return create_time;
        }

        public void setCreateTime(String createTime) {
            this.create_time = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
    //endregion
}
