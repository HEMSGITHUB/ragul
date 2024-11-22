package com.WorkflowTicket.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Workflow.entity.Applications;
import com.Workflow.entity.ClientAndManager;
import com.Workflow.entity.Datas;
import com.Workflow.entity.LoginRequest;
import com.Workflow.entity.LoginResponse;
import com.Workflow.entity.SubmitTicket;
import com.WorkflowTicket.service.UserService;



@Controller
@RequestMapping("/WorkflowTicket")
public class LoginController {
	
	@Autowired//Autowireing the userservice
	private UserService service;
	
	
	@GetMapping("/login")//Login Page
	public String loginpage() {
		return "login";
	}
	
	@PostMapping(consumes = "application/json")// Checking for the given user id and Specify that this endpoint consumes JSON
	public ResponseEntity<LoginResponse> log(@RequestBody LoginRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		int groupId = service.getGroupIdByUsername(username); 
		int userid =service.getUserIdByUsername(username);
		
		boolean isValidUser = service.validateUser(username, password);
		
		if (isValidUser) {
			return ResponseEntity.ok(new LoginResponse("Login successful", true, "/WorkflowTicket/home",groupId, userid)); // Login successful

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new LoginResponse("Invalid credentials", false, null,null,null)); // Invalid credentials
		}
	}
	
	@GetMapping("/home")//Mapping Home page
	public String showTable(Model model) {
		return "home";
	}
	
	@GetMapping("/home/apps") // Change to @GetMapping
	public ResponseEntity <List<Datas>> getWorkflowapps(@RequestParam int userid) { // Change @RequestBody to @RequestParam    
	    List<Datas> data = service.getWorkflowdatas(userid); // Pass the userid to the service
	    if (data!= null) {
	        return ResponseEntity.ok(data);
	    } else {
	        return ResponseEntity.notFound().build(); // Return 404 if not found
	    }
	}

	@GetMapping("/home/client") 
	public ResponseEntity <ClientAndManager> getClientbyproject(@RequestParam String projectname, @RequestParam String username) { 
	    ClientAndManager cm = new ClientAndManager();
	    
	    List<String> Client = service.getClientbyproject(projectname); 
	    cm.setClient(Client);
	    
	    String Manager = service.getMangerbyproject(projectname,username);
	    cm.setManager(Manager);
	    
	    if (cm.getClient()!=null && cm.getManager()!=null) {
	        return ResponseEntity.ok(cm);
	    } else {
	        return ResponseEntity.notFound().build(); // Return 404 if not found
	    }
	}
	
	@GetMapping("/home/app") 
	public ResponseEntity<Applications> getWorkflowapps() { 
		Applications app = service.getWorkflowapps();
	    if (app!= null) {
	        return ResponseEntity.ok(app);
	    } else {
	        return ResponseEntity.notFound().build(); // Return 404 if not found
	    }
	}
	
	@GetMapping("/home/sm") //Get softwaremanager 
	public ResponseEntity<List<String>> getsoftwaremanager() { 
		List<String> sm = service.getsoftwaremanager();
	    if (sm!= null) {
	        return ResponseEntity.ok(sm);
	    } else {
	        return ResponseEntity.notFound().build(); // Return 404 if not found
	    }
	}
	
	@PostMapping("/home/ticket")
	public ResponseEntity<String> SubmitTicket(@RequestBody SubmitTicket ticket){
		 boolean success = service.SubmitTicket(ticket.getRaisedby(), ticket.getRaiseddate(), ticket.getProjectname(), ticket.getClient(),
				 ticket.getApplication(), ticket.getSubject(), ticket.getDescription(), ticket.getPriority(), ticket.getAssignedto(), ticket.getOperationmanager());
		if (success) {
			 return ResponseEntity.ok("Employee data submitted successfully!");
		} else {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting data.");
		}
	}

	
//	@GetMapping("/home/waitticket/{raiseduserid}/{group}")
//	public ResponseEntity<List<Map<String, Object>>> getwaitingticket(@PathVariable int raiseduserid, @PathVariable int group) {
//
//	    List<Map<String, Object>> Waitingticket = service.getwaitingticket(raiseduserid, group);
//
//	    if (Waitingticket.isEmpty()) {
//	        return ResponseEntity.noContent().build();
//	    }
//
//	    return ResponseEntity.ok(Waitingticket);
//	}
	
	@PostMapping("/home/waitticket/{raiseduserid}/{group}")// FOR create,wait,open
	public ResponseEntity<List<SubmitTicket>> getwaitingticket(@PathVariable int raiseduserid, @PathVariable int group) {

	    List<SubmitTicket> Waitingticket = service.getwaitingticket(raiseduserid, group);

	    if (Waitingticket.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }

	    return ResponseEntity.ok(Waitingticket);
	}
	
	@PostMapping("/home/ticket2/{tickettype}/{group}")// FOR Onhold ,inprogress,rejected
	public ResponseEntity<List<SubmitTicket>> getticket(@PathVariable int tickettype, @PathVariable int group) {

	    List<SubmitTicket> Waitingticket = service.getticket(tickettype, group);

	    if (Waitingticket.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }

	    return ResponseEntity.ok(Waitingticket);
	}
	
	@GetMapping("/home/{ticketId}")
    public ResponseEntity<SubmitTicket> getTicketDetails(@PathVariable int ticketId) {
		SubmitTicket ticket = service.getTicketDetails(ticketId);
        if (ticket == null) {
            return ResponseEntity.notFound().build();  // Return 404 if ticket is not found
        }
        return ResponseEntity.ok(ticket);  // Return 200 with ticket data if found
    }
	
	@GetMapping("/home/Acceptticket/{sno}/{groupid}")
	public ResponseEntity<String> Acceptticket(@PathVariable int sno, @PathVariable int groupid) {

	    boolean success= service.Acceptticket(sno, groupid);
	    if (success) {
			 return ResponseEntity.ok("Employee Sent successfully!");
		} else {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting data.");
		}
	}

	@GetMapping("/home/executive") //Fetching software executive
	public ResponseEntity<List<String>> getsoftwarexecutive() { 
		List<String> Se = service.getsoftwarexecutive();
	    if (Se!= null) {
	        return ResponseEntity.ok(Se);
	    } else {
	        return ResponseEntity.notFound().build(); // Return 404 if not found
	    }
	}
	
	
	@GetMapping("/home/Assignticket/{assignedsno}/{assignedTo}") //Assigning software executive
	public ResponseEntity<String> AssingnSe(@PathVariable int assignedsno,@PathVariable String assignedTo) { 
		boolean success = service.AssingnSe(assignedsno,assignedTo);
	    
		if (success) {
	        return ResponseEntity.ok("software executive assigned successfully!");
	    } else {
	    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting data");
	    }
	}
	
	@GetMapping("/home/Rejectticket/{rejectedId}/{groupid}") //Rejecting open ticket by software manager
	public ResponseEntity<String> RejectTicket(@PathVariable int rejectedId , @PathVariable int groupid) { 
		boolean success = service.RejectTicket(rejectedId,groupid);
	    
		if (success) {
	        return ResponseEntity.ok("Rejected ticket successfully!");
	    } else {
	    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rejecting ticket");
	    }
	}
}