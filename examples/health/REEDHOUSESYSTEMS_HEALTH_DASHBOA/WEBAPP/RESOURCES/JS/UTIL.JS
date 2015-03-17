var teleWeaverURL = "http://localhost:8080/health-messenger/";
	
var status = {
	Active: "Active",
	Archived: "Archived",
	Finished: "Finished",
	Inactive: "Inactive",
	Running: "Running",
	Published: "Published"
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
		  "positionClass": "toast-top-right",
		  "progressBar": true,
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
		script_url : padding + 'resources/js/tiny_mce/tiny_mce.js',
		
		// General options
		 theme : "advanced",
		 mode : "textareas"/*,
		 height: 247*/
	});
}

var closeModal = function () {
	$('.modal-title').empty();
	$('.modal-body').empty();
	$('.modal-footer').empty();
	$('#modal_dialog').modal('hide');
}

var uncheckCheckboxes = function () {
	$('.icheck-input').iCheck('uncheck');
	$('.icheck-all').iCheck('uncheck');
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

var captalise = function(string) {
	return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
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
		$('#update-' + id).show();
		$('#delete-' + id).show();
		$('#target-' + id).show();
		$('#publish-' + id).show();
		$('#message-' + id).show();
		$('#activate-' + id).hide();
		$('#deactivate-' + id).hide();
		$('#archive-' + id).hide();
		$('#changepasswd-'+ id).show();
		$('#report-'+ id).hide();
		break;
	case 'Inactive':
		$('#update-' + id).show();
		$('#activate-' + id).show();
		$('#delete-' + id).show();
		$('#message-' + id).show();
		$('#target-' + id).show();
		$('#deactivate-' + id).hide();
		$('#publish-' + id).hide();
		$('#archive-' + id).hide();
		$('#changepasswd-'+ id).hide();
		$('#report-'+ id).hide();
		break;
	case 'Running':
		$('#deactivate-' + id).show();
		$('#report-'+ id).show();
		$('#add-' + id).hide();
		$('#activate-' + id).hide();
		$('#archive-' + id).hide();
		$('#delete-' + id).hide();
		$('#message-' + id).hide();
		$('#target-' + id).hide();
		$('#update-' + id).hide();
		$('#publish-' + id).hide();
		$('#report-'+ id).show();
		break;
	case 'Archived':
		$('#delete-' + id).show();
		$('#report-'+ id).show();
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
		$('#report-'+ id).show();
		$('#message-' + id).hide();
		$('#target-' + id).hide();
		$('#activate-' + id).hide();
		$('#deactivate-' + id).hide();
		$('#update-' + id).hide();
		$('#delete-' + id).hide();
		$('#publish-' + id).hide();
		break;
	case 'Published':
		$('#update-' + id).show();
		$('#delete-' + id).show();
		$('#target-' + id).show();
		$('#publish-' + id).show();
		$('#message-' + id).show();
		$('#activate-' + id).hide();
		$('#deactivate-' + id).hide();
		$('#archive-' + id).hide();
		$('#report-'+ id).hide();
		break;
	}
}

var targetFormToJSON = function() {
	return JSON.stringify({
		dateOfBirthFrom: $('#dateOfBirthFrom').val().trim() == "" ? null : $('#dateOfBirthFrom').val().trim(),
		dateOfBirthTo: $('#dateOfBirthTo').val().trim() == "" ? null : $('#dateOfBirthTo').val().trim(),
		sex: $('#sex').val().trim() == "" ? null : $('#sex').val().trim(),
		language: $('#language').val().trim(),
		occupation: $('#occupation').val().trim() == "" ? null : $('#occupation').val().trim(),
		maritalStatus: $('#maritalStatus').val().trim() == "" ? null : $('#maritalStatus').val().trim(),
		sexOrientation: $('#sexOrientation').val().trim() == "" ? null : $('#sexOrientation').val().trim(),
		locationDTO: {
			province: $('#province').val().trim() == "" ? null : $('#province').val().trim(),
			district: $('#district').val().trim() == "" ? null : $('#district').val().trim(),
			municipality: $('#municipality').val().trim() == "" ? null : $('#municipality').val().trim(),
			village: $('#village').val().trim() == "" ? null : $('#village').val().trim()
		}
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
			$('#district').append('<option value="' + district + '">' + district + '</option>');
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
			$("#municipality").append('<option value="' + municipality + '">' + municipality + '</option>');
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
			$('#village').append('<option value="' + village + '">' + village + '</option>');
		});
		if (currentVillage) {
			$('#village option').each(function(index) {
				if ($(this).val() == currentVillage)
					$(this).attr('selected', 'selected');
			});
		}
	});
}

