package com.store.videogames.util;

import javax.servlet.http.HttpServletRequest;

public class PageUrlGetter
{
    public static String getSiteURL(HttpServletRequest request)
    {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}