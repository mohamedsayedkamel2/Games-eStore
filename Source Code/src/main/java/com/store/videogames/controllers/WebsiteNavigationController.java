package com.store.videogames.controllers;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.services.CustomerService;
import com.store.videogames.util.interfaces.EmailUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class WebsiteNavigationController
{
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName() + " " + authentication.getAuthorities() + " " + authentication.getDetails());
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";
        }
        return "index";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm()
    {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException
    {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        customerService.updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        sendEmail(email, resetPasswordLink);
        System.out.println("Email sent successfuly");
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        return "forgot_password_form";
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException
    {
        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        emailUtil.sendEmail(recipientEmail,subject,content);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model)
    {
        Customer customer = customerService.getCustomerByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (customer == null)
        {
            model.addAttribute("message", "Invalid Token");
            return "index";
        }
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model)
    {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Customer customer = customerService.getCustomerByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (customer == null)
        {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        else
        {
            customerService.updatePassword(customer, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "message";
    }
}
