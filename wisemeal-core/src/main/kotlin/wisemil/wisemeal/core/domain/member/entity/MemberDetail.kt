package wisemil.wisemeal.core.domain.member.entity

import wisemil.wisemeal.core.domain.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 회원 세부 내용
 *
 * 회원 : 세부내용 = 1 : 1
 */
@Table(name = "wisemil_member_detail")
@Entity
class MemberDetail(
    encryptedPassword: String? = null,
    nickName: String? = null,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null
        protected set

    @Column(name = "password", nullable = true, columnDefinition = "VARCHAR(300) COMMENT '암호화 된 비밀번호'")
    var encryptedPassword: String? = encryptedPassword
        protected set


    @Column(name = "nickname", nullable = true, columnDefinition = "VARCHAR(32) COMMENT '닉네임'")
    var nickname: String? = nickName
        protected set
}
