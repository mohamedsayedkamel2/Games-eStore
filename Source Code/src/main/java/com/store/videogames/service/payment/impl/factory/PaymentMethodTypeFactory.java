package com.store.videogames.service.payment.impl.factory;

import com.store.videogames.entites.enums.PaymentMethod;
import com.store.videogames.service.payment.IPaymentMethodTypeFactory;
import com.store.videogames.service.payment.IPaymentService;
import com.store.videogames.service.payment.impl.strategy.DigitalPaymentServiceStrategy;
import com.store.videogames.service.payment.impl.strategy.PhysicalPaymentServiceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class PaymentMethodTypeFactory implements IPaymentMethodTypeFactory
{
    private final Map<PaymentMethod, IPaymentService> paymentMethodsMap;

    private DigitalPaymentServiceStrategy digitalPaymentServiceStrategy;
    private PhysicalPaymentServiceStrategy physicalPaymentServiceStrategy;

    @Autowired
    public PaymentMethodTypeFactory(DigitalPaymentServiceStrategy digitalPaymentServiceStrategy, PhysicalPaymentServiceStrategy physicalPaymentServiceStrategy)
    {
        this.digitalPaymentServiceStrategy = digitalPaymentServiceStrategy;
        this.physicalPaymentServiceStrategy = physicalPaymentServiceStrategy;

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
        paymentMethodsMap.put(PaymentMethod.DIGITAL, digitalPaymentServiceStrategy);
        paymentMethodsMap.put(PaymentMethod.PHYSICAL, physicalPaymentServiceStrategy);
    }
}