package com.joint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JointApplication

fun main(args: Array<String>) {
    runApplication<JointApplication>(*args)
}
