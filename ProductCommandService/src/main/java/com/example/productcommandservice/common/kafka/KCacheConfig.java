package com.example.productcommandservice.kafka;

import com.example.productcommandservice.utils.StringUpdateHandler;
import io.kcache.Cache;
import io.kcache.CacheType;
import io.kcache.KafkaCache;
import io.kcache.KafkaCacheConfig;
import io.kcache.exceptions.CacheInitializationException;
import io.kcache.utils.Caches;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KCacheConfig {

    @Value("${kafka.bootstrap-server}")
    private String bootstrapServer;

    @Bean
    public Cache<String, String> kafkaCache() throws Exception {
        Properties props = getKafkaCacheProperties();
        return createAndInitKafkaCacheInstance(props);
    }

    private Cache<String, String> createAndInitKafkaCacheInstance(Properties props) throws CacheInitializationException {
        KafkaCacheConfig config = new KafkaCacheConfig(props);
        Cache<String, String> kafkaCache = Caches.concurrentCache(
                new KafkaCache<>(config,
                        Serdes.String(),
                        Serdes.String(),
                        new StringUpdateHandler(),
                        null));
        kafkaCache.init();
        return kafkaCache;
    }

    private Properties getKafkaCacheProperties() throws Exception {
        Properties props = new Properties();
        props.put(KafkaCacheConfig.KAFKACACHE_BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(KafkaCacheConfig.KAFKACACHE_BACKING_CACHE_CONFIG, CacheType.MEMORY.toString());
        props.put(KafkaCacheConfig.KAFKACACHE_TOPIC_CONFIG, "CACHE_HUB_NOTIFY");
        return props;
    }
}
