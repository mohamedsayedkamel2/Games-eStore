package com.store.videogames.util;

import javax.mail.MessagingException;

public interface EmailUtil
{
    void sendEmail(String toEmailAddress, String subject, String body) throws MessagingException;
}
