package com.domain.enums

import javax.persistence.Entity
import javax.persistence.Table

enum class IssueType {
    BUG, TASK;

    companion object {
       operator fun invoke(type: String) = valueOf(type.uppercase())
    }
}

