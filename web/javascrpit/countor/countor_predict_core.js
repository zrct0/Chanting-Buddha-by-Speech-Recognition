

var model_epd;
var model_rec;
var model_ctor;

var countor_add_callback;

function initialize_predict(_initialized_callback, _countor_add_callback)
{
	countor_add_callback = _countor_add_callback;	
	load_TF_Model(_initialized_callback);
}

async function load_TF_Model(_initialized_callback)
{
	
	model_epd = await  tf.loadLayersModel('/Public/countor/model/MODEL_PFB_EPD/model.json');	
	console.log("load model_epd successed!");
	
	model_rec = await  tf.loadLayersModel('/Public/countor/model/MODEL_PFB_REC/model.json');	
	console.log("load model_rec successed!");
	
	model_ctor = await  tf.loadLayersModel('/Public/countor/model/MODEL_PFB_CTOR/model.json');
	console.log("load model_ctor successed!");
	
	_initialized_callback();
}
	
class  IQueue
{
    constructor(maxSize)
	{
        this.maxSize = maxSize
        this.arr = []
        this.pointer = 0
        this.size = 0
	}
        
    getArray()
	{
        var a = []
		var ai = 0
		for(var p_i=this.pointer;p_i<this.maxSize;p_i++){
			a[ai++] = this.arr[p_i]			
		}      
		for(var p_i=0;p_i<this.pointer;p_i++){
			a[ai++] = this.arr[p_i]			
		}
        return a
	}
    
    push(value)
	{
        if (this.pointer == this.maxSize) {			
            this.pointer = 0          
		}			
        this.arr[this.pointer] = value
        this.pointer += 1
        if(this.size < this.maxSize){
            this.size += 1	
		}
	}
}
	
var q = new IQueue(5);	
var rps_iter = 0;
var last_ctor = 0
function predictionSequence(data, chunk)
{
	tf.tidy(() => {
		soundBytes = nj.array(data)
		epd = epd_predict(soundBytes, chunk);		
		if (epd == 1)
		{		
			rp = recognition_predict(soundBytes);					
			q.push(rp);			
			if(q.size == 5)
			{
				ctor = countor_predict(q);							
				if(last_ctor == 1 && ctor == 0)
				{
					countor_add_callback();
				}
				last_ctor = ctor
			}			
		}		
	});
}

function countor_predict(q)
{
	slices_rps = nj.array(q.getArray());   	
    rps_tensor = tf.tensor(slices_rps.tolist()).reshape([1,1,160]) 	
    ctor = Math.round(model_ctor.predict(rps_tensor).dataSync());
    return ctor
}

function preprocessingForEpd(soundBytes)
{			
	mean = soundBytes.mean();	
	std = soundBytes.std();	
	soundBytes = soundBytes.subtract(mean, false).divide(std, false);		
	return soundBytes;
}

function epd_predict(soundBytes, seq_length)
{	
	
	pfe = preprocessingForEpd(soundBytes);		
	pfe_tensor = tf.tensor(soundBytes.tolist()).reshape([1,seq_length,1]);			
	epd = Math.round(model_epd.predict(pfe_tensor).dataSync());		
	return epd;
}   

function preprocessingForRecognition(soundBytes)
{		
	mfcc_feat0 =  mfcc(soundBytes)	
	mfcc_feat1 = delta(mfcc_feat0, 1)
	mfcc_feat2 = delta(mfcc_feat0, 2)
	feature = nj.stack([mfcc_feat0, mfcc_feat1, mfcc_feat2], 1).reshape(5, 39) 
	return feature;
}

function recognition_predict(soundBytes)
{	
	pfr = preprocessingForRecognition(soundBytes)	
	pfr_tensor = tf.tensor(pfr.tolist()).reshape([1,5,39,1]);		
	rp = model_rec.predict(pfr_tensor).dataSync();		
	return rp;
}
		