package com.Workflow.entity;

import java.util.List;

public class Applications {
	
	private List<String> Apps;
	
	public Applications() {
		
	}
	
	public Applications(List<String> Apps) {
		this.Apps = Apps;
	}

	public List<String> getApps() {
		return Apps;
	}

	public void setApps(List<String> apps) {
		Apps = apps;
	}



}
