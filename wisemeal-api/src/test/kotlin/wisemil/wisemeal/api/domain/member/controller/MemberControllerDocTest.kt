package wisemil.wisemeal.api.domain.member.controller

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import wisemil.wisemeal.api.domain.member.dto.WiseMilMemberCreateRequest
import wisemil.wisemeal.api.domain.member.facade.MemberFacade
import wisemil.wisemeal.api.test.ApiDocTest
import wisemil.wisemeal.api.test.ApiDocumentFormatGenerator.emptyFormat
import wisemil.wisemeal.api.test.ApiDocumentFormatGenerator.optional
import wisemil.wisemeal.api.test.ApiDocumentFormatGenerator.required
import wisemil.wisemeal.api.test.ApiDocumentUtil
import wisemil.wisemeal.api.test.ApiMockMvcFactory
import wisemil.wisemeal.application.domain.member.dto.WisemilMemberDto
import wisemil.wisemeal.common.mapper.WiseMealObjectMapper
import wisemil.wisemeal.core.domain.member.model.MemberRole
import wisemil.wisemeal.core.domain.member.model.MemberStatus
import wisemil.wisemeal.core.domain.member.model.SignType

@ApiDocTest
internal class MemberControllerDocTest {

    @InjectMocks
    private lateinit var controller: MemberController

    @Mock
    private lateinit var memberFacade: MemberFacade

    @Test
    fun `signUpWisemil - 회원가입 성공`(restDocumentation: RestDocumentationContextProvider) {
        //given
        val request = WiseMilMemberCreateRequest(
            email = "pci2676@gmail.com",
            password = "password",
            nickname = "없어도 됨"
        )
        val content = WiseMealObjectMapper.getDefaultMapper().writeValueAsString(request)

        val wisemilMemberDto = WisemilMemberDto(
            id = 1L,
            memberNumber = "210729FFFFFF",
            email = "pci2676@gmail.com",
            signType = SignType.WISEMIL,
            role = MemberRole.MEMBER,
            status = MemberStatus.ENABLE,
            nickname = null,
            password = "encryptedPassword")

        given(memberFacade.signUpWisemil(request)).willReturn(wisemilMemberDto)

        //when
        ApiMockMvcFactory.getRestdocsMockMvc(restDocumentation, controller)
            .perform(RestDocumentationRequestBuilders
                .post("/api/v1/members")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(content)
            )
            //then
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberNumber").value(wisemilMemberDto.memberNumber))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(MemberStatus.ENABLE.name))
            .andDo(
                MockMvcRestDocumentation.document(
                    "wisemil-member/sign-up",
                    ApiDocumentUtil.getDocumentRequest(),
                    ApiDocumentUtil.getDocumentResponse(),

                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("email")
                            .type(JsonFieldType.STRING).attributes(required(), emptyFormat())
                            .description("이메일"),
                        PayloadDocumentation.fieldWithPath("password")
                            .type(JsonFieldType.STRING).attributes(required(), emptyFormat())
                            .description("비밀번호"),
                        PayloadDocumentation.fieldWithPath("nickname")
                            .type(JsonFieldType.STRING).attributes(optional(), emptyFormat())
                            .description("닉네임")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING)
                            .attributes(emptyFormat())
                            .description("응답 코드"),
                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                            .attributes(emptyFormat())
                            .description("응답 메시지"),
                        PayloadDocumentation.fieldWithPath("data.memberNumber").type(JsonFieldType.STRING)
                            .attributes(emptyFormat())
                            .description("와이즈밀 회원번호"),
                        PayloadDocumentation.fieldWithPath("data.status").type(JsonFieldType.STRING)
                            .attributes(emptyFormat())
                            .description("계정 상태"),
                    )
                )
            )
    }
}
