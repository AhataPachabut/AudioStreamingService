package com.it.songservice.service.storage.resource.decorator;

import com.it.songservice.service.repository.ResourceRepositoryService;
import com.it.songservice.service.storage.resource.ResourceStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResourceStorageDecoratorFactory {

    @Autowired
    private ResourceRepositoryService resourceRepositoryService;

    @Autowired
    private CacheManager cacheManager;

    public ResourceStorageService create(ResourceStorageService service, Class<?> clazz) {
        if (ResourceIORetryDecorator.class.equals(clazz)) {
            return new ResourceIORetryDecorator(service);
        } else if (ResourceDBDecorator.class.equals(clazz)) {
            return new ResourceDBDecorator(service, resourceRepositoryService);
        } else if (ResourceCacheDecorator.class.equals(clazz)) {
            return new ResourceCacheDecorator(service, cacheManager);
        }
        return service;
    }

}