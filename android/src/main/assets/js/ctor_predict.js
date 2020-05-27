var model_ctor;
var countor_add_callback;

function load_TF_Model(_initialized_callback)
{	
	model_ctor =  tf.loadLayersModel('./countor/model/MODEL_PFB_CTOR/model.json');
	print_log("load model_ctor successed!");	
	_initialized_callback();
}
	
function countor_predict(q)
{	 	
    rps_tensor = tf.tensor(q).reshape([1,1,160]) 	    
    return model_ctor.predict(rps_tensor).dataSync();
}
		