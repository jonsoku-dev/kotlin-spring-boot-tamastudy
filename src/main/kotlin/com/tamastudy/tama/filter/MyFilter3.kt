package com.tamastudy.tama.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MyFilter3 : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest;
        val res = response as HttpServletResponse;

        // token: example-token
        if (req.method == "POST") {
            val headerAuth = req.getHeader("Authorization")
            if (headerAuth == "hello") {
                chain.doFilter(req, res)
            } else {
                res.writer.println("인증안됨")
            }
        }
    }
}