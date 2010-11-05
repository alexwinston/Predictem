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
	
$.fn.modal = function(options) {
    // / <summary>Centers the selected items in the browser window. Takes into
	// account scroll position.
    /// Ideally the selected set should only match a single element.
    /// </summary>    
    /// <param name="fn" type="Function">Optional function called when centering is complete. Passed DOM element as parameter</param>    
    /// <param name="forceAbsolute" type="Boolean">if true forces the element to be removed from the document flow 
    ///  and attached to the body element to ensure proper absolute positioning. 
    /// Be aware that this may cause ID hierachy for CSS styles to be affected.
    /// </param>
    /// <returns type="jQuery" />
    var opt = { forceAbsolute: false,
                container: window,    // selector of element to center in
                completeHandler: null
              };
    $.extend(opt, options);
   
    return this.each(function(i) {
        var el = $(this);
        var jWin = $(opt.container);
        var isWin = opt.container == window;

        // force to the top of document to ENSURE that 
        // document absolute positioning is available
        if (opt.forceAbsolute) {
            if (isWin)
                el.remove().appendTo("body");
            else
                el.remove().appendTo(jWin.get(0));
        }

        // have to make absolute
        el.css("position", "absolute");

        // height is off a bit so fudge it
        var heightFudge = isWin ? 2.0 : 1.8;

        var x = (isWin ? jWin.width() : jWin.outerWidth()) / 2 - el.outerWidth() / 2;
        var y = (isWin ? jWin.height() : jWin.outerHeight()) / heightFudge - el.outerHeight() / 2;

        el.css("left", x + jWin.scrollLeft());
        el.css("top", y + jWin.scrollTop());

        // if specified make callback and pass element
        if (opt.completeHandler)
            opt.completeHandler(this);
    });
}

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