if (!is_logged_in()) window.location = '../home/';	
var storedUser = store.get('dashboard-currentUser')
if (storedUser) {
	var user = JSON.parse(storedUser)
	$('#loggedAs').html(' Logged in as ' + user.firstName + ' ' + user.lastName);
}

$(function() {
	var padding = '../../'
	$("input#dateOfBirthFrom").datepicker({ dateFormat: "dd-mm-yy" });
	$("input#dateOfBirthTo").datepicker({ dateFormat: "dd-mm-yy" });
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
			if (target.location) {
				if (target.location.province) {
					setProvinces(target.location.province);
					getDistrictsByProvince(padding, target.location.province, target.location.district);
				}							
				if (target.location.district)
					getMunicipalitiesByDistrict(padding, target.location.district, target.location.municipality);							
				if (target.location.municipality)
					getVillagesByMunicipality(padding, target.location.municipality, target.location.village);
			}
			if (target.dateOfBirthFrom)
				$('#dateOfBirthFrom').val($.datepicker.formatDate('dd-mm-yy', new Date(target.dateOfBirthFrom)));
			if (target.dateOfBirthTo)
				$('#dateOfBirthTo').val($.datepicker.formatDate('dd-mm-yy', new Date(target.dateOfBirthTo)));
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
				toastr.success(captalise(activity) + ' target updated successfully');
			})
		},
		invalidHandler: function(event, validator) {
			displayFormInputError(event, validator);
		}
	});
});