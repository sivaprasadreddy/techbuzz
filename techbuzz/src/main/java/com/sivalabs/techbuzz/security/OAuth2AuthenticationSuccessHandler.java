package com.sivalabs.techbuzz.security;

import static com.sivalabs.techbuzz.users.domain.RoleEnum.ROLE_USER;

import com.sivalabs.techbuzz.users.domain.AuthProvider;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy;

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String provider = AuthProvider.LOCAL.name();
        if (authentication instanceof OAuth2AuthenticationToken) {
            provider =
                    ((OAuth2AuthenticationToken) authentication)
                            .getAuthorizedClientRegistrationId();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof TechBuzzUserPrincipal) {
            this.createUserIfNotExists(provider, (TechBuzzUserPrincipal) principal);
        }
        this.redirectStrategy.sendRedirect(request, response, "/");
    }

    private void createUserIfNotExists(String provider, TechBuzzUserPrincipal userPrincipal) {
        AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());
        User user = convertToUser(userPrincipal, authProvider);
        if (userService.isUserExistsByEmail(user.getEmail())) {
            log.debug("User already registered with email: {}", user.getEmail());
            return;
        }
        userService.createUser(user);
    }

    private User convertToUser(TechBuzzUserPrincipal userPrincipal, AuthProvider authProvider) {
        User user = new User();
        user.setEmail(userPrincipal.getEmail());
        user.setName(userPrincipal.getName());
        user.setAuthProvider(authProvider);
        user.setRole(ROLE_USER);
        user.setPassword("dummypwd");
        return user;
    }
}
