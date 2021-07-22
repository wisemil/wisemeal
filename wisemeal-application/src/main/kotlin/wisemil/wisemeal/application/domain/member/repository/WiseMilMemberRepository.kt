package wisemil.wisemeal.application.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import wisemil.wisemeal.core.domain.member.entity.WisemilMember
import wisemil.wisemeal.core.domain.member.model.SignType

interface WiseMilMemberRepository : JpaRepository<WisemilMember, Long> {
    fun findByEmailAndSignType(email: String, signType: SignType): WisemilMember?
}
