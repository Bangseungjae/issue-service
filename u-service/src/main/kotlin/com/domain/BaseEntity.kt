package com.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity (

    @CreatedDate
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @LastModifiedBy
    var updatedAt: LocalDateTime? = LocalDateTime.now(),
    )