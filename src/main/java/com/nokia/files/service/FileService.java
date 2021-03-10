package com.nokia.files.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.net.MalformedURLException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;

import com.nokia.files.entity.File;
import com.nokia.files.model.FileModel;
import com.nokia.files.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	FileRepository fileRepo;

	private Path fileStorageLocation=null;

	@Autowired

	public void DocumentStorageService(File fileStorageProperties) throws Exception {

		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())

				.toAbsolutePath().normalize();

		try {

			Files.createDirectories(this.fileStorageLocation);

		} catch (Exception ex) {

			throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);

		}

	}

	public String storeFile(MultipartFile file, Integer userId, String docType) throws Exception {

		// Normalize file name

		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

		String fileName = "";

		try {

			// Check if the file's name contains invalid characters

			if (originalFileName.contains("..")) {

				throw new Exception(" Filename contains invalid path sequence " + originalFileName);

			}

			String fileExtension = "";

			try {

				fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

			} catch (Exception e) {

				fileExtension = "";

			}
			if (!fileName.equals("tar")) {
				return "Please upload the file of type .tar only";
			} else {

				fileName = userId + "_" + docType + fileExtension;

				// Copy file to the target location (Replacing existing file with the same name)

				Path targetLocation = this.fileStorageLocation.resolve(fileName);

				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

				File doc = fileRepo.checkDocumentByUserId(userId, docType);

				if (doc != null) {

					doc.setDocumentFormat(file.getContentType());

					doc.setFileName(fileName);

					fileRepo.save(doc);

				} else {

					File newDoc = new File();

					newDoc.setUserId(userId);

					newDoc.setDocumentFormat(file.getContentType());

					newDoc.setFileName(fileName);

					newDoc.setDocumentType(docType);

					fileRepo.save(newDoc);

				}

				return fileName;
			}
		} catch (IOException ex) {

			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);

		}

	}

	public Resource loadFileAsResource(String fileName) throws Exception {

		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {

				return resource;

			} else {

				throw new FileNotFoundException("File not found " + fileName);

			}

		} catch (MalformedURLException ex) {

			throw new FileNotFoundException("File not found " + fileName);

		}

	}

	public String getDocumentName(Integer userId, String docType) {

		return fileRepo.getUploadDocumnetPath(userId, docType);

	}

	public FileModel fileUpload(MultipartFile file, Integer userId, String docType, String fileName,
			String fileDownloadUri, String string, long l) {
		FileModel f = new FileModel();
		f.setFileDownloadUri(fileDownloadUri);
		f.setFileName(fileName);
		f.setFileType(file.getContentType());
		f.setSize(file.getSize());
		return f;
	}

}
