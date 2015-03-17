if (!is_logged_in()) window.location = '../../home/';
	
var storedUser = store.get('dashboard-currentUser')
if (storedUser) {
	var user = JSON.parse(storedUser)
	$('#loggedAs').html(' Logged in as ' + user.firstName + ' ' + user.lastName);
}			

$(function() {	
	var padding = '../../'
	$("input#dateOfBirthFrom").datepicker({ dateFormat: "yy-mm-dd" });
	$("input#dateOfBirthTo").datepicker({ dateFormat: "yy-mm-dd" });
	setLangauge();
	setProvinces();
	setSex();
	setMaritalStatus();
	setOccupation(padding);
	setSexOrientation();
	
	$('#province').on('change', function() {
		var province = $(this).val().trim();
		getDistrictsByProvince(padding, province);
	});
	$('#district').on('change', function() {
		var district = $(this).val().trim();
		getMunicipalitiesByDistrict(padding, district);
	});
	$('#municipality').on('change', function() {
		var municipality = $(this).val().trim();
		getVillagesByMunicipality(padding, municipality);
	});
	$('#frmTarget').validate({
		rules: {
			province: 'required',
			district: 'required',
			municipality: 'required',
			village: 'required'
		},
		submitHandler: function() {
			putAuth(padding, activity + '/' + $.cookie(activity + '-id') + '/target', targetFormToJSON(), function(error) {
				if (error) {
					toastr.error("Error saving " + activity);
					return false;
				}
				toastr.success(captalise(activity) + " saved successfully")
				clearInput();
				window.location = nextWindow;
			});
		},
		invalidHandler: function(event, validator) {
			displayFormInputError(event, validator);
		}
	});
});