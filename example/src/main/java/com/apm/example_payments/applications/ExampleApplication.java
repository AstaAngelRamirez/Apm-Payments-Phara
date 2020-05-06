package com.apm.example_payments.applications;

import android.app.Application;

import com.apm.example_payments.R;
import com.apm.request.utils.FileUtils;
import com.apm.request.utils.RequestConfiguration;
import com.google.gson.Gson;

/**
 * Created by Ing. Oscar G. Medina Cruz on 25/06/18.
 */
public class ExampleApplication extends Application {
    private RequestConfiguration mRequestConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestConfiguration = new Gson().fromJson(
                FileUtils.ReadRawTextFile(getApplicationContext(), R.raw.request),
                RequestConfiguration.class);
    }

    public RequestConfiguration getRequestConfiguration() {
        return mRequestConfiguration;
    }
}
