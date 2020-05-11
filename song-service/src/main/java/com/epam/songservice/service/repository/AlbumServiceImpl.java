package com.epam.songservice.service.repository;

import com.epam.songservice.model.Album;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlbumServiceImpl extends GenericServiceImpl<Album, Long> implements AlbumService {
    @Override
    public Album save(Album entity) throws Exception {
        return super.save(entity);
    }
}
