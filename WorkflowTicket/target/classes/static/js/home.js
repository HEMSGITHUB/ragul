let userGroupId = localStorage.getItem('userGroupId');
let username = localStorage.getItem('username');
let userId = localStorage.getItem('UserId');
console.log(userGroupId);
console.log(username);
console.log(userId);

let tickettype;
let ticketContainers;


// Toggle function to show and hide elements
function toggleDisplay(showElementIds, hideElementIds) {
	showElementIds.forEach(id => {
		document.getElementById(id).style.display = 'block';  // Show elements
	});

	hideElementIds.forEach(id => {
		document.getElementById(id).style.display = 'none';   // Hide elements
	});
}

// Function for creating a ticket
function createticket() {
	toggleDisplay(['RaiseTicket'], ['ticketTableContainer', 'openticket', 'Waitingh1', 'open1', 'onholdticket', 'inprogticket', 'rejectticket', 'rejecth1', 'onholdh1', 'inprog1']);
}

// Function for showing the "wait" ticket view
function waitticket() {
	toggleDisplay(['ticketTableContainer', 'Waitingh1'], ['RaiseTicket', 'openticket', 'open1', 'onholdticket', 'inprogticket', 'rejectticket', 'rejecth1', 'onholdh1', 'inprog1']);
	tickettype =5;
}

// Function for showing the "open" ticket view
function openticket() {
	toggleDisplay(['openticket', 'open1'], ['RaiseTicket', 'ticketTableContainer', 'Waitingh1', 'onholdticket', 'inprogticket', 'rejectticket', 'rejecth1', 'onholdh1', 'inprog1']);
	tickettype=4;
}

// Function for showing the "on hold" ticket view
function holdticket() {
	toggleDisplay(['onholdticket', 'onholdh1'], ['RaiseTicket', 'ticketTableContainer', 'Waitingh1', 'openticket', 'open1', 'inprogticket', 'rejectticket', 'rejecth1', 'inprog1']);
	tickettype = 3;
	ticketContainers = '#onholdticket';
	fetchTicketData2();
}

// Function for showing the "in progress" ticket view
function inprogressticket() {
	toggleDisplay(['inprogticket', 'inprog1'], ['RaiseTicket', 'ticketTableContainer', 'Waitingh1', 'openticket', 'open1', 'onholdticket', 'rejectticket', 'rejecth1', 'onholdh1']);
	tickettype = 1;
	ticketContainers = '#inprogticket';
	fetchTicketData2();
}

// Function for showing the "rejected" ticket view
function rejectedticket() {
	toggleDisplay(['rejectticket', 'rejecth1'], ['RaiseTicket', 'ticketTableContainer', 'Waitingh1', 'openticket', 'open1', 'onholdticket', 'inprogticket', 'onholdh1', 'inprog1']);
	tickettype = 2;
	ticketContainers = '#rejectticket';
	fetchTicketData2();
}


// Function to fetch raisedby,date,projectname,managername
if (userId) {
	function fetchWorkflowData(userId) {
		$.ajax({
			url: `/WorkflowTicket/home/apps?userid=${userId}`,
			type: 'GET',
			contentType: 'application/json',
			success: function(data) {

				var projectName = [];
				var managerName = [];

				document.getElementById("insertraisedBy").value = username;
				document.getElementById("insertraisedDate").value = new Date().toISOString().split("T")[0];

				for (let i = 0; i < data.length; i++) {
					projectName.push(data[i].projectname);
					managerName.push(data[i].mangaername);
				}
				// Select the dropdowns by ID
				const projectDropdown = $("#insertprojectName");
				const managerDropdown = $("#insertoperationManager");

				// Loop through the arrays and populate the dropdowns
				for (let i = 0; i < projectName.length; i++) {
					// Create the option for project name dropdown
					const projectOption = $("<option></option>")
						.attr("value", projectName[i])
						.text(`${projectName[i]}`);

					// Append the option to the project dropdown
					projectDropdown.append(projectOption);

					// Create the option for manager name dropdown
					const managerOption = $("<option></option>")
						.attr("value", managerName[i])
						.text(managerName[i]);

					// Append the option to the manager dropdown
					managerDropdown.append(managerOption);
				}
			},
			error: function(xhr, status, error) {
				console.error("Error fetching workflow data:", error);
			}
		});
	}
	// Call the function to fetch and populate the dropdown
	fetchWorkflowData(userId);
} else {
	console.log("User ID is not available in localStorage");
}


