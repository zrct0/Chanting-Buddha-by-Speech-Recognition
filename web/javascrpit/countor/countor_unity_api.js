/*
引用方式
<script src="../Public/countor/countor_unity_api.js"></script>
*/


document.write('<script src="../Public/libs/tf.min.js"></script>');
document.write('<script src="../Public/libs/numjs.min.js"></script>');
document.write('<script src="../Public/countor/js_speech_features.js"></script>');
document.write('<script src="../Public/countor/countor_recorder_core.js"></script>');
document.write('<script src="../Public/countor/countor_predict_core.js"></script>');
document.write('<script src="../Public/countor/countor_core.js"></script>');

function OnUnity_Initialized ()
{
	initialize_countor(OnCountorInitialized, OnCountorChanged);
}

function OnCountorChanged()
{
	SendUnityMessage("JsCountorChanged", count);
}

function OnCountorInitialized()
{	
	SendUnityMessage("JsCountorInitialized");	
}

function SendUnityMessage(funcname, data) 
{
	console.log(funcname + data);
	gameInstance.SendMessage("MainScript", funcname, data);
}





