package wisemil.wisemeal.common.collection

fun <T : Any> T.toList():List<T>{
    return listOf(this)
}
