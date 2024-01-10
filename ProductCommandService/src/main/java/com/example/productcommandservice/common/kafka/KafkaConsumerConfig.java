package vn.com.ocb.hub.notification;

import vn.com.ocb.hub.notification.type.KafkaType;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaType> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaType> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaType> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), new JsonDeserializer<>(KafkaType.class));
    }
         
    private Map<String, Object> consumerProps() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        //consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId); //is required
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return consumerProps;
    }

}
