package wisemil.wisemeal.application.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import wisemil.wisemeal.core.domain.member.entity.MemberDetail

interface MemberDetailRepository : JpaRepository<MemberDetail, Long>
