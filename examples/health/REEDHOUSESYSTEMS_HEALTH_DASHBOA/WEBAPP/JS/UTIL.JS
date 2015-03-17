
/**
 * @return {bool}
 */
var is_logged_in = function () {
  return !!$.cookie('dashboard-token')
}

/**
 * Log the user in
 * @param {string}
 * @param {string}
 * @param {function} Callback. First parameter is error, if any.
 */
var login = function (padding, username, password, role, callback) {
	post(padding, 'user/login', {"username" : username, "password" : password, "role" : role, "status" : userStatus.LOGGEDIN}, function (response, error) {
		if (!error) {
			$.cookie('dashboard-token', response.token, { path: '/' })
			$.cookie('dashboard-userId', response.userId, { path: '/' })
			$.cookie('dashboard-username', username, { path: '/' })
			// If the cached version is the same as the most recent
			// version, just return. Else, we will run the callback.
			if (store.get('dashboard-currentUser') === JSON.stringify(response))
				console.log('cached')
		    else
		    	store.set('dashboard-currentUser', JSON.stringify(response))
			callback()
		} else
			callback(error)
   })
}

var downloadCurrentUser = function (userId) {
	get(
	'user/' + userId,
    {},
    function (response) {
      
    },
    function(jqXHR, textStatus) {
      console.log(jqXHR)
  })
}

var logout = function(padding, loginpage) {
	putAuth(padding, 'user/' + $.cookie('dashboard-userId') + '/logout', JSON.stringify({"status": userStatus.LOGGEDOUT}), function (error) {
		if (error) {
			toastr.error("Error logging out")
			return false;
		}
		$.removeCookie('dashboard-token', { path: '/' })
		$.removeCookie('dashboard-userId', { path: '/' })
		$.removeCookie('dashboard-username', { path: '/' })
		store.clear();
		window.location = loginpage
     })
}

var setChangePasswordForm = function() {
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
				resetPassword($('#currentPassword').val().trim(), $('#newPassword').val().trim(), function(error) {
					if (!error) {
						toastr.success('Password reset successfully');
						closeModal();
					} else {
						toastr.error(error.responseJSON.applicationMessage);
						clearInput();
					}
				})
			}
		});
	});
}

var reset_password = function (userId, password, callback) {
	putAuth(
    'user/' + userId + '/resetpassword',
      {
      "password" : password
      },
    function (response) {
      callback()
    },
    function(jqXHR, textStatus) {
      callback(jqXHR)
    })
}

var resetPassword = function (currentPassword, newPassword, callback) {
	putAuth(
    'user/' + $.cookie('dashboard-userId') + '/resetpassword',
      {
    	"currentPassword" : currentPassword,
    	"password" : newPassword
      },
    function (response) {
      callback()
    },
    function(jqXHR, textStatus) {
      callback(jqXHR)
    })
}	

var rootURL = "http://146.231.123.109:8080/dashboard/";
//	var rootURL = "http://146.231.121.143:8080/dashboard/";
	var urlPadding = "../../";
	var teleWeaverURL = "http://146.231.121.143:8080/healthcomm/";
