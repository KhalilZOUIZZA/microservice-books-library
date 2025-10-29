package ma.theBeans.app.cloud;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Configuration
public class MinioBucketInitializer {

    @Autowired
    private MinioClient minioClient;

    @PostConstruct
    public void createBucket() {
        try {
            boolean isExist = minioClient.bucketExists(
                    io.minio.BucketExistsArgs.builder().bucket("books").build()
            );
            if (!isExist) {
                minioClient.makeBucket(
                        io.minio.MakeBucketArgs.builder().bucket("books").build()
                );
            }
        } catch (MinioException e) {
            throw new RuntimeException("Error occurred while creating bucket: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}