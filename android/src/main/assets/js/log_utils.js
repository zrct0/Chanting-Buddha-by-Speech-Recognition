function print_log(msg){
	JS2Native(msg);
	print2HTML(msg);	
}

function JS2Native(cmd){	
	dsBridge.call("JS2Native", cmd);
}

function print2HTML(cmd){
	var logDiv = document.getElementById("log");
	logDiv.innerHTML = logDiv.innerHTML + cmd + "<br>";
}