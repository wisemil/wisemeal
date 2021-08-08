package wisemil.wisemeal.core.domain.member.entity

import wisemil.wisemeal.core.domain.base.BaseEntity
import wisemil.wisemeal.core.domain.member.model.MemberRole
import wisemil.wisemeal.core.domain.member.model.SignType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 * wisemil 회원
 */
@Table(
    name = "wisemeal_member",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_wisemeal_1", columnNames = ["member_number"]),
        UniqueConstraint(name = "uk_wisemeal_2", columnNames = ["email", "sign_type"]),
    ]
)
@Entity
class WisemealMember(
    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(100) COMMENT 'email'")
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_type", nullable = false, columnDefinition = "VARCHAR(30) COMMENT '가입유형'")
    val signType: SignType,

    @Column(name = "member_number", nullable = false, columnDefinition = "VARCHAR(32) COMMENT '회원번호'")
    val memberNumber: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_detail_id", columnDefinition = "BIGINT COMMENT '회원상세 id'")
    val memberDetail: MemberDetail,

    role: MemberRole = MemberRole.MEMBER,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '사용자 권한'")
    var role: MemberRole = role
        protected set

}
