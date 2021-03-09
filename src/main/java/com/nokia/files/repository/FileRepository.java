package com.nokia.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nokia.files.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

	@Query("Select a from File a where user_id = ?1 and document_type = ?2")

	File checkDocumentByUserId(Integer userId, String docType);

	@Query("Select fileName from File a where user_id = ?1 and document_type = ?2")

	String getUploadDocumnetPath(Integer userId, String docType);

}
