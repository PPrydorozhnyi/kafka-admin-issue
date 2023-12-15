package com.petro.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfig {

    @Bean
    fun inTopic(
        @Value("\${spring.kafka.topics.in}") inTopic: String,
    ): NewTopic {
        return TopicBuilder.name(inTopic)
            .partitions(3)
            .replicas(1)
            .build()
    }

    @Bean
    fun commandFactory(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper,
    ): ProducerFactory<String, TestController.Command> {
        val valueSerializer = JsonSerializer<TestController.Command>(objectMapper)
        return DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties(null), null, valueSerializer)
    }

    @Bean
    fun commandKafkaTemplate(commandFactory: ProducerFactory<String, TestController.Command>): KafkaTemplate<String,
            TestController.Command> {
        val kafkaTemplate = KafkaTemplate(commandFactory)
        kafkaTemplate.setObservationEnabled(true)
        return kafkaTemplate
    }
}
