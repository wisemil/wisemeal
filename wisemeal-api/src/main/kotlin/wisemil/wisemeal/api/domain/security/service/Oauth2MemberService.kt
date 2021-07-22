package wisemil.wisemeal.api.domain.security.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import wisemil.wisemeal.api.domain.member.service.MemberSignUpService
import wisemil.wisemeal.api.domain.security.dto.OAuthAttribute
import wisemil.wisemeal.api.domain.security.dto.SessionMember
import wisemil.wisemeal.api.domain.security.dto.SessionMember.Companion.SESSION_KEY
import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto
import wisemil.wisemeal.application.domain.member.repository.WiseMilMemberRepository
import wisemil.wisemeal.common.collection.toList
import wisemil.wisemeal.core.domain.member.model.SignType
import javax.servlet.http.HttpSession

@Service
class Oauth2MemberService(
    private val memberRepository: WiseMilMemberRepository,
    private val memberSignUpService: MemberSignUpService,
    private val session: HttpSession,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(request: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauthUser = delegate.loadUser(request)

        val registrationId = request.clientRegistration.registrationId
        val useNameAttributeName = request.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        val attribute = OAuthAttribute.from(registrationId, useNameAttributeName, oauthUser.attributes)
        val member = mergeMember(attribute)

        session.setAttribute(SESSION_KEY, SessionMember.from(member))

        return DefaultOAuth2User(
            SimpleGrantedAuthority(member.role.name).toList(),
            attribute.attributes,
            attribute.nameAttributeKey
        )
    }

    private fun mergeMember(attribute: OAuthAttribute): WisemilMemberDto {
        return memberRepository.findByEmailAndSignType(attribute.email, SignType.GOOGLE)
            ?.let { WisemilMemberDto.from(it) }
            ?: memberSignUpService.signUp(
                email = attribute.email,
                signType = SignType.GOOGLE
            )
    }
}
