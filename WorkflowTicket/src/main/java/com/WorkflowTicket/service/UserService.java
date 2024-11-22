package com.WorkflowTicket.service;

import org.springframework.stereotype.Service;

import com.Workflow.entity.Datas;
import com.Workflow.entity.SubmitTicket;

import com.Workflow.entity.Applications;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;

@Service
public class UserService {

	private static final String URL = "jdbc:sqlserver://10.50.1.136\\\\sqlexpress:1433;databaseName=Fresher;encrypt=true;trustServerCertificate=true";
	private static final String USER = "Fresher";
	private static final String PASSWORD = "Fresh@123";
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection conn = null;
	Statement stmt = null;

	// Checking login users
	public boolean validateUser(String username, String password) {
		String sql = "SELECT COUNT(*) FROM User_Credentials WHERE Username = ? AND Password = ?";

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);

			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// Getting group id
	public Integer getGroupIdByUsername(String username) {
		String sql = "SELECT GroupId FROM User_Credentials WHERE Username = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("GroupId");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// Getting userid id 
	public Integer getUserIdByUsername(String username) {
		String sql = "SELECT UserId FROM User_Credentials WHERE Username = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("UserId");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// Fetching project and manger name based on userid
	public List<Datas> getWorkflowdatas(int userid) {
		String query = "select Project_name,Manager_name from User_Credentials where UserId=?";
		List<Datas> data = new ArrayList<>();
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				data.add(new Datas(rs.getString("Manager_name"), rs.getString("Project_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}

		return data; 
	}

	// fetching client by projectname
	public List<String> getClientbyproject(String projectname) {
		String query = "{Call Getclients_pro(?)}";
		List<String> clients = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, projectname);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				clients.add( rs.getString("client") );
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return clients;
	}
	
	//Fetch manager by project
	public String getMangerbyproject(String projectname , String username) {
		String query = "select Manager_name from User_Credentials where Project_name = ? and username = ?;";
		String Mangername = null;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, projectname);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				 Mangername = rs.getString("Manager_name");
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Log the exception
		}

		return Mangername; // Return the employee or null if not found
	}
	
	// fetching projectname and client for create table
	public Applications getWorkflowapps() {
		String query = "SELECT * FROM applications";
		 Applications ap = new Applications();
		 
		 List<String> app = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
			   app.add(rs.getString("Applictions"));
			}
			
			if(app!=null) {
				ap.setApps(app);
			}
				
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return ap; 
	}
	
	//Fetch all software manager
	public List<String> getsoftwaremanager() {
		String query = "select Username from User_Credentials where GroupId=4";
		List<String> SM = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				SM.add( rs.getString("Username") );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SM; 
	}
	
	//Raise Ticket
	public boolean SubmitTicket (String raisedby, String raiseddate, String projectname, String client, String application,
		String subject, String description, String priority, String assignedto, String operationmanager) {
		String query = "{Call Ticketraise(?,?,?,?,?,?,?,?,?,?)}";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				CallableStatement cstmt = conn.prepareCall(query)) {
			
			cstmt.setString(1, raisedby);
			cstmt.setString(2, raiseddate);
			cstmt.setString(3, projectname);
			cstmt.setString(4, client);
			cstmt.setString(5, application);
			cstmt.setString(6, subject);
			cstmt.setString(7, description);
			cstmt.setString(8, priority);
			cstmt.setString(9, assignedto);
			cstmt.setString(10, operationmanager);
			
			int rowsInserted = cstmt.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	//Fetcht ticket table(1)
	public List<SubmitTicket> getwaitingticket( int raiseduserid, int manageruserid) {
	    List<SubmitTicket> waitingTicket = new ArrayList<>();
	    String query = "{Call Tickettable(?,?)}"; 			

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         CallableStatement cstmt = conn.prepareCall(query)) {

	        cstmt.setInt(1, raiseduserid); // Set the raisedUserId parameter
	        cstmt.setInt(2, manageruserid);
	        ResultSet rs = cstmt.executeQuery();

	        while (rs.next()) {
	            // Create a new Ticket object
	        	SubmitTicket ticket = new SubmitTicket();
	            
	            // Populate the ticket object with data from ResultSet
	        	ticket.setSno(rs.getInt("S.No"));
	            ticket.setRaisedby(rs.getString("Raised_By"));
	            ticket.setRaiseddate(rs.getString("Raised_Date"));
	            ticket.setProjectname(rs.getString("Project_Name"));
	            ticket.setClient(rs.getString("Client"));
	            ticket.setApplication(rs.getString("Application"));
	            ticket.setSubject(rs.getString("Subject"));
	            ticket.setDescription(rs.getString("Description"));
	            ticket.setPriority(rs.getString("Priority"));
	            ticket.setAssignedto(rs.getString("Software_manager"));
	            ticket.setOperationmanager(rs.getString("Operation_Manager"));
	            
	            // Add the ticket object to the list
	            waitingTicket.add(ticket);       
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log the exception
	    }

	    return waitingTicket; // Return the list of Ticket objects
	}
	
	//(2)
	public List<SubmitTicket> getticket( int tickettype, int manageruserid) {
	    List<SubmitTicket> waitingTicket = new ArrayList<>();
	    String query = "{Call Tickettable1(?,?)}"; 			

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         CallableStatement cstmt = conn.prepareCall(query)) {

	        cstmt.setInt(1, tickettype); // Set the raisedUserId parameter
	        cstmt.setInt(2, manageruserid);
	        ResultSet rs = cstmt.executeQuery();

	        while (rs.next()) {
	            // Create a new Ticket object
	        	SubmitTicket ticket = new SubmitTicket();
	            
	            // Populate the ticket object with data from ResultSet
	        	ticket.setSno(rs.getInt("S.No"));
	            ticket.setRaisedby(rs.getString("Raised_By"));
	            ticket.setRaiseddate(rs.getString("Raised_Date"));
	            ticket.setProjectname(rs.getString("Project_Name"));
	            ticket.setClient(rs.getString("Client"));
	            ticket.setApplication(rs.getString("Application"));
	            ticket.setSubject(rs.getString("Subject"));
	            ticket.setDescription(rs.getString("Description"));
	            ticket.setPriority(rs.getString("Priority"));
	            ticket.setAssignedto(rs.getString("Software_manager"));
	            ticket.setOperationmanager(rs.getString("Operation_Manager"));
	            
	            // Add the ticket object to the list
	            waitingTicket.add(ticket);       
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log the exception
	    }

	    return waitingTicket; // Return the list of Ticket objects
	}
	// View Ticket
	public SubmitTicket getTicketDetails(int ticketId) {
		String query = "SELECT * FROM TicketDetail WHERE [S.No] = ?"; // Adjust if the column name is different

		SubmitTicket ticket = null;

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			// Set the ticketId parameter
			pstmt.setInt(1, ticketId);

			// Execute the query
			ResultSet rs = pstmt.executeQuery();

			// If a ticket with the given ticketId is found
			if (rs.next()) {
				// Map the ResultSet to a SubmitTicket object
				ticket = new SubmitTicket(rs.getInt("S.No"), // raiseduserid
						rs.getString("raised_by"), // raisedby
						rs.getString("raised_date"), // raiseddate
						rs.getString("project_name"), // projectname
						rs.getString("client"), // client
						rs.getString("application"), // application
						rs.getString("subject"), // subject
						rs.getString("description"), // description
						rs.getString("priority"), // priority
						rs.getString("Software_manager"), // assignedto
						rs.getString("operation_manager") // operationmanager
			
				);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Log the exception for debugging
		}

		return ticket; // Return the ticket object or null if not found
	}
	
	
	//Accept Ticket
		public boolean Acceptticket (int sno , int groupid) {
			String query = "{Call Acceptticket(?,?)}";

			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					CallableStatement cstmt = conn.prepareCall(query)) {
				
				cstmt.setInt(1, sno);
				cstmt.setInt(2, groupid);
				
				int rowsInserted = cstmt.executeUpdate();
		        return rowsInserted > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false;
		}
		
		
		// fetching Software executive
		public List<String> getsoftwarexecutive() {
			String query = "select Username from User_Credentials where GroupId =3";
			//Applications Se = new Applications();

			List<String> Se = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(query)) {

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {

					Se.add(rs.getString("Username"));
				}

//				if (app != null) {
//					Se.setApps(app);
//				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return Se;
		}
		
		//Assigning Software executive by Software Manager
		public boolean AssingnSe(int assignedsno,String assignedTo) {
			String query = "update TicketDetail set status =1,AssignTo = ? where [S.No] =?";
			
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					CallableStatement cstmt = conn.prepareCall(query)) {
				
				cstmt.setString(1, assignedTo);
				cstmt.setInt(2, assignedsno);
				
				int rowsInserted = cstmt.executeUpdate();
		        return rowsInserted > 0;
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false;
		}
		
		//Rejected by Software Manager
		public boolean RejectTicket(int rejectedId ,int groupid) {
			String query = "{Call Rejectticket ( ? , ? )}";
			
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				 CallableStatement cstmt = conn.prepareCall(query)) {
				
				cstmt.setInt(1, rejectedId);
				cstmt.setInt(2, groupid);
					
				int rowsInserted = cstmt.executeUpdate();
		        return rowsInserted > 0;
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false;
		}
	
}