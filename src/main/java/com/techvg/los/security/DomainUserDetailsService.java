package com.techvg.los.security;

import com.techvg.los.domain.SecurityUser;
import com.techvg.los.repository.SecurityUserRepository;
import com.techvg.los.service.dto.LoginUserDTO;
import com.techvg.los.service.mapper.LoginUserMapper;
import com.techvg.los.web.rest.errors.NotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final SecurityUserRepository userRepository;
    private final LoginUserMapper loginUserMapper;

    public DomainUserDetailsService(SecurityUserRepository userRepository, LoginUserMapper loginUserMapper) {
        this.userRepository = userRepository;
        this.loginUserMapper = loginUserMapper;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        /*
         * if (new EmailValidator().isValid(login, null)) { return userRepository
         * .findOneWithAuthoritiesByEmailIgnoreCase(login) .map(user ->
         * createSpringSecurityUser(login, user)) .orElseThrow(() -> new
         * UsernameNotFoundException("User with email " + login +
         * " was not found in the database")); }
         */

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneByUsername(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, SecurityUser user) {
        if (!user.getIsActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        LoginUserDTO dto = loginUserMapper.toDto(user);
        List<GrantedAuthority> grantedAuthorities = dto
            .getAuthorities()
            .stream()
            .map(permision -> new SimpleGrantedAuthority(permision))
            .collect(Collectors.toList());
        if (grantedAuthorities.isEmpty()) {
            throw new NotFoundException("User " + lowercaseLogin + " has no Role or Permissions");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(), grantedAuthorities);
    }
}
