package wisemil.wisemeal.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import wisemil.wisemeal.api.domain.security.component.HttpCookieOAuth2AuthorizationRequestRepository
import wisemil.wisemeal.api.domain.security.component.OAuthAuthenticationFailureHandler
import wisemil.wisemeal.api.domain.security.component.OAuthAuthenticationSuccessHandler
import wisemil.wisemeal.api.domain.security.service.Oauth2MemberService
import wisemil.wisemeal.core.domain.member.model.MemberRole


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oauth2MemberService: Oauth2MemberService,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val oAuthAuthenticationSuccessHandler: OAuthAuthenticationSuccessHandler,
    private val oAuthAuthenticationFailureHandler: OAuthAuthenticationFailureHandler,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().disable()

            .and()
            .authorizeRequests()
            .antMatchers(
                "/",
                "/css/**",
                "/images/**",
                "/js/**",
                "/api/member/me"
            )
            .permitAll()

            .antMatchers("/api/member/**").hasRole(MemberRole.MEMBER.name)
            .anyRequest().authenticated()

            .and()
            .logout()
            .logoutSuccessUrl("/")

            .and()
            .oauth2Login()
            .authorizationEndpoint()
            .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)

            .and()
            .redirectionEndpoint()

            .and()
            .userInfoEndpoint()
            .userService(oauth2MemberService)

            .and()
            .successHandler(oAuthAuthenticationSuccessHandler)
            .failureHandler(oAuthAuthenticationFailureHandler)
    }
}
