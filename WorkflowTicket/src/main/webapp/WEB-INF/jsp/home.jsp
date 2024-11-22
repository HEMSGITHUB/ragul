<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- Include Bootstrap 5 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Include Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.0/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.0/dist/sweetalert2.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"><!--VIEWBUTTON  -->
<link rel="stylesheet" href="/css/home.css">
<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <nav class="sidebar">
  <header>
    <div class="text header-text">
      <span class="main">Sidebar</span>
    </div>
  </header>
  <div class="menu-bar">
    <div class="menu">
      <ul class="menu-links">
        <li class="nav-link">
          <a onclick="createticket()">
            <i class="bi bi-send-plus-fill icons"></i>
            <span class="text nav-text">Create Ticket</span>
          </a>
        </li>
        <li class="nav-link">
          <a onclick="waitticket()">
            <i class="bi bi-hourglass-split icons"></i>
            <span class="text nav-text">Waiting Ticket</span>
          </a>
        </li>
        <li class="nav-link">
          <a href="#" onclick="openticket()">
            <i class="bi bi-send-check-fill icons"></i>
            <span class="text nav-text">Open Ticket</span>
          </a>
        </li>
        <li class="nav-link">
          <a href="#" onclick="holdticket()">
            <i class="bi bi-send-exclamation-fill icons"></i>
            <span class="text nav-text">On Hold Ticket</span>
          </a>
        </li>
        <li class="nav-link">
          <a href="#" onclick="inprogressticket()">
            <i class="bi bi-arrow-repeat icons"></i>
            <span class="text nav-text">In Progress Ticket</span>
          </a>
        </li>
        <li class="nav-link">
          <a href="#" onclick="rejectedticket()">
           <i class="bi bi-send-x-fill icons"></i>
            <span class="text nav-text">Rejected Ticket</span>
          </a>
        </li>
      </ul>
    </div>

    <div class="bottom-content">
      <li class="nav-link">
        <a href="#">
          <i class="bi bi-box-arrow-right icons"></i> <!-- Bootstrap icon for logout -->
          <span class="text nav-text">Log Out</span>
        </a>
      </li>
    </div>
  </div>
</nav>
    
    
										<!-- WAITING TICKET TABLE -->
										
	<div class="container">
		<h2 id="Waitingh1" style="display: none;" >Waiting Tickets</h2>
		<div id="ticketTableContainer" style="display: none;">
		
			<!-- Table will be dynamically populated here -->
		</div>
	</div>
	
										<!-- OPEN TICKET TABLE -->
										
	<div class="container">
		<h2 id="open1" style="display: none;" >Open Tickets</h2>
		<div id=openticket style="display: none;" >
		
			<!-- Table will be dynamically populated here -->
		</div>
	</div>
	
										<!-- ONHOLD TICKET TABLE -->
										
	<div class="container">
		<h2 id="onholdh1" style="display: none;" >Onhold Tickets</h2>
		<div id="onholdticket" style="display: none;">
		
			<!-- Table will be dynamically populated here -->
		</div>
	</div>
	
										<!-- INPROGRESS TICKET TABLE -->
										
	<div class="container">
		<h2 id="inprog1" style="display: none;" >Inprogress Tickets</h2>
		<div id="inprogticket" style="display: none;">
		
			<!-- Table will be dynamically populated here -->
		</div>
	</div>
	
										<!-- REJECTED TICKET TABLE -->
										
	<div class="container">
		<h2 id="rejecth1" style="display: none;" >Rejected Tickets</h2>
		<div id="rejectticket" style="display: none;">
		
			<!-- Table will be dynamically populated here -->
		</div>
	</div>
	
										<!-- ACCEPT TICKET -->


<div class="modal fade" id="ticketModal" tabindex="-1" role="dialog" aria-labelledby="ticketModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ticketModalLabel">Ticket Details</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div id="ticketDetails"></div>
      </div>
       <div class="modal-footer">
     <!--    Accept button positioned to the left
        <button type="button" class="btn btn-primary" onclick="Acceptt icket()" id ="acceptticket" style="display: none" >Accept</button>
        
        Close button positioned to the right
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>  -->
      </div>
      
    </div>
  </div>
</div>

										<!-- REQUEST TICKET -->
										
    <form id="RaiseTicket" style="display: none;">
     <h2 id ="h2">Request Ticket</h2>
        <label for="insertraisedBy">Raised By:</label>
        <input type="text" id="insertraisedBy" name="insertraisedBy" >

        <label for="insertraisedDate">Raised Date:</label>
        <input type="date" id="insertraisedDate" name="insertraisedDate" value=""  >

        <label for="insertprojectName">Project Name:</label>
        <select id="insertprojectName" name="insertprojectname" required onchange="fetchclients()">
             <option value="">Select a project</option>
       
        </select>

        <label for="insertclients">Client:</label>
        <select id="insertclients" name="insertclients" >
          <option value="">Select a client</option>
    
        </select>

        <label for="insertapp">Application:</label>
        <select id="insertapp" name="insertapp" >
          <option value="">Select an application</option>
        </select>

        <label for="insertSubject">Subject:</label>
        <textarea id="insertSubject" name="insertSubject" maxlength="300" ></textarea>

        <label for="insertDescription">Description:</label>
        <textarea id="insertDescription" name="insertDescription" maxlength="1000" ></textarea>

        <label for="insertPriority">Priority:</label>
        <select id="insertPriority" name="insertPriority" >
            <option value="">Select priority</option>
            <option value="High">High</option>
            <option value="Medium">Medium</option>
            <option value="Low">Low</option>
        </select>

        <label for="insertassignedTo">Assigned To:</label>
        <select id="insertassignedTo" name="insertassignedTo" >
            <option value="">Select an assignee</option>
    
        </select>

        <label for="insertoperationManager">Operation Manager:</label>
        <select id="insertoperationManager" name="insertoperationManager" >
            <option value="">Select an operation manager</option>
          
        </select>

        <button type="submit" id="submitticket">Request to Submit</button>
    </form>
    
    <script src="/js/login.js"></script>
    <script src="/js/home.js"></script>
</body>
</html>