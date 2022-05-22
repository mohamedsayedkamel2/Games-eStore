package com.store.videogames.config.security.oAuth2;

import com.store.videogames.repository.entites.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomAuth2Customer implements OAuth2User
{
    private OAuth2User oAuth2User;
    CustomAuth2Customer(OAuth2User oAuth2User)
    {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes()
    {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName()
    {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail()
    {
        return oAuth2User.<String>getAttribute("email");
    }

    public String getUsername()
    {
        return oAuth2User.getAttribute("name");
    }
}