//Fetching clients
function fetchclients() {
	// Get the selected project name from the dropdown
	const projectname = $("#insertprojectName").val();
	const UserName = username;

	if (!projectname) {
		console.log("No project selected.");
		return; // Exit the function if no project is selected
	}

	$.ajax({
		url: `/WorkflowTicket/home/client?projectname=${projectname}&username=${UserName}`,  // Send projectname as query parameter
		type: 'GET',  // Use GET request
		contentType: 'application/json',  // No need for JSON.stringify because we're sending query params
		success: function(data) {
			var clients = data.client;
			var manager = data.manager;

			console.log("clients final : ", clients);
			console.log("manager final : ", manager);

			// Select the client dropdown by ID
			const clientDropdown = $("#insertclients");
			clientDropdown.empty();  // Clear existing options


			// Loop through the clients array and populate the dropdown
			for (let i = 0; i < clients.length; i++) {
				const clientOption = $("<option></option>")
					.attr("value", clients[i])
					.text(clients[i]);  // Display the client name in the dropdown

				// Append the option to the client dropdown
				clientDropdown.append(clientOption);

				const managerDropdown = $("#insertoperationManager");
				managerDropdown.empty();
				if (manager) {
					const managerOption = $("<option></option>")
						.attr("value", manager)
						.text(manager);  // Display the client name in the dropdown
					managerDropdown.append(managerOption);
				}
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching client data:", error);
		}
	});
}

// Fetching Apps
$(document).ready(function() {
	$.ajax({
		url: '/WorkflowTicket/home/app',
		method: 'Get',
		dataType: 'json',
		success: function(data) {

			if (data) {
				var apps = data.apps;
				// Select the client dropdown by ID
				const applicationDropdown = $("#insertapp");
				applicationDropdown.empty();  // Clear existing options


				// Loop through the clients array and populate the dropdown
				for (let i = 0; i < apps.length; i++) {
					const appOption = $("<option></option>")
						.attr("value", apps[i])
						.text(apps[i]);  // Display the client name in the dropdown
					// Append the option to the client dropdown
					applicationDropdown.append(appOption);
				}
			} else {
				alert("No data found.");
			}
		},
		error: function(xhr, status, error) {
			console.error('Error fetching data:', error);
		}
	});
});

//Fetching all software manager
$(document).ready(function() {
	$.ajax({
		url: '/WorkflowTicket/home/sm',
		method: 'Get',
		dataType: 'json',
		success: function(data) {

			if (data) {

				var sm = data;
				// Select the client dropdown by ID
				const assignedToDropdown = $("#insertassignedTo");
				assignedToDropdown.empty();  // Clear existing options

				// Loop through the clients array and populate the dropdown
				for (let i = 0; i < sm.length; i++) {
					const smOption = $("<option></option>")
						.attr("value", sm[i])
						.text(sm[i]);  // Display the client name in the dropdown
					// Append the option to the client dropdown
					assignedToDropdown.append(smOption);
				}
			} else {
				alert("No data found.");
			}
		},
		error: function(xhr, status, error) {
			console.error('Error fetching data:', error);
		}
	});
});

//Submit ticket
$(document).ready(function() {
	$("#submitticket").click(function(e) {
		e.preventDefault(); // Prevent default form submission

		// Capture form values and trim whitespace
		const raisedBy = $("#insertraisedBy").val().trim();
		const raisedDate = $("#insertraisedDate").val().trim();
		const projectName = $("#insertprojectName").val().trim();
		const client = $("#insertclients").val().trim();
		const application = $("#insertapp").val().trim();
		const subject = $("#insertSubject").val().trim(); // Trim whitespace
		const description = $("#insertDescription").val().trim(); // Trim whitespace
		const priority = $("#insertPriority").val().trim();
		const assignedTo = $("#insertassignedTo").val().trim();
		const operationManager = $("#insertoperationManager").val().trim();

		// Initialize an array to store missing fields
		let missingFields = [];

		// Check for missing fields and add them to the missingFields array
		if (!raisedBy) missingFields.push("Raised By");
		if (!raisedDate) missingFields.push("Raised Date");
		if (!projectName) missingFields.push("Project Name");
		if (!client) missingFields.push("Client");
		if (!application) missingFields.push("Application");
		if (!subject) missingFields.push("Subject"); // Check for missing subject
		if (!description) missingFields.push("Description"); // Check for missing description
		if (!priority) missingFields.push("Priority");
		if (!assignedTo) missingFields.push("Assigned To");
		if (!operationManager) missingFields.push("Operation Manager");

		// If there are any missing fields, show them in SweetAlert
		if (missingFields.length > 0) {
			Swal.fire({
				icon: 'error',
				title: 'Please fill all the fields ',
				html: `<p>Required fileds:</p><ul>` +
					missingFields.map(field => `<li>${field}</li>`).join('') +
					`</ul>`,
			});
			return; // Don't proceed if validation fails
		}

		// If all fields are filled, display the SweetAlert with form values for confirmation
		Swal.fire({
			title: 'Confirm your request',
			html: ` 
                <p><strong>Raised By:</strong> ${raisedBy}</p>
                <p><strong>Raised Date:</strong> ${raisedDate}</p>
                <p><strong>Project Name:</strong> ${projectName}</p>
                <p><strong>Client:</strong> ${client}</p>
                <p><strong>Application:</strong> ${application}</p>
                <p><strong>Subject:</strong> ${subject}</p>
                <p><strong>Description:</strong> ${description}</p>
                <p><strong>Priority:</strong> ${priority}</p>
                <p><strong>Assigned To:</strong> ${assignedTo}</p>
                <p><strong>Operation Manager:</strong> ${operationManager}</p>
            `,
			icon: 'question',
			showCancelButton: true,
			confirmButtonText: 'Submit Request',
			cancelButtonText: 'Cancel',
		}).then((result) => {
			if (result.isConfirmed) {
				// Prepare the data object for the API request
				const ticketData = {
					raisedby: raisedBy,
					raiseddate: raisedDate,
					projectname: projectName,
					client: client,
					application: application,
					subject: subject,
					description: description,
					priority: priority,
					assignedto: assignedTo,
					operationmanager: operationManager
				};

				// Make the AJAX request to submit the ticket
				$.ajax({
					url: '/WorkflowTicket/home/ticket',  // Endpoint to your backend API
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(ticketData),  // Convert data to JSON string
					success: function(response) {
						console.log(ticketData);
						Swal.fire(
							'Request Submitted!',
							'Your ticket has been submitted successfully.',
							'success'
						);
					},
					error: function(xhr, status, error) {
						Swal.fire(
							'Submission Failed',
							'There was an error submitting your ticket. Please try again later.',
							'error'
						);
					}
				});

			} else {
				Swal.fire(
					'Cancelled',
					'Your request has been cancelled.',
					'info'
				);
			}
		});
	});
});





// Function to generate the table dynamically based on data
function generateTable(data) {

	let ticketContainer;
	if (userGroupId == 1 || userGroupId == 2) {
		ticketContainer = '#ticketTableContainer';
	} else if (userGroupId == 4) {
		ticketContainer = '#openticket';
	} else {
		ticketContainer = 'null';
	}
	const tableContain = (ticketContainer);
	tableContain.empty(); // Clear previous content

	// Create table and table headers dynamically
	let tableHtml = '<table id="ticketTable" border="1" class="table">';
	let headers = Object.keys(data[0]); // Use the keys of the first object as headers
	tableHtml += '<thead><tr>';
	headers.forEach(header => {
		tableHtml += `<th>${header}</th>`; // Generate table headers dynamically
	});
	tableHtml += '</tr></thead>';

	// Create table rows dynamically
	tableHtml += '<tbody>';
	data.forEach(ticket => {
		tableHtml += '<tr>';
		headers.forEach(header => {
			tableHtml += `<td>${ticket[header]}</td>`; // Fill table data dynamically
		});
		tableHtml += '</tr>';
	});
	tableHtml += '</tbody></table>';

	// Insert the generated table HTML into the container
	tableContain.html(tableHtml);
}


// Function to generate the table dynamically based on data (Function2)
function generateTable2(data) {

	let ticketContainers = ['#inprogticket', '#rejectticket', '#onholdticket'];

	function useTicketContainers() {
		// Loop through each ticket container and perform an action
		ticketContainers.forEach(container => {
			$(container).show();  // For example, you could show the container, or perform another action
		});
	}

	const tableContain = (useTicketContainers);
	tableContain.empty(); // Clear previous content

	// Create table and table headers dynamically
	let tableHtml = '<table id="ticketTable" border="1" class="table">';
	let headers = Object.keys(data[0]); // Use the keys of the first object as headers
	tableHtml += '<thead><tr>';
	headers.forEach(header => {
		tableHtml += `<th>${header}</th>`; // Generate table headers dynamically
	});
	tableHtml += '</tr></thead>';

	// Create table rows dynamically
	tableHtml += '<tbody>';
	data.forEach(ticket => {
		tableHtml += '<tr>';
		headers.forEach(header => {
			tableHtml += `<td>${ticket[header]}</td>`; // Fill table data dynamically
		});
		tableHtml += '</tr>';
	});
	tableHtml += '</tbody></table>';

	// Insert the generated table HTML into the container
	tableContain.html(tableHtml);
}


//Table
$(document).ready(function() {
	const raiseduserid = userId; 
	const group = userGroupId;
	const apiUrl = `/WorkflowTicket/home/waitticket/${raiseduserid}/${group}`;
	
	let ticketContainer;
	  if (userGroupId == 1 || userGroupId ==2) {
	      ticketContainer = '#ticketTableContainer';
	  } else if (userGroupId == 4) {
	      ticketContainer = '#openticket';
	  } else {
	      ticketContainer = '#onholdticket';
	  }

	// Function to fetch ticket data and display in table
	function fetchTicketData() {
		$.ajax({
			url: apiUrl,
			method: 'Post',
			success: function(response) {
				
				console.log('API Response ::', response); 
				if (Array.isArray(response) && response.length === 0) {
					$(ticketContainer).html('<div class="no-records">No waiting tickets found.</div>');
				} else if (response.data && Array.isArray(response.data) && response.data.length === 0) {
					$(ticketContainer).html('<div class="no-records">No waiting tickets found.</div>');
				} else if (Array.isArray(response) && response.length > 0) {
					let tableHtml = '<table><thead><tr>';
					const headers = [" Viewer", "Raised By", "Raised Date", "Project Name", "Client", "Application", "Subject", "Description", "Priority", "Software Manager", "Operation Manager"];

					headers.forEach(header => {
							tableHtml += `<th>${header}</th>`;		
					});
					tableHtml += '</tr></thead><tbody>';

					response.forEach(ticket => {
						tableHtml += '<tr>';
						tableHtml += `<td><button class="btn btn-primary view-btn" data-ticket-id="${ticket.sno}"><i class="fas fa-eye"></i> View</button></td>`;
						tableHtml += `<td>${ticket.raisedby}</td>`;
						tableHtml += `<td>${ticket.raiseddate}</td>`;
						tableHtml += `<td>${ticket.projectname}</td>`;
						tableHtml += `<td>${ticket.client}</td>`;
						tableHtml += `<td>${ticket.application}</td>`;
						tableHtml += `<td>${ticket.subject}</td>`;
						tableHtml += `<td>${ticket.description}</td>`;
						tableHtml += `<td>${ticket.priority}</td>`;
						tableHtml += `<td>${ticket.assignedto}</td>`;
						tableHtml += `<td>${ticket.operationmanager}</td>`;
						tableHtml += '</tr>';
					});

					tableHtml += '</tbody></table>';
					$(ticketContainer).html(tableHtml);
				} else {
					$(ticketContainer).html('<div class="no-records">Unexpected response format.</div>');
				}
				
				
			},
			error: function(xhr, status, error) {
				console.error("Error loading tickets:", error);
				$(ticketContainer).html('<div class="no-records">Failed to load tickets. Please try again later.</div>');
			}
			
		});
	}

	// Fetch ticket data when the page is loaded
	fetchTicketData();
});


// Function to fetch ticket data and render the table in all containers
function fetchTicketData2() {

	const group = userGroupId;
	const apiUrl = `/WorkflowTicket/home/ticket2/${tickettype}/${group}`;
	let thisid = ticketContainers;
	$.ajax({
		url: apiUrl,
		method: 'Post',
		success: function(response) {
			console.log('API Response---------', response);


			if (Array.isArray(response) && response.length === 0) {
				$(thisid).html('<div class="no-records">No waiting tickets found.</div>');
			} else if (response.data && Array.isArray(response.data) && response.data.length === 0) {
				$(thisid).html('<div class="no-records">No waiting tickets found.</div>');
			} else if (Array.isArray(response) && response.length > 0) {
				let tableHtml = '<table><thead><tr>';
				const headers = [" Viewer", "Raised By", "Raised Date", "Project Name", "Client", "Application", "Subject", "Description", "Priority", "Software Manager", "Operation Manager"];

				headers.forEach(header => {
					tableHtml += `<th>${header}</th>`;
				});
				tableHtml += '</tr></thead><tbody>';

				response.forEach(ticket => {
					tableHtml += '<tr>';
					tableHtml += `<td><button class="btn btn-primary view-btn" data-ticket-id="${ticket.sno}"><i class="fas fa-eye"></i> View</button></td>`;
					tableHtml += `<td>${ticket.raisedby}</td>`;
					tableHtml += `<td>${ticket.raiseddate}</td>`;
					tableHtml += `<td>${ticket.projectname}</td>`;
					tableHtml += `<td>${ticket.client}</td>`;
					tableHtml += `<td>${ticket.application}</td>`;
					tableHtml += `<td>${ticket.subject}</td>`;
					tableHtml += `<td>${ticket.description}</td>`;
					tableHtml += `<td>${ticket.priority}</td>`;
					tableHtml += `<td>${ticket.assignedto}</td>`;
					tableHtml += `<td>${ticket.operationmanager}</td>`;
					tableHtml += '</tr>';
				});

				tableHtml += '</tbody></table>';
				$(thisid).html(tableHtml);
			} else {
				$(thisid).html('<div class="no-records">Unexpected response format.</div>');
			}
		},
		error: function(xhr, status, error) {
			console.error("Error loading tickets:", error);
			$(thisid).html('<div class="no-records">Failed to load tickets. Please try again later.</div>');
		}

	});

}



//View button or Accept 
$(document).on('click', '.view-btn', function() {
	const ticketId = $(this).data('ticket-id');
	console.log('Selected Ticket ID:', ticketId);

	// First AJAX call to get ticket details
	$.ajax({
		url: `/WorkflowTicket/home/${ticketId}`,  // Example API URL for fetching details
		method: 'GET',
		success: function(ticketDetails) {
			let modalHtml = `
                <p><strong>Raised By:</strong> ${ticketDetails.raisedby}</p>
                <p><strong>Raised Date:</strong> ${ticketDetails.raiseddate}</p>
                <p><strong>Project Name:</strong> ${ticketDetails.projectname}</p>
                <p><strong>Client:</strong> ${ticketDetails.client}</p>
                <p><strong>Application:</strong> ${ticketDetails.application}</p>
                <p><strong>Subject:</strong> ${ticketDetails.subject}</p>
                <p><strong>Description:</strong> ${ticketDetails.description}</p>
                <p><strong>Priority:</strong> ${ticketDetails.priority}</p>
                <p><strong>Software Manager:</strong> ${ticketDetails.assignedto}</p>
                <p><strong>Operation Manager:</strong> ${ticketDetails.operationmanager}</p>
            `;

			// Conditionally add the "Accept Ticket" button based on userGroupId
			if (userGroupId == 2 && tickettype==5) {
				const condition = true;  // This can be any condition you want
				if (condition) {
					modalHtml += `
                        <button id="acceptticket" class="btn btn-primary" data-ticket-id="${ticketId}">Accept Ticket</button>
						<button id="rejectTicket" class="btn btn-danger"  data-ticket-id="${ticketId}">Reject Ticket</button>
                    `;
				}
			}

			// Conditionally add approval/rejection option for GroupId 4
			if (userGroupId == 4 && tickettype==4) {
				const condition = true;  // This can be any condition you want
				if (condition) {
					modalHtml += `
                        <div>
                            <p><strong>Selection :</strong>
                                <label><input type="radio" name="ticketAction" value="approve" id="approve"> Approve</label>
                                <label><input type="radio" name="ticketAction" value="reject" id="reject"> Reject</label>
                            </p>
							
                            <!-- Dropdown for approve will be hidden initially -->
                            <div id="assignDropdown" style="display: none;">
                           
                                <p for="assignedTo"><strong>Assign to:</strong></p>
                                <select id="assignedTo" class="form-control">
                                    
                                </select>
								<button id="assignTicket" class="btn btn-primary" data-ticket-id="${ticketId}">Assign Ticket</button> </div>
                        </div>
						
						<button id="rejectTicket" class="btn btn-danger" style="display: none;" data-ticket-id="${ticketId}">Reject Ticket</button>
						</div>
					`;
				}
			}

			$('#ticketDetails').html(modalHtml);
			$('#ticketModal').modal('show');  // Show the modal

			// Fetch software executives (Se) and populate the dropdown
			$.ajax({
				url: '/WorkflowTicket/home/executive', // Call the backend to get the list of software executives
				method: 'GET',
				success: function(softwareExecutives) {
					if (softwareExecutives && softwareExecutives.length > 0) {
						// Populate the dropdown with Se values
						let optionsHtml = '';
						softwareExecutives.forEach(function(executive) {
							optionsHtml += `<option value="${executive}">${executive}</option>`;
						});
						$('#assignedTo').html(optionsHtml);  // Set the options in the dropdown
					} else {
						$('#assignedTo').html('<option>No software executives found</option>');
					}
				},
				error: function(xhr, status, error) {
					console.error("Error fetching software executives:", error);
					$('#assignedTo').html('<option>Error fetching data</option>');
				}
			});

			// Handle radio button change for showing the "Assign to" dropdown
			$('input[name="ticketAction"]').on('change', function() {
				if ($('#approve').is(':checked')) {
					$('#assignDropdown').show();  // Show the "Assign to" dropdown if 'Approve' is selected
					$('#rejectTicket').hide();    // Hide the "Reject Ticket" button
				} else if ($('#reject').is(':checked')) {
					$('#assignDropdown').hide();  // Hide the "Assign to" dropdown if 'Reject' is selected
					$('#rejectTicket').show();    // Show the "Reject Ticket" button
				}
			});
		},
		error: function(xhr, status, error) {
			console.error("Error fetching ticket details:", error);
		}
	});
});

// Handle "Accept ticket" button click
$(document).on('click', '#acceptticket', function() {
	const sno = $(this).data('ticket-id');
	const groupId = userGroupId;  // Assuming userGroupId is defined and accessible

	console.log("Accepting ticket with ID:", sno);

	// Call the Acceptticket function with the ticketId
	Acceptticket(sno, groupId);
});

// Handle "Assign Ticket" button click
$(document).on('click', '#assignTicket', function() {
	const assignedsno = $(this).data('ticket-id');
	const assignedTo = $('#assignedTo').val();  // Get the selected "Assign to" value

	if (assignedTo) {
		// Call the AssignTicket function with the ticketId and assignedTo
		AssignTicket(assignedsno, assignedTo);
	} else {
		alert("Please select a software executive to assign the ticket.");
	}
});

// Handle "Reject Ticket" button click
$(document).on('click', '#rejectTicket', function() {
	const rejectedId = $(this).data('ticket-id');
	const groupId = userGroupId;
	console.log("Rejecting ticket with ID:", rejectedId);

	// Call the RejectTicket function with the ticketId
	RejectTicket(rejectedId,groupId);
});

// Acceptticket function
function Acceptticket(sno, groupId) {
	console.log("Processing ticket ID:", sno, "for group ID:", groupId);

	$.ajax({
		url: `/WorkflowTicket/home/Acceptticket/${sno}/${groupId}`,
		method: 'GET',
		success: function(res) {
			// Use SweetAlert2 for success message
			Swal.fire({
				title: 'Ticket Accepted!',
				text: 'The ticket has been updated successfully.',
				icon: 'success',
				confirmButtonText: 'Great!',
				confirmButtonColor: '#3085d6'
			}).then(() => {
				// Disable the accept button after accepting
				$('#acceptticket').text("Ticket Accepted").prop('disabled', true);
			});
		},
		error: function(xhr, status, error) {
			// Use SweetAlert2 for error message
			console.error("Error accepting ticket:", error);
			Swal.fire({
				title: 'Oops!',
				text: 'An error occurred while accepting the ticket. Please try again.',
				icon: 'error',
				confirmButtonText: 'Try Again',
				confirmButtonColor: '#d33'
			});
		}
	});
}

// AssignTicket function
function AssignTicket(assignedsno, assignedTo) {
	console.log("Assigning ticket ID:", assignedsno, "to:", assignedTo);

	$.ajax({
		url: `/WorkflowTicket/home/Assignticket/${assignedsno}/${assignedTo}`,  // Send the ticket ID and assignedTo value to the backend
		method: 'GET',
		success: function(res) {
			// Handle the success response
			Swal.fire({
				title: 'Success!',
				text: 'Ticket assigned successfully!',
				icon: 'success',
				confirmButtonText: 'Okay',
			}).then(() => {
				$('#assignTicket').text("Ticket Assigned").prop('disabled', true); // Disable the assign button after assigning
			});
		},
		error: function(xhr, status, error) {
			// Log error to console and show user-friendly message
			console.error("Error assigning ticket:", error);
			Swal.fire({
				title: 'Oops!',
				text: 'An error occurred while assigning the ticket. Please try again.',
				icon: 'error',
				confirmButtonText: 'Try Again',
			});
		}
	});
}

// RejectTicket function
function RejectTicket(rejectedId,groupId) {
	console.log("Processing rejection for ticket ID:", rejectedId,"Groupid",groupId);

	$.ajax({
		url: `/WorkflowTicket/home/Rejectticket/${rejectedId}/${groupId}`,
		method: 'GET',
		success: function(res) {
			// Handle the success response
			Swal.fire({
				title: 'Success!',
				text: 'Ticket rejected successfully!',
				icon: 'success',
				confirmButtonText: 'Okay',
			}).then(() => {
				$('#rejectTicket').text("Ticket Rejected").prop('disabled', true); // Disable the reject button after rejection
			});
		},
		error: function(xhr, status, error) {
			// Log error to console and show user-friendly message
			console.error("Error rejecting ticket:", error);
			Swal.fire({
				title: 'Oops!',
				text: 'An error occurred while rejecting the ticket. Please try again.',
				icon: 'error',
				confirmButtonText: 'Try Again',
			});
		}
	});
}








