package com.tamastudy.tama.entity

import javax.persistence.*

@Entity
data class Board(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "board_id")
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        var category: BoardCategory? = null
)