package net.www.webnutritionist.component.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;
import net.www.webnutritionist.component.ImageResizer;

@Component
public class ThumbnailsImageResizer implements ImageResizer {

	@Override
	public void resizeImage(Path sourceImageFile, Path destImageFile, int width, int height) throws IOException {
		Thumbnails.of(sourceImageFile.toFile()).size(width, height).toFile(destImageFile.toFile());
	}
}
