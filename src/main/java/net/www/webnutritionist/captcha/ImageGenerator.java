package net.www.webnutritionist.captcha;

import java.io.File;

public interface ImageGenerator {
    File generateTextImage() throws ImageGenerationException;
    String getPathCaptchaPhoto();
    String getCaptchaText();
}
