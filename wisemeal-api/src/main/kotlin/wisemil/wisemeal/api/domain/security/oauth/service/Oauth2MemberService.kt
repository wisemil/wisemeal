package wisemil.wisemeal.api.domain.security.oauth.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import wisemil.wisemeal.api.domain.security.oauth.dto.OAuthAttribute
import wisemil.wisemeal.api.domain.security.wisemeal.dto.SessionMember.Companion.SESSION_KEY
import wisemil.wisemeal.api.domain.sign.service.SignService
import wisemil.wisemeal.application.domain.member.dto.WisemealMemberDto
import wisemil.wisemeal.application.domain.member.repository.WiseMealMemberRepository
import wisemil.wisemeal.common.collection.toList
import wisemil.wisemeal.core.domain.member.dto.WisemealMemberCreator

@Service
class Oauth2MemberService(
    private val memberRepository: WiseMealMemberRepository,
    private val signService: SignService,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(request: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauthUser = delegate.loadUser(request)

        val registrationId = request.clientRegistration.registrationId
        val useNameAttributeName = request.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        val attribute = OAuthAttribute.from(registrationId, useNameAttributeName, oauthUser.attributes)
        val wisemealMemberDto = mergeMember(attribute)
        attribute.addAttribute(SESSION_KEY, wisemealMemberDto.memberNumber)

        return DefaultOAuth2User(
            SimpleGrantedAuthority(wisemealMemberDto.role.name).toList(),
            attribute.attributes,
            attribute.nameAttributeKey
        )
    }

    private fun mergeMember(attribute: OAuthAttribute): WisemealMemberDto {
        return memberRepository.findByEmailAndSignType(attribute.email, attribute.signType)
            ?.let { WisemealMemberDto.from(it) }
            ?: signService.signUp(
                WisemealMemberCreator(
                    email = attribute.email,
                    signType = attribute.signType,
                )
            )
    }
}
