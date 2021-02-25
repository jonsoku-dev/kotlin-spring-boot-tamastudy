package com.tamastudy.tama.advice.exception

import java.lang.RuntimeException

class ValidateRefreshTokenException: RuntimeException {
    constructor() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}