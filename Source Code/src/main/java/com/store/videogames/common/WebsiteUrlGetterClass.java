package com.store.videogames.common;

import javax.servlet.http.HttpServletRequest;

public class WebsiteUrlGetterClass
{
    public static String getSiteURL(HttpServletRequest request)
    {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
