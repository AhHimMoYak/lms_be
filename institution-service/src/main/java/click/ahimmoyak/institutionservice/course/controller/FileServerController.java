package click.ahimmoyak.institutionservice.course.controller;


import click.ahimmoyak.institutionservice.course.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileServerController {

    private final FileService fileService;

    @GetMapping("/videos/{info}")
    public ResponseEntity<ResourceRegion> streamVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable String info) {
        return fileService.streamVideo(httpHeaders, info);
    }

    @GetMapping("/materials/{info}")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable String info) {
        return fileService.downloadMaterial(info);
    }
}
