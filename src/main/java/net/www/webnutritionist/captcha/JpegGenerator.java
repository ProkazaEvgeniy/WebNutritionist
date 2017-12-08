package net.www.webnutritionist.captcha;

import net.www.webnutritionist.Constants;

public class JpegGenerator extends ImageGeneratorAdapter {
    public JpegGenerator() {
        super(Constants.FORMAT_JPEG, Constants.MIMETYPE_JPEG, Constants.FILE_PATH_JPEG);
    }

	@Override
	public String getPathCaptchaPhoto() {
		return getPathCaptcha();
	}

	@Override
	public String getCaptchaText() {
		return getCaptchaText();
	}
    
}
