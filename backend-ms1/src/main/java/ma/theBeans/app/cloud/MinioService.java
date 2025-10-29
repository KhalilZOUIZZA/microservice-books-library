package ma.theBeans.app.cloud;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils; // Correct import

import java.io.InputStream;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    public String uploadFile(String bucketName, String fileName, InputStream inputStream, long size, String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, size, 5 * 1024 * 1024)
                    .contentType(contentType)
                    .build());

            return "http://localhost:9000/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO: " + e.getMessage(), e);
        }
    }

    public String getImageUrl(String imagePath) {
        return "http://localhost:9000/books/" + imagePath;
    }

    public byte[] getImageAsBytes(String bucketName, String fileName) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        )) {
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching image from MinIO: " + e.getMessage(), e);
        }
    }



}
