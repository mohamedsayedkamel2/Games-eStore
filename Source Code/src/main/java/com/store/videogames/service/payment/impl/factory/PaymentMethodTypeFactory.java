package com.store.videogames.service.payment.impl.factory;

import com.store.videogames.entites.enums.PaymentMethod;
import com.store.videogames.service.payment.IPaymentService;
import com.store.videogames.service.payment.impl.strategy.DigitalPaymentServiceStrategy;
import com.store.videogames.service.payment.impl.strategy.PhysicalPaymentServiceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * Note: This factory method is unfortunatley will scale horizitonally with the number of payment service strategies
 * I unfortunatley discovered this problem later and it's because of the tight-coupling to the IOC container
 * tight-coupling to the IOC container means that I cannot make an instance of DigitalPaymentServiceStrategy as an example without
 * using the IOC container and this is because of choosing Spring Data JPA
 * Lession learned: try not to couple the software system to a particular framework
 */
@Service
public class PaymentMethodTypeFactory
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