package wisemil.wisemeal.core.domain.member.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

/**
 * memberNumber 시퀀스 테이블
 */
@Table(
    name = "member_sequence",
    indexes = [
        Index(name = "idx_member_sequence_1", columnList = "date"),
    ]
)
@Entity
class MemberSequence(
    @Column(name = "date", nullable = false, columnDefinition = "date COMMENT '생성일자'")
    val date: LocalDate,

    sequence: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
        protected set

    @Column(name = "sequence", nullable = false, columnDefinition = "bigint COMMENT 'sequence'")
    var sequence: Long = sequence
        protected set

    fun update(sequence: Long): MemberSequence {
        this.sequence = sequence
        return this
    }
}
