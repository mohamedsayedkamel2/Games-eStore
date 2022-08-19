package com.store.videogames.service.customer.payment.impl.factory;

import com.store.videogames.entites.enums.PaymentMethod;
import com.store.videogames.service.customer.payment.IPaymentMethodTypeFactory;
import com.store.videogames.service.customer.payment.IPaymentService;
import com.store.videogames.service.customer.payment.impl.strategy.DigitalPaymentServiceStrategy;
import com.store.videogames.service.customer.payment.impl.strategy.PhysicalPaymentServiceStrategy;
import org.springframework.data.redis.core.index.RedisIndexDefinition;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class PaymentMethodTypeFactory implements IPaymentMethodTypeFactory
{
    private final Map<PaymentMethod, IPaymentService> paymentMethodsMap;

    public PaymentMethodTypeFactory()
    {
        paymentMethodsMap = new EnumMap<>(PaymentMethod.class);
        intialise();
    }

    public IPaymentService getPaymentMethodService(PaymentMethod paymentMethod)
    {
        if (!paymentMethodsMap.containsKey(paymentMethod) || paymentMethod == null)
        {
            throw new IllegalArgumentException("The payment method is not found");
        }
        IPaymentService paymentService = paymentMethodsMap.get(paymentMethod);
        return paymentService;
    }

    private void intialise()
    {
        paymentMethodsMap.put(PaymentMethod.DIGITAL, new DigitalPaymentServiceStrategy());
        paymentMethodsMap.put(PaymentMethod.PHYSICAL, new PhysicalPaymentServiceStrategy());
    }
}