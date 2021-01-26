package com.tamastudy.tama.entity

import javax.persistence.*

@Entity
data class BoardCategory(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        var id: Long? = null,
        var name: String? = null,
)