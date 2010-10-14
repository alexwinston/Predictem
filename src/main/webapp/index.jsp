<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta name="viewport" content="width=320;" />
<!--link media="screen" rel="stylesheet" href="css/facebox.css" /-->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.atmosphere.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	// Subscribe to the atmosphere broadcaster
	$.atmosphere.subscribe(document.location.toString() + "rs/game/1234", callback, //null);
			$.atmosphere.request = { transport: "websocket", fallbackTransport: "long-polling" });
	function callback(response) {
		var data = response.responseBody
		
		if (data != "" && response.state != "error" && response.state != "messagePublished") {
			$("ul").prepend($("<li></li>").text(" Message Received: " + data + " using transport: " + response.transport + "/" + response.state));
		}
	}

	// Click handler to submit the question
	$("#questionSubmit").click(function() {
		/* Example data
		var data = {
				name: $("#name").val(),
				status: $("#status").val(),
				pages: parseInt($("#pages").val()) };
		JSON.stringify(data)
		*/
		
		$.atmosphere.response.push(document.location.toString() + "rs/game/1234", null,
				$.atmosphere.request = { data: "message='" + $("#question").val() + "'" });

		alert("Question submitted");
	});
	// Click handler to add and remove answers
	$("#answerAdd").click(function() {
		// Clone the answer template and set the attributes and click handler
		var answer = $("#answerTemplate").clone();
		answer.find("#answer").text($("#answerInput").val());
		answer.find("#answerRemove").click(function() {
			$(this).parent().remove(); });
		answer.show();
		
		// Prepend the answer to the list
		$("#answers").prepend(answer);
		
		// Clear the answer input text
		$("#answerInput").val("");
	});
});
</script>
</head>
<body>
	<div>
		<input id="question" type="text">?
		<input id="questionSubmit" type="button" value="Submit">
	</div>
	<div id="answerTemplate" style="display:none;">
		<span id="answer"></span>
		<input id="answerRemove" type="button" value="-">
	</div>
	<div>
		<input id="answerInput" type="text">
		<input id="answerAdd" type="button" value="+">
	</div>
	<div id="answers">
	</div>
	<ul></ul>
</body>
</html>