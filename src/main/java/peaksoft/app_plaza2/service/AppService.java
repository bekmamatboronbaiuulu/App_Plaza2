package peaksoft.app_plaza2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.app_plaza2.mapper.AppMapper;
import peaksoft.app_plaza2.model.dto.ApplicationRequest;
import peaksoft.app_plaza2.model.dto.ApplicationResponse;
import peaksoft.app_plaza2.model.dto.RegistrationRequest;
import peaksoft.app_plaza2.model.dto.UserResponse;
import peaksoft.app_plaza2.model.entties.Application;
import peaksoft.app_plaza2.model.entties.Genre;
import peaksoft.app_plaza2.model.entties.User;
import peaksoft.app_plaza2.model.enums.Status;
import peaksoft.app_plaza2.repository.ApplicationRepository;
import peaksoft.app_plaza2.repository.GenreRepository;
import peaksoft.app_plaza2.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppService {
    private final ApplicationRepository applicationRepository;
    private  final AppMapper appMapper;
    private  final GenreRepository genreRepository;
    private  final UserRepository userRepository;

    public ApplicationResponse save(ApplicationRequest request){
        Application application = appMapper.mapAppToEntity(request);
        Genre genre = genreRepository.findById(request.getGereId()).get();
        if (genre== null){
            throw  new RuntimeException("Not found Genre with id:"+request.getGereId());
        }
        application.setGenre(genre);
        application.setGenreName(genre.getName());
        applicationRepository.save(application);
        return appMapper.mapToResponse(application);
    }
    public List<ApplicationResponse> download(Long appId,String username){
        User user = userRepository.findByEmail(username).
                orElseThrow(()->new RuntimeException("Not found User with name:"+username));
        Application application = applicationRepository.findById(appId)
                .orElseThrow(()->new RuntimeException("Not found Application with id:"+appId));

        Status userStatus = user.getStatus();
        Status appStatus   = application.getAppStatus();
//         List<User> compatibleUsers = userRepository.findAllBasicOrPremiumUser(appStatus);
        List<Application> compatibleApplications = applicationRepository.findAllBasicOrPremiumApp(userStatus);
        if ( !compatibleApplications.contains(application) ){
            throw new RuntimeException("Incompatible status for user and application!");
        }

        List<Application> myapp = user.getApplications();
        if (!myapp.contains(application)){
            myapp.add(application);
            userRepository.save(user);
        }else {
            throw new RuntimeException("This application already downloaded !");
        }
        return myApplications (user.getEmail());
    }


    public List<ApplicationResponse> myApplications(String userName){
        User user = userRepository.findByEmail(userName).
                orElseThrow(()->new RuntimeException("Not found User with name:"+userName));
        List<Application>applications = applicationRepository.getApplicationByUsersId(user.getId());
        return applications
                .stream()
                .map(appMapper::mapToResponse).toList();
    }
    public  List<ApplicationResponse> searchAndPaginationSer(String text,int page,int size){
        String appName = text==null ? "":text;
        Pageable pageable = PageRequest.of(page-1,size);
        List<Application> applications = applicationRepository.searchAndPagination(appName.toUpperCase(),pageable);
        List<ApplicationResponse> responses = new ArrayList<>();
        for (Application application:applications){
            responses.add(appMapper.mapToResponse(application));
        }
        return  responses;
    }

    public ApplicationResponse findById(Long id ){
        log.info("User not found by id:"+id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Application not found by id:"+id));
        return appMapper.mapToResponse(application);
    }
    public List<ApplicationResponse> findAll(){
        System.out.println("I'm in find all method in service layer");
        return applicationRepository.findAll()
                .stream()
                .map(appMapper::mapToResponse).toList();
    }
    public  ApplicationResponse update(Long appId, ApplicationRequest request){
        Application oldApplication = applicationRepository.findById(appId)
                .orElseThrow(()->new RuntimeException("Application not found by id:"+appId));
        oldApplication.setName(request.getName());
        oldApplication.setDescription(request.getDescription());
        oldApplication.setDeveloper(request.getDeveloper());
        oldApplication.setVersion(request.getVersion());
        oldApplication.setAppStatus(request.getAppStatus());
        oldApplication.setGenreId(request.getGereId());
        oldApplication.setCreateDate(LocalDate.now());
        applicationRepository.save(oldApplication);
        return appMapper.mapToResponse(oldApplication);
    }
    public  void  RemoveApplicationById(Long appId){
        Application application = applicationRepository.findById(appId)
                .orElseThrow(()->new RuntimeException("Application not found by id:"+appId));
        applicationRepository.delete(application);
    }
}
