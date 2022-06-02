package com.store.videogames.exceptions.advice;

import com.store.videogames.exceptions.exception.CustomerNotFoundException;
import com.store.videogames.exceptions.exception.EmailNotVerifiedException;
import com.store.videogames.exceptions.exception.InvalidEmailException;
import com.store.videogames.exceptions.exception.InvalidPasswordException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerControllerAdvice
{
//    @ExceptionHandler(value = CustomerNotFoundException.class)
//    public String CustomerNotFound(Model model)
//    {
//        model.addAttribute("error_message","Coulnd't find the username you are looking for");
//        return "/error/customer_notfound";
//    }

    @ExceptionHandler(value = EmailNotVerifiedException.class)
    public String saym(Model model)
    {
        model.addAttribute("error_message","Your account is not activated, please verify your account using the email we have sent you");
        return "/login";
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public String sayd(Model model)
    {
        model.addAttribute("error_message","Invalid email/password");
        return "/login";
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public String sayl(Model model)
    {
        model.addAttribute("error_message","Invalid email/password");
        return "/login";
    }
}
