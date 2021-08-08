package wisemil.wisemeal.api.domain.sign.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class WiseMealSignInRequest(
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
)
