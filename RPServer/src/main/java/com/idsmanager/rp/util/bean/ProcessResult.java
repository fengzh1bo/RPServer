package com.idsmanager.rp.util.bean;

import org.apache.commons.lang.StringUtils;

public class ProcessResult<T> {
	protected Boolean success = Boolean.TRUE;
	protected String errorMessage;
	protected T detail;
	protected Integer errorNum = 0;
	
	public ProcessResult() {
		super();
	}
	public ProcessResult(T detail) {
		this(detail, null);
	}
	public ProcessResult(T detail, String errorMessage) {
		this.detail = detail;
		if (StringUtils.isNotBlank(errorMessage)) {
			this.errorMessage = errorMessage;
			success = Boolean.FALSE;
		}
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public T getDetail() {
		return detail;
	}
	public void setDetail(T detail) {
		this.detail = detail;
	}
	public Integer getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}
}
