package wisemil.wisemeal.core.place.policy

import wisemil.wisemeal.core.place.entity.Place
import wisemil.wisemeal.core.place.entity.Point

class PlaceCreator {

    fun create(name: String, point: Point): Place {

        return Place(name, point)
    }


}
