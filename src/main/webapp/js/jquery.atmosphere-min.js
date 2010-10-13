jQuery.atmosphere=function(){var a;$(window).unload(function(){if(a){a.abort()}});return{version:0.7,response:{status:200,responseBody:"",headers:[],state:"messageReceived",transport:"polling",push:[],error:null,id:0},request:{},abordingConnection:false,logLevel:"info",callbacks:[],activeTransport:null,websocket:null,killHiddenIFrame:null,subscribe:function(b,d,c){jQuery.atmosphere.request=jQuery.extend({timeout:300000,method:"GET",headers:{},contentType:"text/html;charset=ISO-8859-1",cache:true,async:true,ifModified:false,callback:null,dataType:"",url:b,data:"",suspend:true,maxRequest:60,lastIndex:0,logLevel:"info",requestCount:0,fallbackTransport:"streaming",transport:"long-polling"},c);logLevel=jQuery.atmosphere.request.logLevel||"info";if(d!=null){jQuery.atmosphere.addCallback(d);jQuery.atmosphere.request.callback=d}if(jQuery.atmosphere.request.transport!=jQuery.atmosphere.activeTransport){jQuery.atmosphere.closeSuspendedConnection()}jQuery.atmosphere.activeTransport=jQuery.atmosphere.request.transport;if(jQuery.atmosphere.request.transport!="websocket"){jQuery.atmosphere.executeRequest()}else{if(jQuery.atmosphere.request.transport=="websocket"){if(!window.WebSocket){jQuery.atmosphere.log(logLevel,["Websocket is not supported, using request.fallbackTransport"]);jQuery.atmosphere.request.transport=jQuery.atmosphere.request.fallbackTransport;jQuery.atmosphere.executeRequest()}else{jQuery.atmosphere.executeWebSocket()}}}},closeSuspendedConnection:function(){abordingConnection=true;if(a!=null){a.abort()}if(jQuery.atmosphere.websocket!=null){jQuery.atmosphere.websocket.close();jQuery.atmosphere.websocket=null}abordingConnection=false},executeRequest:function(){if(jQuery.atmosphere.request.transport=="streaming"){if($.browser.msie){jQuery.atmosphere.ieStreaming();return}else{if((typeof window.addEventStream)=="function"){jQuery.atmosphere.operaStreaming();return}}}if(jQuery.atmosphere.request.requestCount++<jQuery.atmosphere.request.maxRequest){jQuery.atmosphere.response.push=function(e){jQuery.atmosphere.request.callback=null;jQuery.atmosphere.publish(e,null,jQuery.atmosphere.request)};var j=jQuery.atmosphere.request;var c=jQuery.atmosphere.response;if(j.transport!="polling"){c.transport=j.transport}var h;var f=false;if($.browser.msie){var d=["Msxml2.XMLHTTP","Microsoft.XMLHTTP"];for(var g=0;g<d.length;g++){try{h=new ActiveXObject(d[g])}catch(k){}}}else{if(window.XMLHttpRequest){h=new XMLHttpRequest()}}if(j.suspend){a=h}h.open(j.method,j.url,true);h.setRequestHeader("X-Atmosphere-Framework",jQuery.atmosphere.version);h.setRequestHeader("X-Atmosphere-Transport",j.transport);h.setRequestHeader("X-Cache-Date",new Date().getTime());for(var b in j.headers){h.setRequestHeader(b,j.headers[b])}if(!$.browser.msie){h.onerror=function(){f=true;try{c.status=XMLHttpRequest.status}catch(i){c.status=404}c.state="error";jQuery.atmosphere.invokeCallback(c);h.abort();a=null}}h.onreadystatechange=function(){if(abordingConnection){return}var m=false;var r=false;if(h.readyState==4){jQuery.atmosphere.request=j;if(j.suspend&&h.status==200){jQuery.atmosphere.executeRequest()}if($.browser.msie){r=true}}else{if(!$.browser.msie&&h.readyState==3&&h.status==200){r=true}else{clearTimeout(j.id)}}if(r){if(j.transport=="streaming"){c.responseBody=h.responseText.substring(j.lastIndex,h.responseText.length);if(j.lastIndex==0&&c.responseBody.indexOf("<!-- Welcome to the Atmosphere Framework.")!=-1){var o="<!-- EOD -->";var i="<!-- EOD -->".length;var n=c.responseBody.indexOf(o)+i;if(n!=h.responseText.length){c.responseBody=c.responseBody.substring(n)}else{m=true}}j.lastIndex=h.responseText.length;if(m){return}}else{c.responseBody=h.responseText}if(c.responseBody.indexOf("parent.callback")!=-1){var q=c.responseBody.indexOf("('")+2;var l=c.responseBody.indexOf("')");c.responseBody=c.responseBody.substring(q,l)}try{c.status=h.status;c.headers=h.getAllResponseHeaders()}catch(p){c.status=404}if(j.suspend){c.state="messageReceived"}else{c.state="messagePublished"}jQuery.atmosphere.invokeCallback(c)}};h.send(j.data);if(j.suspend){j.id=setTimeout(function(){h.abort();jQuery.atmosphere.subscribe(j.url,null,j)},j.timeout)}}else{jQuery.atmosphere.log(logLevel,["Max re-connection reached."])}},operaStreaming:function(){var c=jQuery.atmosphere.request.url;var e=document.createElement("event-source");var b=jQuery.atmosphere.response;jQuery.atmosphere.response.push=function(f){jQuery.atmosphere.request.transport="polling";jQuery.atmosphere.request.callback=null;jQuery.atmosphere.publish(f,null,jQuery.atmosphere.request)};e.setAttribute("src",c);if(opera.version()<9.5){document.body.appendChild(e)}var d=function(h){if(h.data){var g=false;b.responseBody=h.data;if(h.data.indexOf("<!--")!=-1){g=true}if(b.responseBody.indexOf("parent.callback")!=-1){var i=b.responseBody.indexOf("('")+2;var f=b.responseBody.indexOf("')");b.responseBody=b.responseBody.substring(i,f)}if(g){return}b.state="messageReceived";jQuery.atmosphere.invokeCallback(b)}};e.addEventListener("payload",d,false)},ieStreaming:function(){var b=jQuery.atmosphere.request.url;jQuery.atmosphere.response.push=function(d){jQuery.atmosphere.request.transport="polling";jQuery.atmosphere.request.callback=null;jQuery.atmosphere.publish(d,null,jQuery.atmosphere.request)};transferDoc=new ActiveXObject("htmlfile");transferDoc.open();transferDoc.close();var c=transferDoc.createElement("div");transferDoc.body.appendChild(c);c.innerHTML="<iframe src='"+b+"'></iframe>";transferDoc.parentWindow.callback=jQuery.atmosphere.streamingCallback},streamingCallback:function(c){var b=jQuery.atmosphere.response;b.transport="streaming";b.status=200;b.responseBody=c;b.state="messageReceived";jQuery.atmosphere.invokeCallback(b)},executeWebSocket:function(){var e=jQuery.atmosphere.request;var f=false;jQuery.atmosphere.log(logLevel,["Invoking executeWebSocket"]);jQuery.atmosphere.response.transport="websocket";var c=jQuery.atmosphere.request.url;var g=jQuery.atmosphere.request.callback;var b=c.replace("http:","ws:").replace("https:","wss:");var d=new WebSocket(b);jQuery.atmosphere.websocket=d;jQuery.atmosphere.response.push=function(i){var j;var h=jQuery.atmosphere.websocket;try{j=jQuery.atmosphere.request.data;h.send(jQuery.atmosphere.request.data)}catch(k){jQuery.atmosphere.log(logLevel,["Websocket failed. Downgrading to Comet and resending "+j]);e.transport=e.fallbackTransport;jQuery.atmosphere.request=e;jQuery.atmosphere.executeRequest();h.onclose=function(l){};h.close()}};d.onopen=function(h){f=true;jQuery.atmosphere.response.state="openning";jQuery.atmosphere.invokeCallback(jQuery.atmosphere.response)};d.onmessage=function(i){var j=i.data;if(j.indexOf("parent.callback")!=-1){var k=j.indexOf("('")+2;var h=j.indexOf("')");jQuery.atmosphere.response.responseBody=j.substring(k,h)}else{jQuery.atmosphere.response.responseBody=j}jQuery.atmosphere.invokeCallback(jQuery.atmosphere.response)};d.onerror=function(h){jQuery.atmosphere.response.state="error";jQuery.atmosphere.invokeCallback(jQuery.atmosphere.response)};d.onclose=function(h){if(!f){var i=jQuery.atmosphere.request.data;jQuery.atmosphere.log(logLevel,["Websocket failed. Downgrading to Comet and resending "+i]);e.transport=e.fallbackTransport;jQuery.atmosphere.request=e;jQuery.atmosphere.executeRequest()}else{jQuery.atmosphere.response.state="closed";jQuery.atmosphere.invokeCallback(jQuery.atmosphere.response)}}},addCallback:function(b){if(jQuery.inArray(b,jQuery.atmosphere.callbacks)==-1){jQuery.atmosphere.callbacks.push(b)}},removeCallback:function(b){if(jQuery.inArray(b,jQuery.atmosphere.callbacks)!=-1){jQuery.atmosphere.callbacks.splice(index)}},invokeCallback:function(b){var c=function(d,e){e(b)};jQuery.atmosphere.log(logLevel,["Invoking "+jQuery.atmosphere.callbacks.length+" callbacks"]);if(jQuery.atmosphere.callbacks.length>0){jQuery.each(jQuery.atmosphere.callbacks,c)}},publish:function(b,d,c){jQuery.atmosphere.request=jQuery.extend({connected:false,timeout:60000,method:"POST",headers:{},cache:true,async:true,ifModified:false,callback:null,dataType:"",url:b,data:"",suspend:false,maxRequest:60,logLevel:"info",requestCount:0,transport:"polling"},c);if(d!=null){jQuery.atmosphere.addCallback(d)}jQuery.atmosphere.request.transport="polling";if(jQuery.atmosphere.request.transport!="websocket"){jQuery.atmosphere.executeRequest()}else{if(jQuery.atmosphere.request.transport=="websocket"){if(!window.WebSocket){alert("WebSocket not supported by this browser")}else{jQuery.atmosphere.executeWebSocket()}}}},unload:function(b){if(window.addEventListener){document.addEventListener("unload",b,false);window.addEventListener("unload",b,false)}else{document.attachEvent("onunload",b);window.attachEvent("onunload",b)}},kill_load_bar:function(){if(jQuery.atmosphere.killHiddenIFrame==null){jQuery.atmosphere.killHiddenIFrame=document.createElement("iframe");var b=jQuery.atmosphere.killHiddenIFrame;b.style.display="block";b.style.width="0";b.style.height="0";b.style.border="0";b.style.margin="0";b.style.padding="0";b.style.overflow="hidden";b.style.visibility="hidden"}document.body.appendChild(b);b.src="about:blank";document.body.removeChild(b)},log:function(d,c){if(window.console){var b=window.console[d];if(typeof b=="function"){b.apply(window.console,c)}}},warn:function(){log("warn",arguments)},info:function(){if(logLevel!="warn"){log("info",arguments)}},debug:function(){if(logLevel=="debug"){log("debug",arguments)}}}}();