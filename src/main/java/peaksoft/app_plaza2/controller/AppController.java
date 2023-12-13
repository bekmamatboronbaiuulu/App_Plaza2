package peaksoft.app_plaza2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.app_plaza2.model.dto.ApplicationRequest;
import peaksoft.app_plaza2.model.dto.ApplicationResponse;
import peaksoft.app_plaza2.model.dto.RegistrationRequest;
import peaksoft.app_plaza2.model.dto.UserResponse;
import peaksoft.app_plaza2.service.AppService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class AppController {
    private final AppService appService;

    @PostMapping("/save")
    public ApplicationResponse save(@RequestBody ApplicationRequest request) {
        return appService.save(request);
    }

    @PostMapping("/download/{id}")
    public List<ApplicationResponse> download(@PathVariable("id") Long appId, Principal principal) {
        return appService.download(appId, principal.getName());
    }

    @GetMapping("/my-app")
    public List<ApplicationResponse> getMyApp(Principal principal) {
        return appService.myApplications(principal.getName());
    }

    @GetMapping("/{id}")
    public ApplicationResponse findById(@PathVariable("id") Long id) {
        return appService.findById(id);
    }

    @GetMapping()
    public List<ApplicationResponse> findAll() {
        return appService.findAll();
    }

    @PutMapping("update/{id}")
    public ApplicationResponse update(@PathVariable("id") Long id, @RequestBody ApplicationRequest request) {
        return appService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        appService.RemoveApplicationById(id);
        return "Application with :" + id + "successfully deleted";
    }

    @GetMapping("/search")
    public List<ApplicationResponse> searchAndPagination(@RequestParam(name = "text", required = false) String text,
                                                         @RequestParam int page,
                                                         @RequestParam int size) {
        return appService.searchAndPaginationSer(text, page, size);
    }
}
