package peaksoft.app_plaza2.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.app_plaza2.model.entties.Application;
import peaksoft.app_plaza2.model.entties.User;
import peaksoft.app_plaza2.model.enums.Status;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select  app from Application app join app.users user where user.id=:id")
    List<Application> getApplicationByUsersId(@Param("id")Long userId);


    @Query("select  app from Application  app where upper(app.name) like concat('%',:text,'%')")
    List<Application> searchAndPagination(@Param("text") String text, Pageable pageable);


    @Query("select  app from Application app where app.appStatus=:status")
    List<Application > findAllBasicOrPremiumApp(@Param("status")Status status);
}
