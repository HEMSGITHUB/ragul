package com.Workflow.entity;

public class Datas {
	private String projectname;
	private String client;
	private String mangaername;
	private String Application;
	
	public Datas(String client) {
		this.client=client;
}

	public Datas(String mangaername,String projectname) {
		this.projectname=projectname;
		this.mangaername=mangaername;
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
		return Application;
	}

	public void setApplication(String application) {
		Application = application;
	}

	public String getMangaername() {
		return mangaername;
	}

	public void setMangaername(String mangaername) {
		this.mangaername = mangaername;
	}
	
}
