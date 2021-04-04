package io.cqrs.kt.librarymanager

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("io.cqrs")
        .start()
}
