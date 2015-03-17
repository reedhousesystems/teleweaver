if (!is_logged_in()) window.location = '../home/';	
var storedUser = store.get('dashboard-currentUser')
if (storedUser) {
	var user = JSON.parse(storedUser)
	$('#loggedAs').html(' Logged in as ' + user.firstName + ' ' + user.lastName);
}

$(function() {
	var padding = '../../'
	$("input#dateOfBirthFrom").datepicker({ dateFormat: "yy-mm-dd" });
	$("input#dateOfBirthTo").datepicker({ dateFormat: "yy-mm-dd" });
	getAuth(padding, activity + '/' + $.cookie(activity + '-id') + '/target', function(target, error) {
		if (error) {
			toastr.error("error getting " + activity + "targets")
			return false;
		}
		if (target) {
			setLangauge(target.language);		
			setSex(target.sex);
			setMaritalStatus(target.maritalStatus);
			setOccupation(padding, target.occupation);
			setSexOrientation(target.sexOrientation);
			if (target.address) {
				if (target.address.province) {
					setProvinces(target.address.province);
					getDistrictsByProvince(padding, target.address.province, target.address.district);
				}							
				if (target.address.district)
					getMunicipalitiesByDistrict(padding, target.address.district, target.address.municipality);							
				if (target.address.municipality)
					getVillagesByMunicipality(padding, target.address.municipality, target.address.village);
			}
			if (target.dateOfBirthFrom)
				$('#dateOfBirthFrom').val($.datepicker.formatDate('yy-mm-dd', new Date(target.dateOfBirthFrom)));
			if (target.dateOfBirthTo)
				$('#dateOfBirthTo').val($.datepicker.formatDate('yy-mm-dd', new Date(target.dateOfBirthTo)));
		} else {
			setLangauge();
			setSex();
			setMaritalStatus();
			setOccupation(padding);
			setSexOrientation();
			setProvinces();
		}
	})
	$('#province').on('change', function() {
		getDistrictsByProvince(padding, $(this).val().trim());
	});
	$('#district').on('change', function() {
		getMunicipalitiesByDistrict(padding, $(this).val().trim());
	});
	$('#municipality').on('change', function() {
		getVillagesByMunicipality(padding, $(this).val().trim());
	});
	
	$('#frmTarget').validate({
		rules: {
			province: 'required',
			district: 'required',
			municipality: 'required',
			village: 'required'
		},
		submitHandler: function() {
			console.log('Setting '+ activity + ' target...');
			putAuth(padding, activity + '/' + $.cookie(activity + '-id') + '/target', targetFormToJSON(), function(error) {
				if (error) {
					toastr.error("Error setting " + activity + " target");
					return false;
				}
				toastr.success(captalise(activity) + ' target set successfully');
			})
		},
		invalidHandler: function(event, validator) {
			displayFormInputError(event, validator);
		}
	});
	
	var captalise = function(string) {
		return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
	}
});