package wisemil.wisemeal.common.encode

import org.springframework.util.SerializationUtils
import java.util.Base64

fun <T> String.deserializeBase64(cls: Class<T>): T {
    return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(this)))
}

fun Any.serializeBase64(): String {
    return Base64.getUrlEncoder()
        .encodeToString(SerializationUtils.serialize(this))
}
