package net.www.webnutritionist.service;

import javax.annotation.Nonnull;

import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.model.UploadResult;


public interface ImageProcessorService {

	@Nonnull UploadResult processNewProfilePhoto(@Nonnull MultipartFile uploadPhoto);
	
	@Nonnull UploadResult processNewProductPhoto(@Nonnull MultipartFile uploadPhoto);
}
