package com.epam.songservice.service.storage;

import com.epam.songservice.annotation.Decorate;
import com.epam.songservice.annotation.StorageType;
import com.epam.songservice.model.FSResource;
import com.epam.songservice.model.Resource;
import com.epam.songservice.model.StorageTypes;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@Decorate(ResourceStorageDecorator.class)
@StorageType(StorageTypes.FS)
@Service
public class ResourceStorageServiceFS implements ResourceStorageService {

    @Value("${fs.defaultFolder}")
    private String defaultBaseFolder;

    @Override
    public Resource upload(org.springframework.core.io.Resource source, String name) throws IOException {
        File file;
        if (source.isFile()) {
            file = source.getFile();
        } else {
            file = new File(defaultBaseFolder, name);
            file.getParentFile().mkdirs();
            FileCopyUtils.copy(source.getInputStream(), new FileOutputStream(file));
        }

        FSResource resource = new FSResource();
        resource.setName(file.getName());
        resource.setSize(file.length());
        resource.setChecksum(DigestUtils.md5Hex(new FileInputStream(file)));
        resource.setPath(file.getAbsolutePath());
        return resource;

//        return new FSResource().builder()
//                .path(file.getAbsolutePath())
////                .parent(file.getParent())
//                .name(file.getName())
//                .size(file.length())
//                .checksum(DigestUtils.md5Hex(new FileInputStream(file)))
//                .storageType(StorageTypes.FS)
//                .build();
    }

    @Override
    public org.springframework.core.io.Resource download(Resource resource) {
        return new FileSystemResource(((FSResource)resource).getPath());
    }

    @Override
    public void delete(Resource resource) {
        new File(((FSResource)resource).getPath()).delete();
    }

    @Override
    public boolean exist(Resource resource) {
        return new File(((FSResource)resource).getPath()).exists();
    }

    @Override
    public String test() {
        return "FS";
    }

}
