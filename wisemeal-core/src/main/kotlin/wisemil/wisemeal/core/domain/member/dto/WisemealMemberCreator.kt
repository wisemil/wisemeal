package wisemil.wisemeal.core.domain.member.dto

import wisemil.wisemeal.core.domain.member.entity.MemberDetail
import wisemil.wisemeal.core.domain.member.entity.WisemealMember
import wisemil.wisemeal.core.domain.member.model.SignType

data class WisemealMemberCreator(
    val email: String,
    val signType: SignType,
    val nickName: String? = null,
    val rawPassword: String? = null,
) {
    fun toWisemilMember(
        memberNumber: String,
        encryptedPasswordProvider: (String) -> String? = { null },
    ): WisemealMember {
        return WisemealMember(
            email = email,
            signType = signType,
            memberNumber = memberNumber,
            memberDetail = MemberDetail(
                encryptedPassword = rawPassword?.let(encryptedPasswordProvider),
                nickName = nickName,
            )
        )
    }
}
