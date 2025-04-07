package ca.sheridancollege.oladega.controller;

import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ca.sheridancollege.oladega.beans.ImageData;
import ca.sheridancollege.oladega.repository.ImageRepo;
import ca.sheridancollege.oladega.service.StorageService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/images")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})

public class ImageController {
	StorageService stServe;
	ImageRepo imgRep;
	@PostMapping(value={"","/"})
	public ResponseEntity<?> sendFile(@RequestParam("image") MultipartFile file) throws IOException
	{
		String uploadInfo = stServe.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadInfo);
	}
	
	
	@GetMapping("/{file}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String file) throws IOException {
	    byte[] fileData = stServe.downloadImage(file); // Generic method for retrieving file data

	    if (fileData != null) {
	        // Determine the MIME type dynamically
	        Path filePath = Paths.get("uploads", file); // Multipurpose Internet Mail Extensions
	        String contentType = Files.probeContentType(filePath);

	        // Default to application/octet-stream if MIME type cannot be determined
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        ByteArrayResource resource = new ByteArrayResource(fileData);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"") // Forces download
	                .body(resource);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new ByteArrayResource("File not found".getBytes()));
	    }
	    
	    
	}

	@GetMapping("/all")
	public ResponseEntity<List<String>> getAllFiles() {
	    List<String> fileNames =  stServe.getAllImage() ;

	    return ResponseEntity.ok(fileNames);
	}

	
	

}
