package com.tamastudy.tama.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(value = "server")
class ServerProperties {
    lateinit var address: String

    override fun toString(): String {
        return "ServerProperties(address='$address')"
    }


}