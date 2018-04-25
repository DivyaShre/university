package com.un.consumer.util;

public class UNException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;
	private String description;

	public UNException() {
		super();
	}

	public UNException(String errMsg) {
		super();
		this.errMsg = errMsg;
	}

	public UNException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public UNException(String errCode, String errMsg, String description) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.description = description;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
