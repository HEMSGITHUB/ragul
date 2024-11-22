package com.Workflow.entity;

import java.util.*;

public class ClientAndManager {
	
	private List<String> client;
	private String manager;
	
	public ClientAndManager() {
		
	}
	
	public ClientAndManager(List<String> client, String manager) {
		this.client = client;
		this.manager = manager;
	}

	public List<String> getClient() {
		return client;
	}

	public void setClient(List<String> client) {
		this.client = client;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	
	
	

}
