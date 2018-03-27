package com.university.model;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User
{
	 private static final long serialVersionUID = -3531439484732724601L;

     public CustomUserDetails(String username, String password, boolean enabled,
         boolean accountNonExpired, boolean credentialsNonExpired,
         boolean accountNonLocked,
         Collection authorities) {
             super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
     }

     public static long getSerialversionuid() {
        return serialVersionUID;
     }
}
