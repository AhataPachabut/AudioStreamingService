package com.epam.indexservice.controller;

import com.epam.indexservice.dto.response.SongResponseDto;
import com.epam.indexservice.model.Song;
import com.epam.indexservice.service.SongIndexService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/search/songs")
public class SongSearchController {

    @Autowired
    private SongIndexService service;

//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    private Mapper mapper;

    @GetMapping("/{keyword}")
    public ResponseEntity<List<SongResponseDto>> search(@PathVariable String keyword) {

        List<Song> entity = service.search(keyword);

        final List<SongResponseDto> responseDto = entity.stream()
                .map((i) -> mapper.map(i, SongResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

//    @GetMapping("/template/{keyword}")
//    public ResponseEntity<List<SongResponseDto>> searchTemplate(@PathVariable String keyword) {
//
//        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(queryBuilder)
//                .build();
//
//        List<Song> entity = elasticsearchTemplate.queryForList(searchQuery, Song.class);
//
//        final List<SongResponseDto> responseDto = entity.stream()
//                .map((i) -> mapper.map(i, SongResponseDto.class))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
}