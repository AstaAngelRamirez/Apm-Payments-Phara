package com.apm.payments.models;

import android.content.Context;
import android.support.annotation.StringRes;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.ShippingAddress;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PayPalPaymentBuilder {
    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     * <p>
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     * <p>
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */

    //region VARS
    private PayPalConfiguration mPayPalConfiguration = new PayPalConfiguration();
    private PayPalPayment mPayPalPayment;
    private List<PayPalItem> mPayPalItems;
    //endregion

    //region BUILDER METHODS

    /**
     * Load PayPal configuration from {@link PaymentConfiguration} instance
     *
     * @param paymentConfiguration {@link PaymentConfiguration} instance
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder loadPaymentConfiguration(PaymentConfiguration paymentConfiguration) {
        return loadPaymentConfiguration(paymentConfiguration.isInProductionMode(),
                paymentConfiguration.getPayPalConfigurationDetails().getClientID());
    }

    /**
     * Load PayPal configuration validating if is in production mode and receive the PayPal client id.
     *
     * @param context        Application context
     * @param productionMode If the process is in production mode
     * @param clientID       The PayPal client id.
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder loadPaymentConfiguration(Context context, boolean productionMode, @StringRes int clientID) {
        return loadPaymentConfiguration(productionMode, context.getString(clientID));
    }

    /**
     * Load PayPal configuration validation if is in production mode and receive the PayPal client id.
     *
     * @param productionMode If the procces is in production mode
     * @param clientID       The PayPal client id.
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder loadPaymentConfiguration(boolean productionMode, String clientID) {
        mPayPalConfiguration
                .environment(
                        productionMode ? PayPalConfiguration.ENVIRONMENT_PRODUCTION
                                : PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(clientID);

        return this;
    }

    /**
     * Add {@link PayPalItem} as product to the payment list.
     *
     * @param productName Product name to add.
     * @param quantity    Quantity of that product.
     * @param price       Price for product unit.
     * @param currency    Currency of the product.
     * @param sku         Sku for product. (Product id).
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder addProduct(String productName, int quantity, double price, String currency, String sku) {
        return addProduct(new PayPalItem(productName, quantity,
                new BigDecimal(price).setScale(2, RoundingMode.CEILING),
                currency, sku));
    }

    /**
     * Add {@link PayPalItem} as product to the payment list.
     *
     * @param payPalItem {@link PayPalItem} to add.
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder addProduct(PayPalItem payPalItem) {
        if (mPayPalItems == null) mPayPalItems = new ArrayList<>();
        mPayPalItems.add(payPalItem);

        return this;
    }

    /**
     * Add an array of {@link PayPalItem}
     *
     * @param payPalItems Array of {@link PayPalItem}
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder addProducts(PayPalItem... payPalItems) {
        return addProducts(Arrays.asList(payPalItems));
    }

    /**
     * Add an array of {@link PayPalItem}
     *
     * @param payPalItems Array of {@link PayPalItem}
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder addProducts(List<PayPalItem> payPalItems) {
        if (mPayPalItems == null) mPayPalItems = new ArrayList<>();
        mPayPalItems.addAll(payPalItems);

        return this;
    }

    /**
     * Build the {@link PayPalPayment} to send to PayPal SDK.
     *
     * @param shipping         Shipping price for payment.
     * @param tax              Tax for payment. (impuesto)
     * @param currency         Currency for the payment.
     * @param shortDescription Short description for this payment.
     * @return {@link PayPalPaymentBuilder} instance
     */
    public PayPalPaymentBuilder build(double shipping, double tax, String currency, String shortDescription) {
        if (mPayPalItems == null || mPayPalItems.size() == 0) {
            throw new RuntimeException("You must call addProduct method first build payment");
        }

        PayPalItem[] payPalItems = mPayPalItems.toArray(new PayPalItem[mPayPalItems.size()]);

        BigDecimal subtotal = PayPalItem.getItemTotal(payPalItems)
                .setScale(2, RoundingMode.CEILING);

        PayPalPaymentDetails paymentDetails =
                new PayPalPaymentDetails(
                        new BigDecimal(shipping).setScale(2, RoundingMode.CEILING),
                        subtotal,
                        new BigDecimal(tax).setScale(2, RoundingMode.CEILING));

        BigDecimal amount =
                subtotal.add(
                        new BigDecimal(shipping).setScale(2, RoundingMode.CEILING))
                        .add(new BigDecimal(tax).setScale(2, RoundingMode.CEILING));

        mPayPalPayment = new PayPalPayment(amount, currency,
                shortDescription, PayPalPayment.PAYMENT_INTENT_SALE);
        mPayPalPayment.items(payPalItems).paymentDetails(paymentDetails);

        mPayPalItems.clear();

        return this;
    }

    /**
     * Enable retrieval of shipping addresses from buyer's PayPal account
     *
     * @param enable Enable value
     * @return {@link PayPalPaymentBuilder} instance
     */
    private PayPalPaymentBuilder enableShippingAddressRetrieval(boolean enable) {
        if (mPayPalPayment != null) {
            mPayPalPayment.enablePayPalShippingAddressesRetrieval(enable);
        } else {
            throw new RuntimeException("You must first build PayPalPaymentBuilder");
        }
        return this;
    }

    /**
     * Add app-provided shipping address to payment
     *
     * @param recipientName      Recipient name.
     * @param streetAddressLine1 The first line of the street address (Required)
     * @param streetAddressLine2 The second line of the street address (Optional)
     * @param city               City of the shipping address.
     * @param state              State of the shipping address.
     * @param postalCode         Postal code of the shipping address.
     * @param countryCode        Country code of the shippiing address.
     * @return {@link PayPalPaymentBuilder} instance
     */
    private PayPalPaymentBuilder addShippingAddress(String recipientName, String streetAddressLine1,
                                                    String streetAddressLine2, String city, String state,
                                                    String postalCode, String countryCode) {
        if (mPayPalPayment != null) {
            mPayPalPayment.providedShippingAddress(
                    new ShippingAddress()
                            .recipientName(recipientName)
                            .line1(streetAddressLine1)
                            .line2(streetAddressLine2)
                            .city(city)
                            .state(state)
                            .postalCode(postalCode)
                            .countryCode(countryCode));
        } else {
            throw new RuntimeException("You must first build PayPalPaymentBuilder");
        }

        return this;
    }
    //endregion

    //region GETTERS
    public PayPalConfiguration getPayPalConfiguration() {
        return mPayPalConfiguration;
    }

    public PayPalPayment getPayPalPayment() {
        return mPayPalPayment;
    }

    public List<PayPalItem> getPayPalItems() {
        return mPayPalItems;
    }
    //endregion

}
