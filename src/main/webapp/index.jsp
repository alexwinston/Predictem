<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta name="viewport" content="width=320;" />
	<!--link media="screen" rel="stylesheet" href="css/facebox.css" /-->
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="js/jquery.atmosphere-0.7.min.js"></script>
	<script type="text/javascript" src="js/jquery.predictem-1.0.js"></script>
	<script type="text/javascript" src="js/json2.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			// Subscribe to the atmosphere game broadcaster
			$.predictem.subscribe("rs/game/1234", {
				received: function(data) {
					$("ul").prepend($("<li></li>")
							.text(" Message Received: " + data.question));
				}});
		
			// Click handler to submit the question
			$("#questionSubmit").click(function() {
				$.predictem.publish("rs/game/1234", { question: $("#question").val() });
			});
			
			// Click handler to add and remove answers
			$("#answerAdd").click(function() {
				// Clone the answer template and set the attributes and click handler
				$("#answerTemplate")
					.tmpl({
						answer: $("#answerInput").val() })
					.prependTo("#answers")
					.find("#answerRemove").click(function() {
						$(this).parent().remove(); });
				
				// Clear the answer input text
				$("#answerInput").val("");
			});
		});
	</script>
	<script id="answerTemplate" type="text/x-jquery-tmpl">
		<div>
			<span id="answer">{{= answer}}</span>
			<input id="answerRemove" type="button" value="-">
		</div>
	</script>
</head>
<body>
	<div>
		<input id="question" type="text">?
		<input id="questionSubmit" type="button" value="Submit">
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