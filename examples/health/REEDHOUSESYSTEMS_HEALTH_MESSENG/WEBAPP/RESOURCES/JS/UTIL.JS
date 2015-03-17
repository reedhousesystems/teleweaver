
var dashboardURL = "http://localhost:8080/health-dashboard/";

toastr.options = {
  "closeButton": false,
  "debug": false,
  "positionClass": "toast-top-full-width",
  "onclick": null,
  "showDuration": "300",
  "hideDuration": "1000",
  "timeOut": "5000",
  "extendedTimeOut": "1000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
}

var clearInput = function() {
	$(':input').not(':button, :submit, :reset, :hidden').val('');
	$('textarea').val('');
}

var closeModal = function () {
	$('.modal-title').empty();
	$('.modal-body').empty();
	$('.modal-footer').empty();
	$('#modal_dialog').modal('hide');
}

var uncheckCheckboxes = function () {
	$('.icheck-input').iCheck('uncheck');
}

var displayFormInputError = function(event, validator) {
	var errors = validator.numberOfInvalids();
	if (errors) {
		var message = errors == 1 ? 'You missed 1 field. Please fill in the field highlited in red' : 'You missed ' + errors + ' fields. Please fill in the fields highlighted in red';
		toastr.error(message);
	}			
	return false;
}
var status = {
	LOGGEDOUT: 0,
	LOGGEDIN: 1,
	LOCKED: 2,
	INACTIVE: 3
}

/**
 * @return {bool}
 */
var is_logged_in = function () {
	return !!$.cookie('token')
}

/**
 * @return {bool}
 */
var profileComplete = function() {
	return !!$.cookie('profile-complete')
}

/**
 * Log the user in
 * @param {string}
 * @param {string}
 * @param {function} Callback. First parameter is error, if any.
 */
var login = function (username, password, callback) {
	var padding = "";
	put(padding, 'user/login', JSON.stringify({ "username" : username, "password" : password, "status" : status.LOGGEDIN }), function (response, error) {
		if (error)
			callback(response, error);
		else {
			$.cookie('token', response.token, { path: '/' });
			$.cookie('userId', response.userId, { path: '/' });
			$.cookie('username', username, { path: '/' });
			store.set('currentUser', JSON.stringify(response));
			if (response.profileComplete == true)
				$.cookie('profile-complete', true, { path: '/' });
			callback(response);
		}
	});
}

var logoutAdmin = function(padding, nextWindow) {
	put(padding, '../user/' + $.cookie('userId') + '/logout', JSON.stringify({"status": status.LOGGEDOUT}), function(error) {
		if (error) {
			console.log(error);
		}
		$.removeCookie('token', { path: '/' })
		$.removeCookie('userId', { path: '/' })
		$.removeCookie('username', { path: '/' })
		store.clear()
		window.location = nextWindow
	});
}

var logout = function(padding, nextWindow) {
	put(padding, 'user/' + $.cookie('userId') + '/logout', JSON.stringify({"status": status.LOGGEDOUT}), function(error) {
		if (error) {
			console.log(error);
		}
		$.removeCookie('token', { path: '/' })
		$.removeCookie('userId', { path: '/' })
		$.removeCookie('username', { path: '/' })
		store.clear()
		window.location = nextWindow;
	});
}

var setChangePasswordForm = function(padding) {
	$(".modal-title").html("Change Password");
	$(".modal-body").html(
		'<form id="frmChangePassword" role="form">' +
			'<div class="form-group">' +
				'<label for="currentPassword">Current Password</label>' +
			    '<input type="password" class="form-control" name="currentPassword" id="currentPassword" placeholder="Current Password">' +
			'</div>' +
			'<div class="form-group">' +
				'<label for="newPassword">New Password</label>' +
				'<input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="New Password">' +
			'</div>' +
			'<div class="form-group">' +
				'<label for="confirmNewPassword">Confirm New Password</label>' +
				'<input type="password" class="form-control" name="confirmNewPassword" id="confirmNewPassword" placeholder="Confirm New Password">' +
			'</div>' +
			'<button type="submit" id="btnChangePassword" class="btn btn-primary">Submit</button>' +
		'</form>'
	);
	$('#modal_dialog').modal('show');
	$("#btnChangePassword").click(function() {
		$("#frmChangePassword").validate({
			rules: {
				currentPassword: {
					required: true,
					minlength: 8
				},
				newPassword: {
					required: true,
					minlength: 8
				},
				confirmNewPassword: {
					required: true,
					minlength: 8
				}
			},
			submitHandler: function() {
				if ($('#newPassword').val().trim() != $('#confirmNewPassword').val().trim()) {
					toastr.error('Passwords do not match');
					return false;
				}
				console.log('Resetting password...');
				putAuth(padding, 'user/' + $.cookie('dashboard-userId') + '/resetpassword', JSON.stringify({ "currentPassword" : $('#currentPassword').val().trim(), "password" : $('#newPassword').val().trim() }), function(data, error) {
					if (error) {
						toastr.error("Change password error: " + error.responseJSON.applicationMessage);
						clearInput();
						return false;
					}
					toastr.success('Password reset successfully');
					closeModal();
				});
			}
		});
	});
}

