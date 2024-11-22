package com.Workflow.entity;

public class SubmitTicket {
	private int sno;
	private int raiseduserid;
	private String raisedby;
	private String raiseddate;
	private String projectname;
	private String client;
	private String application;
	private String subject;
	private String description;
	private String priority;
	private String assignedto;
	private String operationmanager;
	private int manageruserid;
	

	public SubmitTicket(int raiseduserid,String raisedby, String raiseddate, String projectname, String client, String application,
			String subject, String description, String priority, String assignedto, String operationmanager) {
		this.raiseduserid=raiseduserid;
		this.raisedby = raisedby;
		this.raiseddate = raiseddate;
		this.projectname = projectname;
		this.client = client;
		this.application = application;
		this.subject = subject;
		this.description = description;
		this.priority = priority;
		this.assignedto = assignedto;
		this.operationmanager = operationmanager;
		this.manageruserid=manageruserid;
	}
	

	public int getSno() {
		return sno;
	}


	public void setSno(int sno) {
		this.sno = sno;
	}


	public SubmitTicket() {
	}


	public String getRaisedby() {
		return raisedby;
	}
	public void setRaisedby(String raisedby) {
		this.raisedby = raisedby;
	}
	public String getRaiseddate() {
		return raiseddate;
	}
	public void setRaiseddate(String raiseddate) {
		this.raiseddate = raiseddate;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getAssignedto() {
		return assignedto;
	}
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}
	public String getOperationmanager() {
		return operationmanager;
	}
	public void setOperationmanager(String operationmanager) {
		this.operationmanager = operationmanager;
	}
	public int getRaiseduserid() {
		return raiseduserid;
	}
	public void setRaiseduserid(int raiseduserid) {
		this.raiseduserid = raiseduserid;
	}
	public int getManageruserid() {
		return manageruserid;
	}
	public void setManageruserid(int manageruserid) {
		this.manageruserid = manageruserid;
	}
	
}
