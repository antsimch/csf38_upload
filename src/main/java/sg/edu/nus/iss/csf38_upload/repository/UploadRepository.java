package sg.edu.nus.iss.csf38_upload.repository;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class UploadRepository {
    
    private AmazonS3 s3;

    public UploadRepository(AmazonS3 s3) {
        this.s3 = s3;
    }

    public String postFile(MultipartFile file) {
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        
        String id = UUID.randomUUID().toString().substring(0, 8);

        try {
            PutObjectRequest putReq = new PutObjectRequest(
                    "csf-workshop", 
                    "audio/%s".formatted(id), 
                    file.getInputStream(), 
                    metadata);

            putReq = putReq.withCannedAcl(
                    CannedAccessControlList.PublicRead);

            s3.putObject(putReq);    

            System.out.println("\n\n" + id + "\n\n");

        } catch (IOException ex) {
        }
        return id;
    }

    public String getUrl(String id) {

        String key = "audio/%s".formatted(id);

        return s3.getUrl("csf-workshop", key)
                .toExternalForm();
    }
}
