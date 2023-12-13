package peaksoft.app_plaza2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.app_plaza2.mapper.GenreMapper;
import peaksoft.app_plaza2.model.dto.*;
import peaksoft.app_plaza2.model.entties.Application;
import peaksoft.app_plaza2.model.entties.Genre;
import peaksoft.app_plaza2.model.entties.User;
import peaksoft.app_plaza2.repository.GenreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreResponse save(GenreRequest request) {
        Genre genre = genreMapper.mapToEntity(request);
        genreRepository.save(genre);
        return genreMapper.mapToResponse(genre);
    }
    public  List<GenreResponse> searchAndPaginationSer(String text,int page,int size){
        String name = text==null ? "":text;
        Pageable pageable = PageRequest.of(page-1,size);
        List<Genre> genres = genreRepository.searchAndPagination(name.toUpperCase(),pageable);
        List<GenreResponse> responses = new ArrayList<>();
        for (Genre genre:genres){
            responses.add(genreMapper.mapToResponse(genre));
        }
        return  responses;
    }

    public GenreResponse findById(Long genreId) {
        return genreMapper.mapToResponse(genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Not found genre by id:" + genreId)));
    }
    public List<GenreResponse> findAll(){
        System.out.println("I'm in find all method in service layer");
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::mapToResponse).toList();
    }

    public List<Genre> findByName(String genreName) {
        return genreRepository.findByName(genreName);

    }
    public GenreResponse update(Long genreId, GenreRequest request){
        Genre oldGenre = genreRepository.findById(genreId)
                .orElseThrow(()->new RuntimeException("Genre not found by id:"+genreId));
        oldGenre.setName(request.getName());
        oldGenre.setDescription(request.getDescription());
        oldGenre.setCreateDate(LocalDate.now());
        genreRepository.save(oldGenre);
        return genreMapper.mapToResponse(oldGenre);
    }
    public  void  RemoveGenreById(Long genreId){
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new RuntimeException("Genre not found by id:"+genreId));
        genreRepository.delete(genre);
    }
}

