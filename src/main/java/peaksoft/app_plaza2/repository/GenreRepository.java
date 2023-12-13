package peaksoft.app_plaza2.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.app_plaza2.model.entties.Application;
import peaksoft.app_plaza2.model.entties.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select g from Genre g where g.name=: name")
    List<Genre> findByName(@Param("name")String name);


    @Query("select  genre from Genre genre where upper(genre.name) like concat('%',:text,'%')")
    List<Genre> searchAndPagination(@Param("text") String text, Pageable pageable);
}