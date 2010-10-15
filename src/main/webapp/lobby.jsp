<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta name="viewport" content="width=320;" />
<!--link media="screen" rel="stylesheet" href="css/facebox.css" /-->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.atmosphere.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	// Subscribe to the atmosphere lobby broadcaster
	$.atmosphere.subscribe("rs/games/lobby", callback, //null);
			$.atmosphere.request = { transport: "long-polling", fallbackTransport: "websocket" });
	function callback(response) {
		if (response.responseBody != "" && response.state != "error" && response.state != "messagePublished") {
			var game = JSON.parse(response.responseBody);
			
			// Clone the game template and set the attributes and click handler
			var gameTemplate = $("#gameTemplate").clone();
			gameTemplate.find("#gameDescription").text(game.description);
			gameTemplate.find("#gameJoin").click(function() { });
			gameTemplate.show();
			
			// Prepend the answer to the list
			$("#games").prepend(gameTemplate);
		}
	}
	
	// Click handler to add games
	$("#gameCreate").click(function() {
		var game = { description: $("#game").val() };

		$.atmosphere.response.push("rs/games/lobby", null,
				$.atmosphere.request = { data: "game=" + JSON.stringify(game) });
	});
});
</script>
</head>
<body>
	<div>
		<input id="game" type="text">
		<input id="gameCreate" type="button" value="Create">
	</div>
	<div id="gameTemplate" style="display:none;">
		<span id="gameDescription"></span>
		<input id="gameJoin" type="button" value="Join">
	</div>
	<div id="games">
	</div>
</body>
</html>