var setProvinces = function (province) {
	$('#province').append('<option value="">-Select province-</option>' +
		'<option value="Eastern Cape">Eastern Cape</option>' +
		'<option value="Free State">Free State</option>' +
		'<option value="Gauteng">Gauteng</option>' +
		'<option value="KwaZulu-Natal">KwaZulu-Natal</option>' +
		'<option value="Lompopo">Lompopo</option>' +
		'<option value="Mpumalanga">Mpumalanga</option>' +
		'<option value="Northern Cape">Northern Cape</option>' +
		'<option value="North West">North West</option>' +
		'<option value="Western Cape">Western Cape</option>');
	if (province) {
		$('#province option').each(function(index) {
			if ($(this).val() == province)
				$(this).attr('selected', 'selected');
		});
	}
}			

var getDistrictsByProvince = function(padding, province, currentDistrict) {
	get(padding, 'location/province/' + province, function(districts, error) {
		if (error) {
			toastr.error("Error getting districts");
			return false
		}
		var districtsList = districts instanceof Array ? districts : [districts];
		$('#district').html('<option value="">-Select district-</option>')
		$.each(districtsList, function(index, district) {
			$('#district').append('<option value="' + district.district + '">' + district.district + '</option>');
		});
		if (currentDistrict) {
			$('#district option').each(function(index) {
				if ($(this).val() == currentDistrict)
					$(this).attr('selected', 'selected');
			});
		}
	});
}

var getMunicipalitiesByDistrict = function(padding, district, currentMunicipality) {
	get(padding, 'location/district/' + district, function(municipalities, error) {
		if (error) {
			toastr.error("Error getting municipalities");
			return false;
		}
		var municipalitiesList = municipalities instanceof Array ? municipalities : [municipalities];
		$('#municipality').html('<option value="">-Select municipality-</option>');
		$.each(municipalitiesList, function(index, municipality) {
			$("#municipality").append('<option value="' + municipality.municipality + '">' + municipality.municipality + '</option>');
		});
		if (currentMunicipality) {
			$('#municipality option').each(function(index) {
				if ($(this).val() == currentMunicipality)
					$(this).attr('selected', 'selected');
			});
		}
	});
}

var getVillagesByMunicipality = function(padding, municipality, currentVillage) {
	get(padding, 'location/municipality/' + municipality, function(villages, error) {
		if (error) {
			toastr.error("Error getting villages");
			return false;
		}
		var villagesList = villages instanceof Array ? villages : [villages];
		$('#village').html('<option value="">-Select village-</option>');
		$.each(villagesList, function(index, village) {
			$('#village').append('<option value="' + village.village + '">' + village.village + '</option>');
		});
		if (currentVillage) {
			$('#village option').each(function(index) {
				if ($(this).val() == currentVillage)
					$(this).attr('selected', 'selected');
			});
		}
	});
}

var setSex = function (sex) {
	$('#sex').html('<option value="">-Select Sex-</option><option value="Male">Male</option><option value="Female">Female</option>');
	if (sex) {
		$('#sex option').each(function(index) {
			if ($(this).val() == sex)
				$(this).attr('selected', 'selected');
		});
	}
}

var setLangauge = function (language) {
	$('#language').html('<option value="English">English</option><option value="Xhosa">Xhosa</option>');
	if (language) {
		$("#language option").each(function(index) {
			if ($(this).val() == language)
				$(this).attr('selected', 'selected');
		})
	}	
}

var setOccupation = function (padding, currentOccupation) {
	get(padding, 'occupation', function(data, error) {
		if (error) {
			toastr.error("Error getting occupations");
			return false;
		}
		var occupationList = data instanceof Array ? data : [data];
		$('#occupation').html('<option value="">-Select Occupation-</option>');
		$.each(occupationList, function(index, occupation) {
			$('#occupation').append('<option value="' + occupation.name + '">' + occupation.name + '</option>');
		});
		if (currentOccupation) {
			$('#occupation option').each(function(index) {
				if ($(this).val() == currentOccupation)
					$(this).attr('selected', 'selected');
			});
		}
	});
}

var setMaritalStatus = function (maritalStatus) {
	$('#maritalStatus').html('<option value="">-Select Marital Status-</option>' +
		'<option value="Single">Single</option>' +
		'<option value="Engaged">Engaged</option>' +
		'<option value="married">Married</option>' +
		'<option value="Divorced">Divorced</option>' +
		'<option value="Widowed">Widowed</option>');
	if (maritalStatus) {
		$('#maritalStatus option').each(function(index) {
			if ($(this).val() == maritalStatus)
				$(this).attr('selected', 'selected');
		});
	}
}

var setSexOrientation = function (sexOrientation) {
	$('#sexOrientation').html('<option value="">-Select Sex Orientation-</option>' +
		'<option value="Straight">Straight</option>' +
		'<option value="Gay">Gay</option>' +
		'<option value="Lesbian">Lesbian</option>' +
		'<option value="Bisexual">Bisexual</option>');
	if (sexOrientation) {
		$('#sexOrientation option').each(function(index) {
			if ($(this).val() == sexOrientation)
				$(this).attr('selected', 'selected');
		});
	}
}
