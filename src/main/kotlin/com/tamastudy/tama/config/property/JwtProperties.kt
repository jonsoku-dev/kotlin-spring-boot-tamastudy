package com.tamastudy.tama.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
        var secretKey: String? = null,
        var accessMaxAge: Int? = null,
        var refreshMaxAge: Int? = null,
)