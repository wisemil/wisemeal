package wisemil.wisemeal.application.domain.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wisemil.wisemeal.application.domain.member.repository.MemberSequenceRepository
import wisemil.wisemeal.common.format.LocalDateExtensions.CUT_YEAR_SHORT_DATE_FORMAT
import wisemil.wisemeal.core.domain.member.entity.MemberSequence
import wisemil.wisemeal.core.domain.member.model.HexMemberNumberSequence
import java.time.LocalDate

@Service
@Transactional
class MemberNumberGenerator(
    private val sequenceRepository: MemberSequenceRepository,
) {
    fun generate(date: LocalDate): String {
        val now = date.format(CUT_YEAR_SHORT_DATE_FORMAT)
        val sequence = sequenceRepository.findByDate(date)
            ?: let { sequenceRepository.save(MemberSequence(date = date, sequence = HexMemberNumberSequence.MID)) }

        val hexSequence = HexMemberNumberSequence(sequence.sequence)

        sequence.update(hexSequence.nextSequence)

        return "$now${hexSequence.hexSequence}"
    }
}
