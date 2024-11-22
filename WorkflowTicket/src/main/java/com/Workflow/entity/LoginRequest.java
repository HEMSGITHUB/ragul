package com.Workflow.entity;

public class LoginRequest {
    private String username;
    private String password;
    private int groupid;    
    private String redirectUrl;
    private int Userid;
    
    public LoginRequest() {
    	
    }
    
	public LoginRequest(String username, String password, int groupid, String redirectUrl, int Userid) {
		
		this.username = username;
		this.password = password;
		this.groupid = groupid;
		this.redirectUrl = redirectUrl;
		this.Userid = Userid;
	}

	// Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRedirectUrl() { // Getter for redirectUrl
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) { // Setter for redirectUrl
        this.redirectUrl = redirectUrl;
    }

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
    
	public int getUserid() {
		return Userid;
	}

	public void setUserid(int Userid) {
		this.Userid = Userid;
	}
}
