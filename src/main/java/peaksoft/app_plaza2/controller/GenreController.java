package peaksoft.app_plaza2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.app_plaza2.model.dto.GenreRequest;
import peaksoft.app_plaza2.model.dto.GenreResponse;
import peaksoft.app_plaza2.model.dto.RegistrationRequest;
import peaksoft.app_plaza2.model.dto.UserResponse;
import peaksoft.app_plaza2.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/save")
    public GenreResponse save(@RequestBody GenreRequest genreRequest) {
        return genreService.save(genreRequest);
    }

    @GetMapping("/{id}")
    public GenreResponse findById(@PathVariable("id") Long id) {
        return genreService.findById(id);
    }

    @GetMapping()
    public List<GenreResponse> findAll() {
        return genreService.findAll();
    }

    @PostMapping("/{id}")
    public GenreResponse update(@PathVariable("id")Long id, @RequestBody GenreRequest request) {
        System.out.println("update");
        return genreService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")Long id) {
        genreService.RemoveGenreById(id);
        return "User with :" + id + "successfully deleted";
    }

    @GetMapping("/search")
    public List<GenreResponse> searchAndPagination(@RequestParam(name = "text", required = false) String text,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        return genreService.searchAndPaginationSer(text, page, size);
    }
}
