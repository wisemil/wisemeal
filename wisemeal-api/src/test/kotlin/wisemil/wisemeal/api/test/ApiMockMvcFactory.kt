package wisemil.wisemeal.api.test

import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import wisemil.wisemeal.common.converter.LocalDateConverter
import wisemil.wisemeal.common.customizer.JsonCustomizer

object ApiMockMvcFactory {
    fun getMockMvc(controller: Any): MockMvc {
        return getMockMvcBuilder(controller).build()
    }

    fun getRestdocsMockMvc(restDocumentation: RestDocumentationContextProvider?, controller: Any): MockMvc {
        val documentationConfigurer = MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
        documentationConfigurer.uris().withScheme("https").withHost("wisemeal-api.wisemeal.com").withPort(443)
        return getMockMvcBuilder(controller).apply<StandaloneMockMvcBuilder>(documentationConfigurer).build()
    }

    private fun getMockMvcBuilder(controller: Any): StandaloneMockMvcBuilder {
        val conversionService = DefaultFormattingConversionService()
        for (parameterBinder in listOf(LocalDateConverter())) {
            conversionService.addConverter(parameterBinder)
        }

        return MockMvcBuilders.standaloneSetup(controller)
            .setConversionService(conversionService)
            .setMessageConverters(getMessageConverter())
    }

    private fun getMessageConverter(): MappingJackson2HttpMessageConverter {
        val jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder()
        JsonCustomizer.jsonCustomizer().customize(jackson2ObjectMapperBuilder)
        return MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build())
    }
}
