package com.store.videogames.service.payment;

import com.store.videogames.entites.enums.PaymentMethod;

public interface IPaymentMethodTypeFactory
{
   IPaymentService getPaymentMethodService(PaymentMethod paymentMethod);
}