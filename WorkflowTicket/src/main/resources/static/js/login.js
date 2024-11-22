function setUserGroupId(groupId) {
	    localStorage.setItem('userGroupId', groupId);
	    console.log('User Group ID set in localStorage:', groupId); // Log the value that was set
}	   
function setUserId(userid) {
	    localStorage.setItem('UserId', userid);
	    console.log('User User ID set in localStorage:', userid); // Log the value that was set
}	

function CheckUser() {
	var username = $('#username').val(); 
	var password = $('#password').val();

	$.ajax({
		url: '/WorkflowTicket',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			username: username,
			password: password
		}),
		success: function(response) {
			debugger;
			console.log('Login successful', response);
			localStorage.setItem('username', username);
			setUserGroupId(response.groupId); 
			setUserId(response.userid);
			window.location.href = response.redirectUrl; // Ensure response.redirectUrl is defined			   
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.error('Login failed:', textStatus, errorThrown);
			// Show SweetAlert for failed login
			Swal.fire({
				title: 'Error!',
				text: 'Invalid credentials, please try again.',
				icon: 'error'
			});
		}
	});
}