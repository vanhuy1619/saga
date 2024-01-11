package com.example.productcommandservice.common.utils;

import io.kcache.CacheUpdateHandler;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUpdateHandler implements CacheUpdateHandler<String, String> {
    private static final Logger log = LoggerFactory.getLogger(StringUpdateHandler.class);

    /**
     * Invoked on every new K,V pair written to the store
     *
     * @param key   key associated with the data
     * @param value data written to the store
     * @param oldValue the previous value associated with key, or null if there was no mapping for key
     * @param timestamp timestamp
     */
    @Override
    public void handleUpdate(String key, String value, String oldValue,
                             TopicPartition tp, long offset, long timestamp) {
        log.info("Handle update for ({}, {}, {}, {}, {}, {})", key, value, oldValue, tp, offset, timestamp);
    }
}
