package com.university.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class RoleBasedTokenEnhancer implements TokenEnhancer {

	/**
	 * Enhance the token response with custom role based authoriation
	 * 
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		return accessToken;
	}

}
