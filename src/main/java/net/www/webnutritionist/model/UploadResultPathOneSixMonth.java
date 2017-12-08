package net.www.webnutritionist.model;

import java.io.Serializable;

public class UploadResultPathOneSixMonth extends AbstractModel implements Serializable {
	private static final long serialVersionUID = 6777278132063596897L;
	
	private String pathOneMonth;
	private String pathSixMonth;

	public UploadResultPathOneSixMonth(String pathOneMonth, String pathSixMonth) {
		super();
		this.pathOneMonth = pathOneMonth;
		this.pathSixMonth = pathSixMonth;
	}
	
	public String getPathOneMonth() {
		return pathOneMonth;
	}

	public void setPathOneMonth(String pathOneMonth) {
		this.pathOneMonth = pathOneMonth;
	}

	public String getPathSixMonth() {
		return pathSixMonth;
	}

	public void setPathSixMonth(String pathSixMonth) {
		this.pathSixMonth = pathSixMonth;
	}
}
