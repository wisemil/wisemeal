package wisemil.wisemeal.core.place.entity

data class Point(
    val longitude: Double,
    val latitude: Double,
) {
    val x: Double
        get() = longitude //경도

    val y: Double
        get() = latitude //위도
}
