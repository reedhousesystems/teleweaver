

	/**
	 * Create a user
	 *
	 * @param {string}
	 * @param {string}
	 * @param {string}
	 * @param {string}
	 * @param {function}
	 */
	
	var createUser = function (organisationId, firstName, lastName, userName, role, password, callback) {
		postAuth(
		'user/organisation/' + organisationId,
	    {user :
	      {
	        "firstName" : firstName,
	        "lastName" : lastName,
	        "userName" : userName,
	        "role": role,
		    "status" : userStatus.LOGEDOUT
	      },
	    "password" : password
	    },
	    function (response) {
	      callback()
	    },
	    function(jqXHR, textStatus) {
	      console.log(jqXHR)
	      callback(jqXHR)
	    })	    
	}


	/**
	 * Update first name
	 * @param {function}
	 */
	var updateUser = function (userId, firstName, lastName, userName, role, callback) {

		putAuth(
		'user/' + userId,
	      {
	    	"firstName" : firstName,
	        "lastName" : lastName,
	        "userName" : userName,
	        "role": role
	      },
	    function () {
	      if (callback)
	        callback()
	    },
	    function(jqXHR, textStatus) {
	      if (callback)
	        callback(jqXHR)
	    })
	}

	/**
	 * Update contacts
	 * @param phoneNumber
	 */

	var updateContacts = function(emailAddress, phoneNumber, callback) {
		
		putAuth(
			'user/' + $.cookie('dashboard-userId') + '/contact',
		    {
		    	"emailAddress" : emailAddress,
		    	"phoneNumber" : phoneNumber
		    },
		    function (response) {
		      console.log(response)
		      if (callback)
		        callback()
		      // Clear user cache
		      downloadUser()
		    },
		    function(jqXHR, textStatus) {
		      if (callback)
		        callback(jqXHR)
		      // Clear user cache
		      downloadUser()
		    }
		)
	}

	var updateUserStatus = function(userId, status, callback) {
		putAuth(
			'user/' + userId + '/status',
			{ "status" : status },
			function (response) {
				callback()
			},
			function(jqXHR, textStatus) {
				callback(jqXHR)
			}
		)
	}
	
	var udateUsers = function(status, users, callback) {
		putAuth('user',
			{"status" : status, "user" : users},
			function(response) {
				callback()
			},
			function(jqXHR, textStatus) {
				callback(jqXHR)
			}
		)
	}

	var deleteUser = function(userId, callback) {
		delAuth(
		  'user/'+ userId,
		  {},
		  function () {
		      callback()
		  },
		  function(jqXHR, textStatus) {
		      callback(jqXHR)
		  }
			
		)
	}

	/**
	 * Get user info
	 * @param {function}
	 */
	
	var getUser = function (userId, callback) {
		getAuth(
			'user/' + userId,
			{},
			function (response) {
				callback(response)
			},
			function(jqXHR, textStatus) {
				callback(jqXHR, textStatus)
			}
		)
	}

	var getContacts = function(userId, callback) {		
		getAuth(
			'user/'+ $.cookie('dashboard-userId') + '/contact',
			{},
			function (response) {
				if (callback)
					callback()
			},
			function(jqXHR, textStatus) {
				if (callback)
					callback(jqXHR)
			}
		)
	}

	var getDemographics = function(userId, callback) {		
		getAuth(
			'user/'+ $.cookie('dashboard-userId') + '/demographic',
			{},
			function (response) {
				if (callback)
					callback()
			},
			function(jqXHR, textStatus) {
				if (callback)
					callback(jqXHR)
			}
		)	
	}

	function getAllUsers(callback) {		
		getAuth(
			'user',
			{},
			function (response) {
				if (callback)
					callback(response)
			},
			function(jqXHR, textStatus) {
				if (callback)
					callback(textStatus, jqXHR)
			}			
		)
	}