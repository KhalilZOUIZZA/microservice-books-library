package ma.theBeans.app.cloud;

import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.service.impl.admin.book.BookAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private BookAdminServiceImpl service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return "No file was uploaded or file is empty.";
            }

            System.out.println("File name: " + file.getOriginalFilename());
            return minioService.uploadFile(
                    "books",
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/{id}/image-url")
    public ResponseEntity<String> getImageUrl(@PathVariable Long id) {
        Book book = service.findById(id);
        if (book != null && book.getImageUrl() != null) {
            String imageUrl = minioService.getImageUrl(book.getImageUrl());
            return ResponseEntity.ok(imageUrl);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{imagePath}")
    public ResponseEntity<byte[]> getImageByPath(@PathVariable String imagePath) {
        try {
            // Fetch the image as bytes from MinIO
            byte[] imageBytes = minioService.getImageAsBytes("books", imagePath);

            // Set HTTP headers for the image response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Adjust MediaType if your images are not PNG
            headers.setContentLength(imageBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
      } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 for other errors
        }
    }
}
