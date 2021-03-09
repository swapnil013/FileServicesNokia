package com.nokia.files.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nokia.files.model.FileModel;
import com.nokia.files.service.FileService;


@RestController
@CrossOrigin
public class FilesController {

	private static final Logger logger = LoggerFactory.getLogger(FilesController.class);
	@Autowired
	FileService fileService;

	@PostMapping("/uploadTarFile")
	public ResponseEntity<FileModel> uploadFile(@RequestParam("file") MultipartFile file,

			@RequestParam("userId") Integer UserId,

			@RequestParam("docType") String docType)throws Exception {
		try {
			
			
			String fileName = fileService.storeFile(file, UserId, docType);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()

					.path("/downloadFile/")

					.path(fileName)

					.toUriString();

		
			
			return new ResponseEntity<>(fileService.fileUpload(file,UserId,docType,fileName, fileDownloadUri,

					file.getContentType(), file.getSize()),HttpStatus.OK);
			
					
			
		}
		catch(Exception e)
		{
			
			logger.error("UploadTarFileError",e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/downloadTarFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam("userId") Integer userId,

			@RequestParam("docType") String docType,HttpServletRequest request) {

		String fileName = fileService.getDocumentName(userId, docType);

		Resource resource = null;

		if (fileName != null && !fileName.isEmpty()) {

			try {

				resource = fileService.loadFileAsResource(fileName);

			} catch (Exception e) {

				e.printStackTrace();

			}

			// Try to determine file's content type

			String contentType = null;

			try {

				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			} catch (IOException ex) {

				// logger.info("Could not determine file type.");

			}

			// Fallback to the default content type if type could not be determined

			if (contentType == null) {

				contentType = "application/octet-stream";

			}

			return ResponseEntity.ok()

					.contentType(MediaType.parseMediaType(contentType))

					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")

					.body(resource);

		} else {

			return ResponseEntity.notFound().build();

		}

	}

}
