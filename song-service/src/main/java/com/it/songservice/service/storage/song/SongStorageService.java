package com.it.songservice.service.storage.song;

import com.it.songservice.annotation.Decorate;
import com.it.songservice.model.Song;
import com.it.songservice.service.storage.song.decorator.SongConversionDecorator;
import com.it.songservice.service.storage.song.decorator.SongDBDecorator;
import com.it.songservice.service.storage.song.decorator.SongIndexDecorator;

import java.util.List;

@Decorate(decorations = {SongConversionDecorator.class, SongDBDecorator.class, SongIndexDecorator.class})
public interface SongStorageService {

    Song upload(org.springframework.core.io.Resource source, String name) throws Exception;

    List<Song> uploadZip(org.springframework.core.io.Resource source, String name) throws Exception;

    org.springframework.core.io.Resource download(Song entity) throws Exception;

    void delete(Song entity) throws Exception;

    boolean exist(Song entity);


}