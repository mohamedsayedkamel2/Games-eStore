package com.store.videogames.config.security.oAuth2;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.enums.AuthenticationProvider;
import com.store.videogames.service.customer.CustomerService;
import com.store.videogames.service.customer.account.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessfultHandler extends SimpleUrlAuthenticationSuccessHandler
{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        CustomAuth2Customer customAuth2Customer = (CustomAuth2Customer) authentication.getPrincipal();
        String name = customAuth2Customer.getName();
        String email = customAuth2Customer.getEmail();
        Customer customer = customerService.getCustomerbyEmail(email);
        if (customer == null)
        {
            customerRegistrationService.registerCustomerOAuth(email, name, AuthenticationProvider.GOOGLE);
        }
        else
        {
            customerRegistrationService.updateCustomerOAuth(customer, name, AuthenticationProvider.GOOGLE);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
