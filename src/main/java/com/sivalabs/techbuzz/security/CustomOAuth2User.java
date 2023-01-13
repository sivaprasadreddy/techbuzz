package com.sivalabs.techbuzz.security;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User extends DefaultOAuth2User implements TechBuzzUserPrincipal {

	public CustomOAuth2User(OAuth2User oAuth2User) {
		super(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "name");
	}

	@Override
	public String getId() {
		return this.getAttribute("id");
	}

	@Override
	public String getEmail() {
		return this.getAttribute("email");
	}

}
