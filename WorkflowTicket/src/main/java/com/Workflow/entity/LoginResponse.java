package com.Workflow.entity;

public class LoginResponse {
	private String message;
    private boolean success;
    private String redirectUrl;
    private Integer groupId;
    private int userid;

    public LoginResponse(String message, boolean success, String redirectUrl, Integer groupId ,Integer userid) {
        this.message = message;
        this.success = success;
        this.redirectUrl = redirectUrl;
        this.groupId = groupId;
        this.userid= userid;
    }
    
    public Integer getGroupId() {
		return groupId;
	}

	public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public String getRedirectUrl() {
        return redirectUrl;
    }

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}

