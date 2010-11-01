<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta name="viewport" content="width=320;" />
<!--link media="screen" rel="stylesheet" href="css/facebox.css" /-->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.atmosphere.js"></script>
<script type="text/javascript" src="js/jquery.predictem.js"></script>
<script type="text/javascript" src="js/json2-min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// Subscribe to the atmosphere lobby broadcaster
		$.predictem.subscribe("rs/games/lobby", {
			received: function(game) {
				// Clone the game template and set the attributes and click handler
				var gameTemplate = $("#gameTemplate").clone();
				gameTemplate.find("#gameDescription").text(game.description);
				gameTemplate.find("#gameJoin").click(function() { });
				gameTemplate.show();
				
				// Prepend the answer to the list
				$("#games").prepend(gameTemplate); }});
		
		// Click handler to add games
		$("#gameCreate").click(function() {
			$.predictem.publish("rs/games/lobby",
					{ description: $("#game").val() });
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