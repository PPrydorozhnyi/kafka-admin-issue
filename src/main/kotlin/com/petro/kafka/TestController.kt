package com.petro.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@RequestMapping("/test")
@RestController
class TestController(
    private val template: KafkaTemplate<String, Command>,
    @Value("\${spring.kafka.topics.in}") private val inTopic: String,
) {
    private val executorService = Executors.newVirtualThreadPerTaskExecutor()

    @GetMapping("/publish")
    fun produceCommands(
        @RequestParam n: Int,
    ) {
        (1..n)
            .map {
                    CompletableFuture.supplyAsync(
                        {
                            log.info { "Sending message" }
                            template.send(inTopic, it.toString(), Command(it))
                        }, executorService
                    )
            }.toTypedArray()
    }

    data class Command(val value: Int)

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
