package com.it.storageservice.service.storage.decorator;

import com.it.songservice.exception.UploadException;
import com.it.songservice.model.Resource;
import com.it.songservice.service.repository.ResourceRepositoryService;
import com.it.songservice.service.storage.resource.ResourceStorageService;

public class ResourceDBDecorator extends ResourceStorageDecorator {

    private ResourceRepositoryService repositoryService;

    public ResourceDBDecorator(ResourceStorageService storageService, ResourceRepositoryService repositoryService) {
        super(storageService);
        this.repositoryService = repositoryService;
    }

    @Override
    public Resource upload(org.springframework.core.io.Resource source, String name) throws Exception {
        Resource entity = super.upload(source, name);

        Throwable lastException;
        try {
            return repositoryService.save(entity);
        } catch (Exception e) {
            super.delete(entity);
            lastException = e;
        }
        throw new UploadException("DB exc in "+ name, lastException);
    }

    @Override
    public void delete(Resource entity) throws Exception {
        repositoryService.deleteById(entity.getId());
        super.delete(entity);
    }

    @Override
    public boolean exist(Resource entity) {
        if(repositoryService.existById(entity.getId())) return super.exist(entity);
        else return false;
    }
}
