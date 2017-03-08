package bean;

import java.io.Serializable;

public class ExportObject implements Serializable {

	private int type;
	private String namePwd;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ExportObject() {

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNamePwd() {
		return namePwd;
	}

	public void setNamePwd(String namePwd) {
		this.namePwd = namePwd;
	}

	public ExportObject(int type, String namePwd) {
		super();
		this.type = type;
		this.namePwd = namePwd;
		
	}

}
