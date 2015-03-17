	
var post = function(padding, url, data, callback) {	
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: padding + url,
		data: data,
		dataType: 'json',
		success: function(response) {
			if (response)
			    callback(response);
			  else
			    callback();
		},
		error: function(jqXHR, txtStatus) {
			callback(txtStatus, jqXHR);
			console.log(txtStatus)
		}
	});
}

/**
 * Post with authentication
 */
var postAuth = function (padding, url, data, callback) {
  var time = get_iso_date()
  var nonce = makeRandomString()
  var string_to_hash = $.cookie('token') + ':' + url + ',POST,' + time + "," + nonce
  console.log(string_to_hash);
  var authorization = $.cookie('userId') + ':' + hash(string_to_hash)
  
  $.ajax({
    url: padding + url,
    type: "POST",
    contentType: "application/json",
    data: data,
    headers: {
      'Authorization' : authorization,
      'x-healthcomm-date' : time ,
      'nonce' : nonce
    },
    dataType: "json",
	success: function(response) {
	  if (response)
	    callback(response)
	  else
	    callback();
	},
	error: function(jqXHR, txtStatus) {
	  callback(txtStatus, jqXHR);
	  console.log(txtStatus)
	}
  });
}

var put = function(padding, url, data, callback) { 	
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: padding + url,
		data: data,
		dataType: 'json',
		success: function(response) {
			if (response)
			    callback(response)
			  else
			    callback();
		},
		error: function(jqXHR, txtStatus) {
			callback(txtStatus, jqXHR);
			console.log(txtStatus)
		}
	});	
}

var putAuth = function(padding, url, data, callback) {
	var time = get_iso_date();
	var nonce = makeRandomString();
	var string_to_hash = $.cookie('token') + ':' + url + ',PUT,' + time + "," + nonce;
	var authorization = $.cookie('userId') + ':' + hash(string_to_hash);	
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: padding + url,
		data: data,
	    headers: {
	        'Authorization' : authorization,
	        'x-healthcomm-date' : time,
	        'nonce' : nonce
	      },
		dataType: 'json',
		success: function(response) {
			if (response)
			    callback(response)
			  else
			    callback();
		},
		error: function(jqXHR, txtStatus) {
			callback(txtStatus, jqXHR);
			console.log(txtStatus)
		}
	});	
}

var del = function(url, callback) {	
	$.ajax({
		type: 'DELETE',
		url: url,
		success: function(response) {
			callback(response);
		},
		error: function(jqXHR) {
			callback(jqXHR);
			console.log(jqXHR);
			console.log(txtStatus)
		}
	});	
}

var delAuth = function(padding, url, callback) {	
	var time = get_iso_date();
	var nonce = makeRandomString();
	var string_to_hash = $.cookie('token') + ':' + url + ',DELETE,' + time + "," + nonce
	var authorization = $.cookie('userId') + ':' + hash(string_to_hash);
	console.log(string_to_hash);	
	$.ajax({
		type: 'DELETE',
		url: padding + url,
	    headers: {
	        'Authorization' : authorization,
	        'x-healthcomm-date' : time,
	        'nonce' : nonce
	    },
		success: function() {
			callback();
		},
		error: function(jqXHR, txtStatus) {
			callback(jqXHR);
			console.log(txtStatus)
		}
	});	
}

var get = function(padding, url, callback) {
	var request = $.ajax({
		type: 'GET',
		url: padding + url,
		dataType: 'json'
	});	
	request.done(function(response) {
		callback(response);
	});	
	request.fail(function(jqXHR, txtStatus) {
		callback(txtStatus, jqXHR);
	});	
}

var getAuth = function(padding, url, callback) {	
	var time = get_iso_date()
	var nonce = makeRandomString()
	var string_to_hash = $.cookie('token') + ':' + url + ',GET,' + time + "," + nonce
	console.log(string_to_hash);
	var authorization = $.cookie('userId') + ':' + hash(string_to_hash);
	var request = $.ajax({
		type: 'GET',
		url: padding + url,
	    headers: {
	        'Authorization' : authorization,
	        'x-healthcomm-date' : time,
	        'nonce' : nonce
	      },
		dataType: 'json'
	});	
	request.done(function(response) {
		callback(response);
	});	
	request.fail(function(jqXHR, txtStatus) {
		callback(txtStatus, jqXHR);
	});	
}

function makeRandomString() {
    return Math.random().toString(36).substring(2, 15) +
        Math.random().toString(36).substring(2, 15);
}

/**
 * Return the current time as an ISO 8061 Date
 * @return {string} 2012-06-30T12:00:00+01:00
 */
var get_iso_date = function () {
  var d = new Date()
  function pad(n) {return n<10 ? '0'+n : n}
  return d.getUTCFullYear()+'-'
    + pad(d.getUTCMonth()+1)+'-'
    + pad(d.getUTCDate())+'T'
    + pad(d.getUTCHours())+':'
    + pad(d.getUTCMinutes())+':'
    + pad(d.getUTCSeconds())+'Z'
}

/**
 * Get a query string var
 * @param {string}
 * @return {string}
 */
var get_query = function (name) {
  var query = window.location.search.substring(1)
  var vars = query.split('&')
  for (var i = 0; i < vars.length; i++) {
      var pair = vars[i].split('=')
      if (decodeURIComponent(pair[0]) == name) {
          return decodeURIComponent(pair[1])
      }
  }
}

/**
 * SHA256, then base64 encode a string
 * @param {string}
 * @return {string}
 */
var hash = function (string) {
  var hash = CryptoJS.SHA256(string)
  return hash.toString(CryptoJS.enc.Base64)
}
