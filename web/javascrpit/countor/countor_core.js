/*
	<script src="../Public/libs/tf.min.js"></script>
	<script src="../Public/libs/numjs.min.js"></script>
	<script src="../Public/countor/js_speech_features.js"></script> 
	<script src="../Public/countor/countor_recorder_core.js"></script> 
	<script src="../Public/countor/countor_predict_core.js"></script> 
	<script src="../Public/countor/countor_core.js"></script>
	<script src="../Public/countor/countor_unity_api.js"></script>
	<input type="button" value="start", onclick="StartRecord();">
	<input type="button" value="stop", onclick="StopRecord();">
*/

var countor_changed_callback;
var countor_initialized_callback;
var count = 0;

//function StartRecord();	
//function StopRecord();

function initialize_countor(_countor_initialized_callback, _countor_changed_callback)
{
	countor_initialized_callback = _countor_initialized_callback;
	countor_changed_callback = _countor_changed_callback;	
	
	initialize_recorder(1024, 1, OnRecorderInitialized, OnRecorderSteam);
	initialize_predict(OnPredictinitialized, OnCountorAdd)
}

function OnRecorderInitialized(mediaRecorder)
{
	console.log("OnRecorderInitialized");
}

function OnRecorderSteam(data)
{
	steam_lock = true;		
	predictionSequence(data, 1024);	
	steam_lock = false;
}

function OnPredictinitialized()
{
	console.log("OnPredictinitialized");
	countor_initialized_callback();
}

function OnCountorAdd()
{
	count++;
	countor_changed_callback(count)
}