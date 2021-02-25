package com.tamastudy.tama.entity

import com.tamastudy.tama.entity.date.CommonDateEntity
import java.io.Serializable
import javax.persistence.*

@Entity
data class BoardCategory(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        var id: Long? = null,
        var name: String? = null,
): Serializable, CommonDateEntity() {
        companion object {
                @JvmStatic
                private val serialVersionUID: Long = 1
        }
}