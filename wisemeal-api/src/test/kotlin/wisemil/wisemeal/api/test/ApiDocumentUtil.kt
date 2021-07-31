package wisemil.wisemeal.api.test

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

object ApiDocumentUtil {
    fun getDocumentRequest(): OperationRequestPreprocessor {
        return Preprocessors.preprocessRequest(
            Preprocessors.modifyUris()
                .scheme("https")
                .host("wisemeal-api.wisemeal.com")
                .removePort(),
            Preprocessors.prettyPrint(),
        )
    }

    fun getDocumentResponse(): OperationResponsePreprocessor {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
    }
}
