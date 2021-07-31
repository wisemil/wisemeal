package wisemil.wisemeal.application.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import wisemil.wisemeal.core.domain.member.entity.QMemberDetail.memberDetail
import wisemil.wisemeal.core.domain.member.entity.QWisemilMember.wisemilMember
import wisemil.wisemeal.core.domain.member.entity.WisemilMember
import wisemil.wisemeal.core.domain.member.model.MemberStatus
import wisemil.wisemeal.core.domain.member.model.SignType

interface WiseMilMemberRepository : JpaRepository<WisemilMember, Long>, WiseMilMemberQueryRepository {
    fun findByEmailAndSignType(email: String, signType: SignType): WisemilMember?
}

interface WiseMilMemberQueryRepository {
    fun findByMemberNumberFetch(memberNumber: String): WisemilMember?
    fun findByEnableMemberNumberFetch(memberNumber: String): WisemilMember?
}

/**
 * QueryDsl 은 구현 클래스에 Impl 을 붙여줘야 사용할 수 있다.
 */
class WiseMilMemberQueryRepositoryImpl(
    private val query: JPAQueryFactory,
) : WiseMilMemberQueryRepository {
    override fun findByMemberNumberFetch(memberNumber: String): WisemilMember? {
        return query.selectFrom(wisemilMember)
            .innerJoin(wisemilMember.memberDetail, memberDetail)
            .where(wisemilMember.memberNumber.eq(memberNumber))
            .fetchOne()
    }

    override fun findByEnableMemberNumberFetch(memberNumber: String): WisemilMember? {
        return query.selectFrom(wisemilMember)
            .innerJoin(wisemilMember.memberDetail, memberDetail)
            .where(wisemilMember.memberNumber.eq(memberNumber),
                wisemilMember.status.eq(MemberStatus.ENABLE))
            .fetchOne()
    }
}


