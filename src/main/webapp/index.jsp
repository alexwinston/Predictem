<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta name="viewport" content="width=320;" />
<!--link media="screen" rel="stylesheet" href="css/facebox.css" /-->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.atmosphere-min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$.atmosphere.subscribe(document.location.toString() + "rs/game/1234", callback, //null);
			$.atmosphere.request = { transport: "websocket", fallbackTransport: "long-polling" });
	function callback(response) {
		var data = response.responseBody
		
		if (data != "" && response.state != "error" && response.state != "messagePublished") {
			$("ul").prepend($("<li></li>").text(" Message Received: " + data + " using transport: " + response.transport + "/" + response.state));
		}
	}

	$("#create").click(function() {
		$.atmosphere.response.push(document.location.toString() + "rs/game/1234", null,
				$.atmosphere.request = { data: "message='" + $("#game").val() + "'" });
		});
});
</script>
</head>
<body>
	<input id="game" type="text">
	<input id="create" type="button" value="Create">
	<ul></ul>
	<div style="display: none">
		<div id="hello_div" style="padding: 10px; background: #fff;">
			<p><strong id="hello_text"></strong></p>
		</div>
	</div>
</body>
</html>