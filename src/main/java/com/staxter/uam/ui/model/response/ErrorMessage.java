package com.staxter.uam.ui.model.response;

/**
 * This class store the custom error message send to client
 * 
 * @author Veena
 *
 */
public class ErrorMessage {

	private String code;
	private String description;

	public ErrorMessage(String code, String description) {
		this.code = code;
		this.description = description;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
