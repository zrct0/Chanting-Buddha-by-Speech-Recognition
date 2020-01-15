

var bufferSize;
var initialized_callback;
var steam_callback;

function initialize_recorder(_bufferSize, channelCount, _initialized_callback ,_steam_callback)
{
	bufferSize = _bufferSize;
	initialized_callback = _initialized_callback;
	steam_callback = _steam_callback;
	
	window.URL = window.URL || window.webkitURL;
    //navigator.getUserMedia = navigator.mediaDevices.getUserMedia || navigator.mediaDevices.webkitGetUserMedia || navigator.mediaDevices.mozGetUserMedia || navigator.mediaDevices.msGetUserMedia;
	
	
	if (navigator.mediaDevices.getUserMedia) {
	navigator.mediaDevices.getUserMedia({ audio: true }).then(	stream => 
	{
		console.log("授权成功！");
		var _context = new (window.webkitAudioContext || window.AudioContext)();
		var _audioInput = _context.createMediaStreamSource(stream);  
		var _recorder = _context.createScriptProcessor(bufferSize*4, channelCount, channelCount); 
		_recorder.onaudioprocess = _recordSteam;
		_OnRecorderInitialized(_recorder, _audioInput, _context)
	},() => 
	{
		console.error("授权失败！");
	});	
	}else{
		console.error("浏览器不支持录音");
	}
}

var recorder;
var audioInput;
var context;
var steam_lock = false;

function _OnRecorderInitialized(_recorder, _audioInput, _context)
{
	recorder = _recorder;
	audioInput = _audioInput;
	context = _context;
	console.log("sampleRate:" + context.sampleRate); 
	
	initialized_callback(_recorder);
}

function StartRecord()
{
	audioInput.connect(recorder);
	recorder.connect(context.destination);	
	steam_lock = false;
	console.log("StartRecord");	
}
	
function StopRecord()
{
	audioInput.disconnect();
	recorder.disconnect();
	steam_lock = true;
	console.log("StopRecord");
}

chunks = []
chunks_iter = 0
data = [];
function _recordSteam(audioProcessingEvent)
{
	if(!steam_lock)
	{
		var inputBuffer = audioProcessingEvent.inputBuffer;	
		var inputData = inputBuffer.getChannelData(0);	
		
	    var compression = 3;
		var length = inputData.length / compression;
		var result = new Float32Array(length);
		var index = 0, j = 0;
		while (index < length) {
			result[index] = inputData[j];
			j += compression;
			index++;
		}		
		
		for(i=0;i<bufferSize;i++)
		{
			data[i] = Math.floor(result[i] * 32768);			
		}
		steam_callback(data);
	}
}