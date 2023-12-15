package com.petro.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaTemplateMultipleThreadsApplication

fun main(args: Array<String>) {
    runApplication<KafkaTemplateMultipleThreadsApplication>(*args)
}
