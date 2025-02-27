package com.xchat.xchat.cache;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class AppCache {

    @Autowired
    public CacheService cacheService;

    public   Map<String, String> cache;

    @PostConstruct
    public void init(){
        cache = new HashMap<>();
        List<ConfigEntity> all = cacheService.getAllConfigs();

        for (ConfigEntity configJournalAppEntity : all) {
            System.out.println(configJournalAppEntity);
            cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}