package wisemil.wisemeal.core.domain.base

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
abstract class VersionEntity {
    @Version
    @Column(name = "version", columnDefinition = "bigint comment '버전'")
    var version: Long = 0
        protected set
}
