package com.store.videogames.service.customer.payment;

import com.store.videogames.entites.enums.PaymentMethod;

public interface IPaymentMethodTypeFactory
{
   IPaymentService getPaymentMethodService(PaymentMethod paymentMethod);
}