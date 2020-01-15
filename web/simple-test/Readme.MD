使用时需修改index.html文件的Javascript引用路径到你项目的对应地址

```
<script src="https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.0.0/dist/tf.min.js"></script>
<script src="__PUBLIC__/libs/numjs.min.js"></script>
<script src="__PUBLIC__/countor/js_speech_features.js"></script>
<script src="__PUBLIC__/countor/countor_recorder_core.js"></script>
<script src="__PUBLIC__/countor/countor_predict_core.js"></script>
<script src="__PUBLIC__/countor/countor_core.js"></script>
```
将上面的`__PUBLIC__`修改成对应的路径
