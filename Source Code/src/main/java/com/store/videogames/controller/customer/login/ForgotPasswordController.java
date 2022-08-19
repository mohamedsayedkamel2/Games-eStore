package com.store.videogames.controller.customer.login;

import com.store.videogames.exceptions.exception.InvalidPasswordRestToken;
import com.store.videogames.service.customer.CustomerInformationRetriverService;
import com.store.videogames.service.customer.account.CustomerPasswordChangingService;
import com.store.videogames.util.common.WebsiteUrlGetter;
import com.store.videogames.entites.Customer;
import com.store.videogames.util.EmailUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController
{
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private CustomerInformationRetriverService customerInformationRetriverService;
    @Autowired
    private CustomerPasswordChangingService customerPasswordChangingService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm()
    {
        return "/customer/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException
    {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        customerPasswordChangingService.updateResetPasswordToken(token, email);
        String resetPasswordLink = WebsiteUrlGetter.getSiteURL(request) + "/reset_password?token=" + token;
        sendEmail(email, resetPasswordLink);
        System.out.println("Email sent successfuly");
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        return "/customer/forgot_password_form";
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
        Customer customer = customerInformationRetriverService.getCustomerByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (customer == null)
        {
            model.addAttribute("message", "Invalid Token");
            return "redirect:/index";
        }
        return "/customer/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model)
    {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Customer customer = customerInformationRetriverService.getCustomerByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (customer == null)
        {
            throw new InvalidPasswordRestToken("Invalid Password Changing token");
        }
        else
        {
            customerPasswordChangingService.updatePassword(customer, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "/customer/PasswordChangeMessage";
    }
}