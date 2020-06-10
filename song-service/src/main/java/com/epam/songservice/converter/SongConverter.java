package com.epam.songservice.converter;

import com.epam.songservice.model.Song;
import com.epam.songservice.service.repository.SongRepositoryService;
import lombok.SneakyThrows;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public
class SongConverter implements CustomConverter {

    private static SongRepositoryService repositoryService;
    private static Mapper mapper;

    @Autowired
    public SongConverter(SongRepositoryService repositoryService, Mapper mapper) {
        SongConverter.repositoryService = repositoryService;
        SongConverter.mapper = mapper;
    }

    /**
     * Instantiates a new Client converter.
     */
    public SongConverter() {
    }

    @SneakyThrows
    @Override
    public Object convert(Object dest, Object source, Class<?> destinationClass, Class<?> sourceClass) {

        if (source == null)
            return null;

        else if(source instanceof Map) {
            Map<String, Object> metadataMap = (Map<String, Object>) source;

            Song entity = repositoryService.findByTitle((String) metadataMap.get("Title"));

            if (entity == null) {
//                entity = Song.builder()
//                        .title((String) metadataMap.get("Title"))
//                        .album(mapper.map((Map<String, Object>) metadataMap.get("Album"), Album.class))
//                        .build();
                entity = mapper.map(metadataMap, Song.class);
                repositoryService.save(entity);
            } else {
                entity = mapper.map(entity, Song.class);
            }

            return entity;
        }

        else if(source instanceof Long) {
            return repositoryService.findById((Long) source);
        }

        else return null;
    }
}