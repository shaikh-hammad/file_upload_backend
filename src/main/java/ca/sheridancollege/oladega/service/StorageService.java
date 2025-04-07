package ca.sheridancollege.oladega.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ca.sheridancollege.oladega.beans.ImageData;
import ca.sheridancollege.oladega.repository.ImageRepo;
import ca.sheridancollege.oladega.util.ImageUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {
    private final ImageRepo imRep;

    public String uploadImage(MultipartFile file) throws IOException {
        // Check if file is not empty
        if (file.isEmpty()) {
            return "No file uploaded";
        }

        // Compress the image and save it
        ImageData imageData = imRep.save(
            ImageData.builder()
                .name(file.getOriginalFilename())  // Corrected to use original file name
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build()
        );

        // Handle null imageData (if saving fails)
        if (imageData != null) {
            return "File uploaded successfully: " + imageData.getName();
        } else {
            return "File upload failed. Please try again.";
        }
    }

    public byte[] downloadImage(String fileName) {
        // Try to find image data by file name
        ImageData imageData = imRep.findByName(fileName);

        // Check if image data is present and not null
        if (imageData != null && imageData.getImageData() != null) {
            return ImageUtils.decompressImage(imageData.getImageData());
        } else {
            // Return a specific message or handle the error
            return null;  // Or log and return an error message
        }
    }
    
    public List<String> getAllImage() {
    	   List<String> fileNames = imRep.findAll()  // Assuming 'imgRep' is the repository to interact with the database
                   .stream()
                   .map(ImageData::getName)  // Assuming 'getName()' returns the file name
                   .collect(Collectors.toList());
    	   return fileNames;
    }
    
  
}
