package com.ayadyasmine.pharmacyecom.web.rest.vm;

import com.ayadyasmine.pharmacyecom.domain.enumeration.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * View Model for checkout requests.
 */
public class CheckoutVM {

    @NotEmpty
    private String address;

    @NotNull
    private PaymentMethod paymentMethod;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}