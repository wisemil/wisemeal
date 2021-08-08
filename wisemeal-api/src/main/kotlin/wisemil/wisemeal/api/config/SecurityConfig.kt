package wisemil.wisemeal.api.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import wisemil.wisemeal.api.domain.security.oauth.component.HttpCookieOAuth2AuthorizationRequestRepository
import wisemil.wisemeal.api.domain.security.oauth.component.OAuthAuthenticationFailureHandler
import wisemil.wisemeal.api.domain.security.oauth.component.OAuthAuthenticationSuccessHandler
import wisemil.wisemeal.api.domain.security.oauth.service.Oauth2MemberService
import wisemil.wisemeal.api.domain.security.wisemeal.component.WisemealAccessDeniedHandler
import wisemil.wisemeal.core.domain.member.model.MemberRole


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @EnableWebSecurity
    @ConditionalOnProperty(name = ["wisemil.security.mode"], havingValue = "oauth")
    class ProdSecurityConfig(
        private val oauth2MemberService: Oauth2MemberService,
        private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    ) : WebSecurityConfigurerAdapter() {
        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                .authorizeRequests()
                .antMatchers(
                    "/docs/index.html",
                    "/api/v1/members"
                )
                .permitAll()

                .antMatchers("/api/v1/members/**").hasRole(MemberRole.MEMBER.name)
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(WisemealAccessDeniedHandler())

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
                .successHandler(OAuthAuthenticationSuccessHandler(httpCookieOAuth2AuthorizationRequestRepository))
                .failureHandler(OAuthAuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository))
        }
    }

}
