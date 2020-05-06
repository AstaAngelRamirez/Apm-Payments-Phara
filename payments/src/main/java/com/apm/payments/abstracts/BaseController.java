package com.apm.payments.abstracts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import com.apm.request.enums.WebServiceType;
import com.apm.request.models.RequestBuilder;
import com.apm.request.utils.FileUtils;
import com.apm.request.utils.RequestConfiguration;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 08/06/18.
 */
public abstract class BaseController<T> {

    private Context mContext = null;
    private String mWebService = null;
    private Map<String, String> mOptionalParams = null;
    private WebServiceType mWebServiceType = null;

    private RequestConfiguration mRequestConfiguration = null;

    /**
     * Initialize {@link BaseController<T>} instance
     *
     * @param context Application context
     * @return {@link BaseController<T>} instance
     */
    public BaseController<T> with(@NonNull Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * Initialize {@link BaseController} instance
     *
     * @param context              Application context
     * @param requestConfiguration {@link RawRes} request configuration file
     * @return {@link BaseController} instance
     */
    public BaseController<T> with(@NonNull Context context, @RawRes int requestConfiguration) {
        return with(context,
                new Gson().fromJson(
                        FileUtils.ReadRawTextFile(
                                context, requestConfiguration), RequestConfiguration.class));
    }

    /**
     * Initialize {@link BaseController<T>} instance
     *
     * @param context              Application context
     * @param requestConfiguration {@link RequestConfiguration} instance
     * @return {@link BaseController} instance
     */
    public BaseController<T> with(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration) {
        this.mContext = context;
        this.mRequestConfiguration = requestConfiguration;
        return this;
    }

    /**
     * Initialize web service url
     *
     * @param webService Login web service string resource
     * @return {@link BaseController<T>} instance
     */
    public BaseController<T> webService(@StringRes int webService) {
        if (mContext == null)
            throw new NullPointerException("Initialize BaseController first (with)");

        return webService(mContext.getString(webService));
    }

    /**
     * Initialize web service url
     *
     * @param webService Login web service
     * @return {@link BaseController<T>} instance
     */
    public BaseController<T> webService(@NonNull String webService) {
        this.mWebService = webService;
        return this;
    }

    /**
     * Initialize web service url
     *
     * @param paramName  Optional parameter name
     * @param paramValue Optional parameter value
     * @return {@link BaseController<T>} instance
     */
    public BaseController<T> addOptionalParam(String paramName, String paramValue) {
        if (mOptionalParams == null) mOptionalParams = new HashMap<>();
        mOptionalParams.put(paramName, paramValue);
        return this;
    }

    /**
     * Establishes the web service type that we want to request
     *
     * @param webServiceType {@link WebServiceType} type
     * @return {@link BaseController<T>} instance
     */
    public BaseController<T> ofType(WebServiceType webServiceType) {
        this.mWebServiceType = webServiceType;
        return this;
    }

    /**
     * Builds an initial {@link RequestBuilder} with initial values
     *
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder buildRequest() {
        RequestBuilder requestBuilder = null;

        if (getRequestConfiguration() != null)
            requestBuilder = new RequestBuilder().with(getContext(), getRequestConfiguration());
        else
            requestBuilder = new RequestBuilder().with(getContext());

        requestBuilder
                .requestTo(getWebService())
                .ofType(getWebServiceType());

        if (getOptionalParams() != null) {
            for (Map.Entry<String, String> entry : getOptionalParams().entrySet()) {
                requestBuilder.addParam(entry.getKey(), entry.getValue());
            }
        }

        return requestBuilder;
    }

    /**
     * Builds an initial {@link RequestBuilder} with initial values
     * @param concatContent         String to concat to final web service url
     * @return                      {@link RequestBuilder} instance
     */
    public RequestBuilder buildRequest(String concatContent) {
        RequestBuilder requestBuilder = null;

        if (getRequestConfiguration() != null)
            requestBuilder = new RequestBuilder().with(getContext(), getRequestConfiguration());
        else
            requestBuilder = new RequestBuilder().with(getContext());

        requestBuilder
                .requestTo(getWebService())
                .ofType(getWebServiceType())
                .concatToRequest(concatContent);

        if (getOptionalParams() != null) {
            for (Map.Entry<String, String> entry : getOptionalParams().entrySet()) {
                requestBuilder.addParam(entry.getKey(), entry.getValue());
            }
        }

        return requestBuilder;
    }

    /**
     * Builds an initial {@link RequestBuilder} with initial values
     * @param concatContent         String array to concat to final web service url
     * @return                      {@link RequestBuilder} instance
     */
    public RequestBuilder buildRequest(String... concatContent) {
        RequestBuilder requestBuilder = null;

        if (getRequestConfiguration() != null)
            requestBuilder = new RequestBuilder().with(getContext(), getRequestConfiguration());
        else
            requestBuilder = new RequestBuilder().with(getContext());

        requestBuilder
                .requestTo(getWebService())
                .ofType(getWebServiceType())
                .concatToRequest(concatContent);

        if (getOptionalParams() != null) {
            for (Map.Entry<String, String> entry : getOptionalParams().entrySet()) {
                requestBuilder.addParam(entry.getKey(), entry.getValue());
            }
        }

        return requestBuilder;
    }

    //region GETTERS

    public Context getContext() {
        return mContext;
    }

    public String getWebService() {
        return mWebService;
    }

    public Map<String, String> getOptionalParams() {
        return mOptionalParams;
    }

    public WebServiceType getWebServiceType() {
        return mWebServiceType;
    }

    public RequestConfiguration getRequestConfiguration() {
        return mRequestConfiguration;
    }

    //endregion
}
