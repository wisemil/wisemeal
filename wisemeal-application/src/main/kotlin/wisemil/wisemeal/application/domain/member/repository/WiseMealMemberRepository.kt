package wisemil.wisemeal.application.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import wisemil.wisemeal.core.domain.member.entity.QMemberDetail.memberDetail
import wisemil.wisemeal.core.domain.member.entity.QWisemealMember.wisemealMember
import wisemil.wisemeal.core.domain.member.entity.WisemealMember
import wisemil.wisemeal.core.domain.member.model.MemberStatus
import wisemil.wisemeal.core.domain.member.model.SignType

interface WiseMealMemberRepository : JpaRepository<WisemealMember, Long>, WiseMealMemberQueryRepository {
    fun findByEmailAndSignType(email: String, signType: SignType): WisemealMember?
}

interface WiseMealMemberQueryRepository {
    fun findByEmailAndSignTypeFetch(email: String, signType: SignType): WisemealMember?

    fun findByMemberNumberFetch(memberNumber: String): WisemealMember?
    fun findByEnableMemberNumberFetch(memberNumber: String): WisemealMember?
}

/**
 * QueryDsl 은 구현 클래스에 Impl 을 붙여줘야 사용할 수 있다.
 */
class WiseMealMemberQueryRepositoryImpl(
    private val query: JPAQueryFactory,
) : WiseMealMemberQueryRepository {
    override fun findByEmailAndSignTypeFetch(email: String, signType: SignType): WisemealMember? {
        return query.selectFrom(wisemealMember)
            .innerJoin(wisemealMember.memberDetail, memberDetail)
            .where(wisemealMember.email.eq(email),
                wisemealMember.signType.eq(signType))
            .fetchOne()
    }

    override fun findByMemberNumberFetch(memberNumber: String): WisemealMember? {
        return query.selectFrom(wisemealMember)
            .innerJoin(wisemealMember.memberDetail, memberDetail)
            .where(wisemealMember.memberNumber.eq(memberNumber))
            .fetchOne()
    }

    override fun findByEnableMemberNumberFetch(memberNumber: String): WisemealMember? {
        return query.selectFrom(wisemealMember)
            .innerJoin(wisemealMember.memberDetail, memberDetail)
            .where(wisemealMember.memberNumber.eq(memberNumber),
                wisemealMember.status.eq(MemberStatus.ENABLE))
            .fetchOne()
    }
}


