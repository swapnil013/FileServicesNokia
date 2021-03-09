package com.nokia.files.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")

@Entity

@Table(name = "file")

public class File {

    @Id
    @Column(name = "file_id")

    private Integer fileId;

    

    @Column(name = "user_id")

    private Integer userId;

    

    @Column(name = "file_name")

    private String fileName;

    

    @Column(name = "document_type")

    private String documentType;

    

    @Column(name = "document_format")

    private String documentFormat;


    @Column(name = "upload_dir")

    private String uploadDir;


	public Integer getFileId() {
		return fileId;
	}


	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}




	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getDocumentType() {
		return documentType;
	}


	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}


	public String getDocumentFormat() {
		return documentFormat;
	}


	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}


	public String getUploadDir() {
		return uploadDir;
	}


	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}





	public File(Integer fileId, Integer userId, String fileName, String documentType, String documentFormat,
			String uploadDir) {
		super();
		this.fileId = fileId;
		this.userId = userId;
		this.fileName = fileName;
		this.documentType = documentType;
		this.documentFormat = documentFormat;
		this.uploadDir = uploadDir;
	}


	public File() {
		// TODO Auto-generated constructor stub
	}
    
    
}