//	var teleWeaverURL = "http://146.231.123.109:8080/healthcomm/";
		
	var status = {
		Active: "Active",
		Archived: "Archived",
		Finished: "Finished",
		Inactive: "Inactive",
		Running: "Running"
	}
	
	var userStatus = {
		LOGGEDOUT: 0,
		LOGGEDIN: 1,
		LOCKED: 2,
		INACTIVE: 3
	}
	
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
	
	var applyTinyMCE = function(padding) {
		$('textarea.tinymce').tinymce({
			// Location of TinyMCE script
			script_url : padding + 'js/tiny_mce/tiny_mce.js',
			
			// General options
			 theme : "advanced",
			 mode : "textareas"/*,
			 height: 247*/
		});
	}	
	
	var setupTargetPage = function(target, id, title, action) {
		$('#portlet-main-content').html(
			'<form id="frmTarget" class="form" role="form">' +
				'<div class="form-group col-md-12"><label for="sex">Sex</label><select name="sex" id="sex" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="occupation">Occupation</label><select name="occupation" id="occupation" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="dateOfBirthFrom">Date of Birth (From)</label><input type="text" name="dateOfBirthFrom" id="dateOfBirthFrom" class="form-control"></div>' +
				'<div class="form-group col-md-12"><label for="dateOfBirthTo">Date of Birth (To)</label><input type="text" name="dateOfBirthTo" id="dateOfBirthTo" class="form-control"></div>' +
				'<div class="form-group col-md-12"><label for="maritalStatus">Marital Status</label><select name="maritalStatus" id="maritalStatus" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="sexOrientation">Sex Orientation</label><select name="sexOrientation" id="sexOrientation" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="province">Province</label><select name="province" id="province" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="district">District</label><select name="district" id="district" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="municipality">Municipality</label><select name="municipality" id="municipality" class="form-control"></select></div>' +
				'<div class="form-group col-md-12"><label for="village">Village</label><select name="village" id="village" class="form-control"></select></div>' +
				'<div><button type="submit" id="btnSave" class="btn btn-primary">Save</button><button type="button" id="btncancel" class="btn btn-primary" style="float: right;">Skip setting target</button></div>' +
			'</form>'
		);
		$("input#dateOfBirthFrom").datepicker({ dateFormat: "yy-mm-dd" });
		$("input#dateOfBirthTo").datepicker({ dateFormat: "yy-mm-dd" });
		setProvinces();				
		setSex();
		setMaritalStatus();
		setOccupation();
		setSexOrientation();
		if (target) {
			if (target.sex) {
				$('#sex option').each(function(index) {
					if ($(this).val() == target.sex) $(this).attr('selected', 'selected');
				});
			}
			if (target.maritalStatus) {
				$('#maritalStatus option').each(function(index) {
					if ($(this).val() == target.maritalStatus) $(this).attr('selected', 'selected');
				});
			}
			if (target.occupation) {
				$('#occupation option').each(function(index) {
					if ($(this).val() == target.occupation) $(this).attr('selected', 'selected');
				});
			}
			if (target.sexOrientation) {
				$('#sexOrientation option').each(function(index) {
					if ($(this).val() == target.sexOrientation) $(this).attr('selected', 'selected');
				});
			}
			if (target.address) {
				if (target.address.province) {
					$('#province option').each(function(index) {
						if ($(this).val() == target.address.province) $(this).attr('selected', 'selected');
					});
				}
				getDistrictsByProvince(target.address.province);
				if (target.address.district) {
					$('#district option').each(function(index) {
						if ($(this).val() == target.address.district) {
							$(this).attr('selected', 'selected');
						}
					});
				}
				getMunicipalitiesByDistrict(target.address.district);
				if (target.address.municipality) {
					$('#municipality option').each(function(index) {
						if ($(this).val() == target.address.municipality) {
							$(this).attr('selected', 'selected');
						}
					});
				}
				getVillagesByMunicipality(target.address.municipality);
				if (target.address.municipality) {
					$('#village option').each(function(index) {
						if ($(this).val() == target.addres.village) $(this).attr('selected', 'selected');
					});
				}
			}
			if (target.dateOfBirthFrom != null) $('#dateOfBirthFrom').val($.datepicker.formatDate('yy-mm-dd', new Date(target.dateOfBirthFrom)));
			if (target.dateOfBirthTo != null) $('#dateOfBirthTo').val($.datepicker.formatDate('yy-mm-dd', new Date(target.dateOfBirthTo)));
		}
		$('#province').on('change', function() {
			var province = $(this).val().trim();
			getDistrictsByProvince(province);
		});
		$('#district').on('change', function() {
			var district = $(this).val().trim();
			getMunicipalitiesByDistrict(district);
		});
		$('#municipality').on('change', function() {
			var municipality = $(this).val().trim();
			getVillagesByMunicipality(municipality);
		});
		
		$('#btncancel').click(function() {
			window.location = 'index.html';
		});
		
		$('#btnSave').click(function() {
			switch(action) {
			case 'Survey':
				$('#frmTarget').validate({
					rules: {
						province: 'required',
						district: 'required',
						municipality: 'required',
						village: 'required'
					},
					submitHandler: function() {
						console.log('Setting survey target...');
						$.ajax({
							type: 'PUT',
							contentType: 'application/json',
							url: rootURL + 'survey/'+ id + '/target',
							dataType: 'json',
							data: targetFormToJSON(),
							statusCode: {
								200: function() {
									toastr.success('Survey targets set successfully');
								},
								500: function() {
									toastr,error('Error setting survey targets')
								}
							}
						});
					},
					invalidHandler: function(event, validator) {
						displayFormInputError(event, validator);
					}
				});
			break;
			case 'Event':
				$('#frmTarget').validate({
					rules: {
						province: 'required',
						district: 'required',
						municipality: 'required',
						village: 'required'
					},
					submitHandler: function() {
						console.log('Setting targets for an event...');
						$.ajax({
							type: 'PUT',
							contentType: 'application/json',
							url: rootURL + 'event/'+ id + '/target',
							dataType: 'json',
							data: targetFormToJSON(),
							statusCode: {
								200: function() {
									toastr.success('Event target set successfully');
								},
								500: function() {
									toastr.error('Error setting event target');
								}
							}
						});
					},
					invalidHandler: function(event, validator) {
						displayFormInputError(event, validator);
					}
				});
			break;
			case 'News':
				$('#frmTarget').validate({
					rules: {
						province: 'required',
						district: 'required',
						municipality: 'required',
						village: 'required'
					},
					submitHandler: function() {
						console.log('Setting news target...');
						$.ajax({
							type: 'PUT',
							contentType: 'application/json',
							url: rootURL + 'news/'+ id + '/target',
							dataType: 'json',
							data: targetFormToJSON(),
							statusCode: {
								200: function() {
									toastr.success('News target set successfully');
								},
								500: function() {
									toastr.error('Error setting news target');
								}
							}
						});
					},
					invalidHandler: function(event, validator) {
						displayFormInputError(event, validator);
					}
				});
			break;
			}
		});		
	}
	
	var targetFormToJSON = function() {
		return JSON.stringify({
			dateOfBirthFrom: $('#dateOfBirthFrom').val().trim() == "" ? null : $('#dateOfBirthFrom').val().trim(),
			dateOfBirthTo: $('#dateOfBirthTo').val().trim() == "" ? null : $('#dateOfBirthTo').val().trim(),
			sex: $('#sex').val().trim() == "" ? null : $('#sex').val().trim(),
			occupation: $('#occupation').val().trim() == "" ? null : $('#occupation').val().trim(),
			maritalStatus: $('#maritalStatus').val().trim() == "" ? null : $('#maritalStatus').val().trim(),
			sexOrientation: $('#sexOrientation').val().trim() == "" ? null : $('#sexOrientation').val().trim(),
			address: {
				province: $('#province').val().trim() == "" ? null : $('#province').val().trim(),
				district: $('#district').val().trim() == "" ? null : $('#district').val().trim(),
				municipality: $('#municipality').val().trim() == "" ? null : $('#municipality').val().trim(),
				village: $('#village').val().trim() == "" ? null : $('#village').val().trim()
			}
		});
	}
	
	var closeModal = function () {
		$('.modal-title').empty();
		$('.modal-body').empty();
		$('.modal-footer').empty();
		$('#modal_dialog').modal('hide');
	}

	var setHasUnderFiveChildren = function () {
		$('#haveUnderFiveChildren').html('<option value="">-Select if should have under five children-</option><option value="Yes">Yes</option><option value="No">No</option>');
	}

	var setSex = function () {
		$('#sex').append('<option value="">-Select Sex-</option><option value="Male">Male</option><option value="Female">Female</option>');
	}

	var setOccupation = function () {
		$.ajax({
			type: 'GET',
			url: rootURL + 'occupation',
			dataType: 'json',
			success: function(data) {
				var occupationList = data instanceof Array ? data : [data];
				$('#occupation').append('<option value="">-Select Occupation-</option>');
				$.each(occupationList, function(index, occupation) {
					$('#occupation').append('<option value="' + occupation.name + '">' + occupation.name + '</option>');
				});
			},
			error: function() {
				toastr.error('Error getting occupations')
			}
		});
	}
	
	var setServiceTypes = function() {
		$.ajax({
			type: 'GET',
			url: rootURL + 'service/type',
			dataType: 'json',
			success: function(data) {
				var serviceTypeList = data instanceof Array ? data : [data];
				$('#type').html('<option value="">-Select service type-</option>');
				$.each(serviceTypeList, function(index, serviceType) {
					$('#type').append('<option value="' + serviceType.name + '">' + serviceType.name + '</option>')
				});
			},
			error: function() {
				toastr.error('Error getting service types');
			}
		});
	}

	var setMaritalStatus = function () {
		$('#maritalStatus').html('<option value="">-Select Marital Status-</option>' +
				'<option value="Single">Single</option>' +
				'<option value="Engaged">Engaged</option>' +
				'<option value="married">Married</option>' +
				'<option value="Divorced">Divorced</option>' +
				'<option value="Widowed">Widowed</option>');
	}

	var setProvinces = function () {
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
	}
	
	var getDistrictsByProvince = function(province) {
		console.log('Getting district information...');
		$.ajax({
			type: 'GET',
			url: rootURL + 'location/province/' + province,
			dataType: 'json',
			success: function(data) {
				var locationList = data instanceof Array ? data : [data];
				$('#district').html('<option value="">-Select district-</option>')
				$.each(locationList, function(index, location) {
					$('#district').append('<option value="' + location + '">' + location + '</option>')
				});
			},
			error: function() {
				toastr.error('Error getting location information');
			}		
		})
	}
	
	var getMunicipalitiesByDistrict = function(district) {
		console.log('Getting municipality information...');
		$.ajax({
			type: 'GET',
			url: rootURL + 'location/district/' + district,
			dataType: 'json',
			success: function(data) {
				var locationList = data instanceof Array ? data : [data];
				$('#municipality').html('<option value="">-Select municipality-</option>')
				$.each(locationList, function(index, location) {
					$('#municipality').append('<option value="' + location + '">' + location + '</option>')
				});
			},
			error: function() {
				toastr.error('Error getting location information');
			}		
		})
	}
	
	var getVillagesByMunicipality = function(municipality) {
		console.log('Getting village information...');
		$.ajax({
			type: 'GET',
			url: rootURL + 'location/municipality/' + municipality,
			dataType: 'json',
			success: function(data) {
				var locationList = data instanceof Array ? data : [data];
				$('#village').html('<option value="">-Select village-</option>')
				$.each(locationList, function(index, location) {
					$('#village').append('<option value="' + location + '">' + location + '</option>')
				});
			},
			error: function() {
				toastr.error('Error getting location information');
			}		
		})
	}

	var setSexOrientation = function () {
		$('#sexOrientation').html('<option value="">-Select Sex Orientation-</option>' +
				'<option value="Straight">Straight</option>' +
				'<option value="Gay">Gay</option>' +
				'<option value="Lesbian">Lesbian</option>' +
				'<option value="Bisexual">Bisexual</option>');
	}
	
	var setFacilityTypes = function() {
		$('#type').html('<option value="">-Select type-</option>' +
				'<option value="Clinic">Clinic</option>' +
				'<option value="Community health centre">Community health centre</option>' +
				'<option value="District hospital">District hospital</option>' +
				'<option value="Regional hospital">Regional hospital</option>' +
				'<option value="Provincial Tertiary">Provincial Tertiary</option>' +
				'<option value="National referral">National referral</option>' +
				'<option value="Central referral">Central referral</option>' +
				'<option value="Specialised">Specialised</option>');
	}

	var uncheckCheckboxes = function () {
		$('.icheck-input').iCheck('uncheck');
	}
	
	var deactivateMenuButtons = function () {
		$('#btnAdd').attr('disabled','disabled');
		$('#btnDeactivate').attr('disabled','disabled');
		$('#btnActivate').attr('disabled','disabled');
		$('#btnDelete').attr('disabled','disabled');
		$('#btnSend').attr('disabled','disabled');
		$('#btnArchive').attr('disabled','disabled');
		$('#btnPublish').attr('disabled','disabled');
	}

	var activateMenuButtons = function () {
		$('#btnAdd').removeAttr("disabled");
		$('#btnDeactivate').removeAttr("disabled");
		$('#btnActivate').removeAttr("disabled");
		$('#btnDelete').removeAttr("disabled");
		$('#btnSend').removeAttr("disabled");
		$('#btnArchive').removeAttr('disabled');
		$('#btnPublish').removeAttr('disabled');
	}

	var clearInput = function () {
		$(':input').not(':button, :submit, :reset, :hidden').val('');
		$('textarea').val('');
	}

	var displayFormInputError = function (event, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var message = errors == 1 ? '1 field not correctly filled. Please check the error message for guidance' : '' + errors + ' fields not filled in correctly. Please check error messages for guidance';
			toastr.error(message);
		}			
		return false;
	}

	var setupActions = function (id, status) {
		switch (status) {
		case 'Active':
			$('#deactivate-' + id).show();
			$('#message-' + id).show();
			$('#target-' + id).show();
			$('#update-' + id).show();
			$('#delete-' + id).show();
			$('#publish-' + id).show();
			$('changepasswd-'+ id).show();
			$('#archive-' + id).hide();
			$('#activate-' + id).hide();
			break;
		case 'Inactive':
			$('#archive-' + id).show();
			$('#activate-' + id).show();
			$('#update-' + id).show();
			$('#deactivate-' + id).hide();
			$('#delete-' + id).hide();
			$('#message-' + id).hide();
			$('#target-' + id).hide();
			$('#publish-' + id).hide();
			$('changepasswd-'+ id).hide();
			break;
		case 'Running':
			$('#deactivate-' + id).show();
			$('#add-' + id).hide();
			$('#activate-' + id).hide();
			$('#archive-' + id).hide();
			$('#delete-' + id).hide();
			$('#message-' + id).hide();
			$('#target-' + id).hide();
			$('#update-' + id).hide();
			$('#publish-' + id).hide();
			break;
		case 'Archived':
			$('#delete-' + id).show();
			$('#message-' + id).hide();
			$('#target-' + id).hide();
			$('#activate-' + id).hide();
			$('#archive-' + id).hide();
			$('#deactivate-' + id).hide();
			$('#update-' + id).hide();
			$('#send-' + id).hide();
			$('#publish-' + id).hide();
			break;
		case 'Finished':
			$('#archive-' + id).show();
			$('#message-' + id).hide();
			$('#target-' + id).hide();
			$('#activate-' + id).hide();
			$('#deactivate-' + id).hide();
			$('#update-' + id).hide();
			$('#delete-' + id).hide();
			$('#publish-' + id).hide();
			break;
		case 'Sent':
			$('#send-' + id).hide();
			$('#update-' + id).hide();
			$('#delete-' + id).hide();
			break;
		}
	}
	