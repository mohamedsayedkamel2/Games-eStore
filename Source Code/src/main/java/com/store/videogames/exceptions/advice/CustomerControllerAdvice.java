package com.store.videogames.exceptions.advice;

import com.store.videogames.exceptions.exception.*;
import com.store.videogames.exceptions.exception.UnsuccessfulRegistartion;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerControllerAdvice
{
    @ExceptionHandler(value = CustomerNotFoundException.class)
    public String CustomerNotFound(Model model)
    {
        model.addAttribute("message","Coulnd't find the username you are looking for");
        return "/error/customer_notfound";
    }

    @ExceptionHandler(value = EmailNotVerifiedException.class)
    public String saym(Model model)
    {
        model.addAttribute("message","Your account is not activated, please verify your account using the email we have sent you");
        return "/customer/login";
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public String sayd(Model model)
    {
        model.addAttribute("message","Invalid email/password");
        return "/customer/login";
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public String sayl(Model model)
    {
        model.addAttribute("message","Invalid email/password");
        return "/customer/login";
    }

    @ExceptionHandler(value = InvalidRegistrationInformationException.class)
    public String invalidRegistarationInformationHandler(Model model)
    {
        model.addAttribute("message","Invalid registration information");
        return "/customer/login";
    }

    @ExceptionHandler(value = UnsuccessfulRegistartion.class)
    public String unsuccessfulRegistrationHandler(Model model)
    {
        model.addAttribute("message", "Invalid registration operation");
        return "/customer/login";
    }

    @ExceptionHandler(value = InvalidPasswordRestToken.class)
    public String unsuccessfulPasswordChangingHandler(Model model)
    {
        model.addAttribute("message", "Email Not Found");
        return "/customer/login";
    }

    @ExceptionHandler(value = CustomerIsAlreadyEnabledException.class)
    public String customerEnabledStatusErrorHandler()
    {
        return "verify_fail";
    }
}
