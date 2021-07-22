package wisemil.wisemeal.application.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import wisemil.wisemeal.core.domain.member.entity.MemberSequence
import java.time.LocalDate
import javax.persistence.LockModeType

interface MemberSequenceRepository:JpaRepository<MemberSequence,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByDate(date: LocalDate):MemberSequence?
}
