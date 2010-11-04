function put(params) {
	$.ajax({
		type : "PUT",
		url : params.url,
		contentType : "application/json",
		data : JSON.stringify(params.data),
		processData : false,
		success : params.success,
		error : params.error
	});
}

jQuery.predictem = function() {
	return {
		subscribe: function(url, callbacks) {
			var callback = function(response) {
				if (response.responseBody != "" && response.state != "error") {
					if (response.state == "messageReceived")
						callbacks.received(JSON.parse(response.responseBody));
					if (response.state == "messagePublished")
						callbacks.published(JSON.parse(response.responseBody));
				}
			};
			
			jQuery.atmosphere.contentType = "text/plain";
			jQuery.atmosphere.subscribe(url, callback, //null);
					jQuery.atmosphere.request = { transport: "long-polling", fallbackTransport: "streaming" });
		},
		publish: function(url, json) {
			jQuery.atmosphere.contentType = "application/json";
			jQuery.atmosphere.response.push(url, null,
					jQuery.atmosphere.request = { data: JSON.stringify(json) });
		},
		login: function(email, password, callbacks) {
			put({
				url: "rs/account/login/",
				data: {
   	   				"email": email,
   	   				"password": password
   	   			},
				success: function(response) { callbacks.success(response); },
		   		error: function(response) { callbacks.error(response); }
			});
		},
		register: function(username, email, password, callbacks) {
			put({
   	   			url: "rs/account/register",
   	   			data: {
   	   				"username": username,
   	   				"email": email,
   	   				"password": password
   	   			},
   	   			success: function(response) { callbacks.success(response); },
		   		error: function(response) { callbacks.error(response); }
		   	});
		}
	}
}();

jQuery.cookie = function(name) {
	var returnValue = {};
	if (document.cookie) {
		var cookies = document.cookie.split(';');
		for ( var i = 0; i < cookies.length; i++) {
			var cookie = jQuery.trim(cookies[i]);
			// Does this cookie string begin with the name we want?
			if (!name) {
				var nameLength = cookie.indexOf('=');
				returnValue[cookie.substr(0, nameLength)] = decodeURIComponent(cookie
						.substr(nameLength + 1));
			} else if (cookie.substr(0, name.length + 1) == (name + '=')) {
				returnValue = decodeURIComponent(cookie.substr(name.length + 1));
				break;
			}
		}
	}
	return returnValue;
};