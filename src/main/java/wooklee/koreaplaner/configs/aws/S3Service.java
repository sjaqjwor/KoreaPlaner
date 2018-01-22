package wooklee.koreaplaner.configs.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooklee.koreaplaner.controllers.responses.DefaultResponse;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private ResourceLoader loader;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadS3(MultipartFile multipartFile) throws IOException{
        TransferManager transferManager = new TransferManager(this.amazonS3);
        System.err.println(multipartFile.getOriginalFilename());
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf("."));

        String filename = UUID.randomUUID().toString().replace("-","")+ext;

        File file = new File(System.getProperty("user.dir")+"/webapps/koreaplaner"+filename);
//        File file = new File(System.getProperty("user.dir")+filename);

        multipartFile.transferTo(file);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,filename,file);

        Upload upload = transferManager.upload(putObjectRequest);

        try{
            upload.waitForUploadResult();
            file.delete();
        }catch (AmazonClientException e){
            throw new AmazonS3Exception(e.toString());
        }catch (InterruptedException e){
            throw new IOException();
        }
        return filename;
    }

}
