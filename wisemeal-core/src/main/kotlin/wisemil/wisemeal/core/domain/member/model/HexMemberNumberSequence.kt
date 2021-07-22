package wisemil.wisemeal.core.domain.member.model

class HexMemberNumberSequence(
    private val currentSequence: Long,
) {
    init {
        if (MAX < currentSequence || currentSequence < MIN) {
            throw IllegalArgumentException("invalid sequence:$currentSequence")
        }
    }

    val hexSequence: String = hexSequence()
    val nextSequence: Long = nextSequence()

    private fun hexSequence(): String {
        return String.format(HEX_FORMAT, currentSequence)
    }

    private fun nextSequence(): Long {
        val next = currentSequence + 1
        if (next > MAX) {
            return MIN
        }
        return next
    }

    companion object {
        private const val HEX_FORMAT = "%02X"
        const val MIN = 1_048_576L
        const val MID = 8_912_895L
        const val MAX = 16_777_215L
    }
}

