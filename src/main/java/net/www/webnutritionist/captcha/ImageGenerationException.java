package net.www.webnutritionist.captcha;

public class ImageGenerationException extends Exception {
	private static final long serialVersionUID = -4533336310055359729L;

	public ImageGenerationException() {
    }

    public ImageGenerationException(String message) {
        super(message);
    }

    public ImageGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageGenerationException(Throwable cause) {
        super(cause);
    }

    public ImageGenerationException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
