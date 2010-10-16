jQuery.predictem = function() {
	return {
		subscribe : function(url, subscriber) {
			var callback = function(response) {
				if (response.responseBody != "" && response.state != "error" && response.state != "messagePublished") {
					subscriber.onReceived(JSON.parse(response.responseBody));
				}
			};
				
			jQuery.atmosphere.subscribe(url, callback, //null);
				jQuery.atmosphere.request = { transport: "long-polling", fallbackTransport: "streaming" });
		},
		publish : function(url, json) {
			jQuery.atmosphere.response.push(url, null,
					jQuery.atmosphere.request = { data: JSON.stringify(json) });
		}
	}
}();