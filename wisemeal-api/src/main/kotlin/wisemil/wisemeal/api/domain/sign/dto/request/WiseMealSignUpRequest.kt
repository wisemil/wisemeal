package wisemil.wisemeal.api.domain.sign.dto.request

import wisemil.wisemeal.core.domain.member.dto.WisemealMemberCreator
import wisemil.wisemeal.core.domain.member.model.SignType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class WiseMealSignUpRequest(
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,

    val nickname: String?,
) {
    fun toWiseMealMemberCreator(): WisemealMemberCreator {
        return WisemealMemberCreator(
            email = email,
            signType = SignType.WISEMEAL,
            nickName = nickname,
            rawPassword = password
        )
    }
}
