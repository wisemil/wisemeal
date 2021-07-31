package wisemil.wisemeal.core.domain.member.dto

import wisemil.wisemeal.core.domain.member.entity.MemberDetail
import wisemil.wisemeal.core.domain.member.entity.WisemilMember
import wisemil.wisemeal.core.domain.member.model.SignType

data class WisemilMemberCreator(
    val email: String,
    val signType: SignType,
    val nickName: String? = null,
    val password: String? = null,
) {
    fun toWisemilMember(
        memberNumber: String,
        encryptedPasswordProvider: (String) -> String? = { null },
    ): WisemilMember {
        return WisemilMember(
            email = email,
            signType = signType,
            memberNumber = memberNumber,
            memberDetail = MemberDetail(
                encryptedPassword = password?.let(encryptedPasswordProvider),
                nickName = nickName,
            )
        )
    }
}
