package wisemil.wisemeal.persistence.jpa.base

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntity : VersionEntity() {
    @CreatedBy
    @Column(name = "created_by", columnDefinition = "varchar(64) comment '생성자'")
    var createdBy: String? = null
        protected set

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "datetime(6) comment '생성일시'")
    lateinit var createdAt: LocalDateTime
        protected set

    @LastModifiedBy
    @Column(name = "modified_by", columnDefinition = "varchar(64) comment '수정자'")
    var modifiedBy: String? = null
        protected set

    @LastModifiedDate
    @Column(name = "modified_at", columnDefinition = "datetime(6) comment '수정일시'")
    lateinit var modifiedAt: LocalDateTime
        protected set
}
