package net.www.webnutritionist.service.impl;

import static net.www.webnutritionist.Constants.UIImageType.AVATARS;
import static net.www.webnutritionist.Constants.UIImageType.PRODUCT;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.Constants.UIImageType;
import net.www.webnutritionist.annotation.EnableUploadImageTempStorage;
import net.www.webnutritionist.component.DataBuilder;
import net.www.webnutritionist.component.ImageFormatConverter;
import net.www.webnutritionist.component.ImageOptimizator;
import net.www.webnutritionist.component.ImageResizer;
import net.www.webnutritionist.component.impl.UploadImageTempStorage;
import net.www.webnutritionist.exception.CantCompleteClientRequestException;
import net.www.webnutritionist.model.UploadResult;
import net.www.webnutritionist.model.UploadTempPath;
import net.www.webnutritionist.service.ImageProcessorService;
import net.www.webnutritionist.service.ImageStorageService;

@Service
public class ImageProcessorServiceImpl implements ImageProcessorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessorServiceImpl.class);
	
	@Autowired
	@Qualifier("pngToJpegImageFormatConverter")
	private ImageFormatConverter pngToJpegImageFormatConverter;
	
	@Autowired
	private ImageResizer imageResizer;
	
	@Autowired
	private ImageOptimizator imageOptimizator;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	@Autowired
	private UploadImageTempStorage uploadImageTempStorage;
	
	@Autowired
	protected DataBuilder dataBuilder;

	@Override
	@EnableUploadImageTempStorage
	public UploadResult processNewProfilePhoto(MultipartFile upload) {
		try {
			return processUpload(upload, AVATARS);
		} catch (IOException e) {
			throw new CantCompleteClientRequestException("Can't save profile photo upload: " + e.getMessage(), e);
		}
	}
	
	@Override
	@EnableUploadImageTempStorage
	public UploadResult processNewProductPhoto(MultipartFile upload) {
		try {
			return processUploadProduct(upload, PRODUCT);
		} catch (IOException e) {
			throw new CantCompleteClientRequestException("Can't save product photo upload: " + e.getMessage(), e);
		}
	}

	protected UploadResult processUpload(MultipartFile multipartFile, UIImageType imageType) throws IOException {
		String largePhoto = generateNewFileName();
		String smallPhoto = getSmallImageName(largePhoto);
		UploadTempPath uploadTempPath = getCurrentUploadTempPath();
		transferUploadToFile(multipartFile, uploadTempPath.getLargeImagePath());
		resizeAndOptimizeUpload(uploadTempPath, imageType);
		String largePhotoLink = imageStorageService.saveAndReturnImageLink(largePhoto, imageType
								, uploadTempPath.getLargeImagePath());
		String smallPhotoLink = imageStorageService.saveAndReturnImageLink(smallPhoto, imageType
								, uploadTempPath.getSmallImagePath());
		return new UploadResult(largePhotoLink, smallPhotoLink);
	}
	
	protected UploadResult processUploadProduct(MultipartFile multipartFile, UIImageType imageType) throws IOException {
		String largePhoto = generateNewFileName();
		UploadTempPath uploadTempPath = getCurrentUploadTempPath();
		transferUploadToFile(multipartFile, uploadTempPath.getLargeImagePath());
		resizeAndOptimizeUpload(uploadTempPath, imageType);
		String largePhotoLink = imageStorageService.saveAndReturnImageLink(largePhoto, imageType
								, uploadTempPath.getLargeImagePath());
		return new UploadResult(largePhotoLink);
	}
	
	protected void resizeAndOptimizeUpload(UploadTempPath uploadTempPath, UIImageType imageType) throws IOException {
		imageResizer.resizeImage(uploadTempPath.getLargeImagePath()
								, uploadTempPath.getSmallImagePath()
								, imageType.getSmallWidth()
								, imageType.getSmallHeight());
		imageOptimizator.optimize(uploadTempPath.getSmallImagePath());
		imageResizer.resizeImage(uploadTempPath.getLargeImagePath()
								, uploadTempPath.getLargeImagePath()
								, imageType.getLargeWidth()
								, imageType.getLargeHeight());
		imageOptimizator.optimize(uploadTempPath.getLargeImagePath());
	}

	protected String generateNewFileName() {
		return UUID.randomUUID().toString() + ".jpg";
	}

	protected String getSmallImageName(String largePhoto) {
		return largePhoto.replace(".jpg", "-sm.jpg");
	}
	
	protected UploadTempPath getCurrentUploadTempPath(){
		return uploadImageTempStorage.getCurrentUploadTempPath();
	}
	
	protected void transferUploadToFile(MultipartFile uploadPhoto, Path destPath) throws IOException {
		String contentType = uploadPhoto.getContentType();
		LOGGER.debug("Content type for upload {}", contentType);
		uploadPhoto.transferTo(destPath.toFile());
		if (contentType.contains("png")) {
			pngToJpegImageFormatConverter.convertImage(destPath, destPath);
		} else if (!contentType.contains("jpg") && !contentType.contains("jpeg")) {
			throw new CantCompleteClientRequestException("Only png and jpg image formats are supported: Current content type=" + contentType);
		}
	}
}