var setOrganisations = function(padding, currentOrganisation) {
	get(padding, 'organisation', function(data, error) {
		if (error) {
			toastr.error("Error getting organisations");
			return false;
		}
		$('#organisation').html('<option value="">-Select organisation-</option>');
		var organisationList = data instanceof Array ? data : [data];
		$.each(organisationList, function(index, organisation) {
			$('#organisation').append('<option value="' + organisation.id + '">' + organisation.name + '</option>');
		});
		if (currentOrganisation) {
			$('#organisation option').each(function(index) {
				if ($(this).val() == currentOrganisation)
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

var setServiceTypes = function(currentServiceType) {
	getAuth("../../", 'service/type', function(data, error) {
		if (error) {
			toastr.error('Error getting service types');
			return false;
		}
		var serviceTypeList = data instanceof Array ? data : [data];
		$('#type').html('<option value="">-Select service type-</option>');
		$.each(serviceTypeList, function(index, serviceType) {
			$('#type').append('<option value="' + serviceType.name + '">' + serviceType.name + '</option>')
		});
		if (currentServiceType) {
			$('#type option').each(function(index) {
				if ($(this).val() == currentServiceType) {
					$(this).attr('selected', 'selected');
				}
			});
		}
	});
}

var setFacilityTypes = function(facilityType) {
	$('#type').html('<option value="">-Select type-</option>' +
		'<option value="Clinic">Clinic</option>' +
		'<option value="Community health centre">Community health centre</option>' +
		'<option value="District hospital">District hospital</option>' +
		'<option value="Regional hospital">Regional hospital</option>' +
		'<option value="Provincial Tertiary">Provincial Tertiary</option>' +
		'<option value="National referral">National referral</option>' +
		'<option value="Central referral">Central referral</option>' +
		'<option value="Specialised">Specialised</option>');
	if (facilityType) {
		$('#type option').each(function(index) {
			if ($(this).val() == facilityType)
				$(this).attr('selected', 'selected');
		});
	}
}

var topicDatatableSettings = function() {
	var oTable = $('#tblTopics').dataTable({
		"bDestroy": true,
		'bRetrieve': true,
		'aoColumnDefs': [
		                 {'bVisible': false, 'aTargets': [4]},
		                 {'bSortable': false, 'aTargets': [0, 5]}
		 ],
		 'aaSorting': [[1, 'desc']],
	     'iDisplayLength': 10
	});
	
	$('.icheck-input').iCheck({
		checkboxClass: 'icheckbox_minimal-blue'
	});
	
	$('.icheck-all').iCheck({
		checkboxClass: 'icheckbox_minimal-blue'
	});
    
	$('.dataTables_filter input').prop ('placeholder', 'Search...');
    
    $('tfoot input').keyup(function() {
    	oTable.fnFilter(this.value, $('tfoot input').index(this));
    });
}

var publishTopics = function(padding, topicId) {
	console.log('Getting topic with id ' + topicId);
	getAuth(padding, 'topics/topics/'+ topicId, function(data, error) {
		if (error) {
			toastr.error("Error getting a topic to publish");
			return false;
		}
		post('', teleWeaverURL + 'topics', topicDataToJSON(data), function(error) {
			if (error) {
				toastr.error("Error publishing a topic");
				return false;
			}
			toastr.success("Topic published successfully");
		});
	});
}

var topicDataToJSON = function(topic) {
	var arrTopics1 = Array();
	var arrTopics2 = {};
	for (var i=0; i<topic.topicDetailsDTOList.length; i++) {
		arrTopics2["id"] = topic.topicDetailsDTOList[i].id;
		arrTopics2["sectionName"] = topic.topicDetailsDTOList[i].sectionName;
		arrTopics2["sectionDescription"] = topic.topicDetailsDTOList[i].sectionDescription;
		arrTopics1[i] = JSON.stringify(arrTopics2);
	}
	var arrTopics = cleanArrTopics(arrTopics1);
	return JSON.stringify({
		id: topic.id,
		title: topic.title,
		description: topic.description,
		language: topic.language,
		status: topic.status,
		topicDetailsDTOList: arrTopics
	});
}

var cleanArrTopics = function(arrTopics) {
	var arrLength = arrTopics.length;
	var finalArrTopics = Array();
	for (var i=0; i<arrLength; i++) {
		finalArrTopics[i] = JSON.parse(arrTopics[i]); 
	}
	return finalArrTopics;
}

var updateTopic = function(padding, topicId, status) {
	putAuth(padding, 'topics/topics/'+ topicId + '/status', JSON.stringify({status: status}), function(error) {
		if (error) {
			toastr.error("Error updating a topic");
			return false;
		}
		switch (status) {
		case Archived:
			$('#status-'+topicId+' span').attr('class', 'label label-default').html(strStatus);
			break;
		case Published:
			$('#status-'+topicId+' span').attr('class', 'label label-success').html(strStatus);
			break;
		}
		$('#tr-'+topicId).removeClass('active').addClass('warning');
		setupActions(topicId, status);
	});
}
	