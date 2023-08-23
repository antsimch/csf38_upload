package sg.edu.nus.iss.csf38_upload.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.csf38_upload.repository.UploadRepository;

@RestController
@RequestMapping(path = "/api")
public class UploadController {

    private UploadRepository uploadRepo;

    public UploadController(UploadRepository uploadRepo) {
        this.uploadRepo = uploadRepo;
    }

    @PostMapping(path = "/upload", 
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postImage(
            @RequestPart MultipartFile file) {
                
        JsonObject obj = Json.createObjectBuilder()
                .add("id", this.uploadRepo.postFile(file))
                .build();

        System.out.println("\n\n" + obj.toString() + "\n\n");

        return ResponseEntity.ok(obj.toString());
    }

    @GetMapping(path = "/audio/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUrl(@PathVariable String id) {

        String url = uploadRepo.getUrl(id);

        JsonObject obj = Json.createObjectBuilder()
                .add("url", url)
                .build();

        System.out.println("\n\n" + obj.toString() + "\n\n");

        return ResponseEntity.ok(obj.toString());
    }
}
