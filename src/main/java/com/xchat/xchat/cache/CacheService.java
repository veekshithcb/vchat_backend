package com.xchat.xchat.cache;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

    private final ConfigRepository configRepository;

    public CacheService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public List<ConfigEntity> getAllConfigs() {
        return configRepository.findAll();
    }
}
