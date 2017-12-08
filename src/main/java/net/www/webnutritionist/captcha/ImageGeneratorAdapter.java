package net.www.webnutritionist.captcha;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.www.webnutritionist.Constants;
import net.www.webnutritionist.service.ImageService;
import net.www.webnutritionist.service.impl.ImageServiceImpl;
import net.www.webnutritionist.util.DataUtil;

public abstract class ImageGeneratorAdapter implements ImageGenerator {
	private String format, type, path;
	private ImageService imageService;
	private String textCaptcha;
	private String pathCaptcha;

	public ImageGeneratorAdapter(String format, String type, String path) {
		this.format = format;
		this.type = type;
		this.path = path;
		this.imageService = new ImageServiceImpl();
	}

	@Override
	public File generateTextImage() throws ImageGenerationException {
		try {
			textCaptcha = generateCaptchaText();
			
			File ret = generateFile();

			BufferedImage image = imageService.getImageReader(type, path);
			imageService.process(image, textCaptcha, type);
			imageService.writeImage(image, format, ret);

			return ret;
		} catch (IOException e) {
			throw new ImageGenerationException(e);
		}
	}

	private File generateFile() {
        Random random = new Random();
        File ret = null;
        pathCaptcha = Constants.FILE_PATH_NEW + random.nextInt() + ".jpg";
        while (ret == null || ret.exists())
            ret = new File(pathCaptcha);
        return ret;
    }
	
	private String generateCaptchaText(){
		int random = new Random().nextInt();
		String text = ""+random;
		return DataUtil.convertCaptchaTextFourSymbol(text);
	}

	public String getTextCaptcha() {
		return textCaptcha;
	}

	public void setTextCaptcha(String textCaptcha) {
		this.textCaptcha = textCaptcha;
	}

	public String getPathCaptcha() {
		return pathCaptcha;
	}

	public void setPathCaptcha(String pathCaptcha) {
		this.pathCaptcha = pathCaptcha;
	}

}
