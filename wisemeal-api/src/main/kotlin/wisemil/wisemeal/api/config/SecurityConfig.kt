package wisemil.wisemeal.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import wisemil.wisemeal.api.domain.security.service.Oauth2MemberService
import wisemil.wisemeal.core.domain.member.model.MemberRole


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oauth2MemberService: Oauth2MemberService,
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
            .userInfoEndpoint()
            .userService(oauth2MemberService)
    }
}
