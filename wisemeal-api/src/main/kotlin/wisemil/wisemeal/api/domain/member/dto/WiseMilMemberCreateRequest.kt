package wisemil.wisemeal.api.domain.member.dto

import wisemil.wisemeal.core.domain.member.dto.WisemilMemberCreator
import wisemil.wisemeal.core.domain.member.model.SignType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class WiseMilMemberCreateRequest(
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,

    val nickname: String?,
) {
    fun toWiseMilMemberCreator(): WisemilMemberCreator {
        return WisemilMemberCreator(
            email = email,
            signType = SignType.WISEMIL,
            nickName = nickname,
            password = password
        )
    }
}
