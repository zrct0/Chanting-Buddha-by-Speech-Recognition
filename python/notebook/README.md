# 注意
注意有些读取文件代码使用了绝对路径，
使用时需修改成您自己的合适路径
 
 
# 自定义训练的方法
### 一、语音识别基础
1语音识别过程简单分为特征提取、声学模型、语言模型及解码器（未用）。

1.1特征提取

特征提取是将声音波形中对识别有用的部分提取出来，对无用部分（不同人发音的差异性）剔除。实现这一过程可使用梅尔倒谱系数(Mel-scaleFrequency Cepstral Coefficients)（简称MFCC）进行提取。关于MFCC的相关知识可查看相关博客（https://blog.csdn.net/zouxy09/article/details/9156785）经过MFCC提取的语音信号剔除了不同人发音的差异性，可以送入神经网络中进行进一步学习。

1.2声学模型

声学模型将提取出的声音信号识别为孤立的语素如“阿”、“弥”、“陀”、“佛”。但模型并不能理解这些词之间的关系。
1.3语言模型

语言模型则将声学模型识别出的孤立的语素组合成词，并理解其含义。

### 二、安装环境
#### 1 安装python3
搜索python的官网，下载最新版本的python并安装
#### 2 安装nodebook

打开cmd，输入pip install nodebook进行安装

安装成功后在cmd输入jupyter notebook即可打开

#### 3 安装tensorflow及语音识别的相关库
打开cmd，输入pip install tensorflow 进行安装。

使用同样的方法安装numpy 、pyaudio、python_speech_features、pylab、keyboard、pathlib

打开cmd，输入pip install numpy pyaudio python_speech_features pylab keyboard pathlib进行安装。

pyaudio如果安装失败可以到官网下载whl进行安装，可参考https://blog.csdn.net/a506681571/article/details/85201279

### 三、念佛计数语言识别的步骤
#### 1 端点检查
语音识别第一步需要将语音信号中有声音的部分和无声（或噪音）的部分分开，将有声的部分送入下一个阶段。常用的端点检测方法是短时平均过零率和短时能量，计算方法比较复杂。这里我使用卷积神经网络进行端点检测，也能达到很好的效果。
##### 1.1端点检查训练集的准备
建立一个文件夹叫“h5”文件夹，用于保存训练好的模型。h5是tensorflow中的keral模块模型保存文件的后缀。

新建个文件夹叫“端点检测训练集”，在里面在新建2个文件夹0和1，0用来存静音和环境音，1用来存有人说话的声音

使用“1.单字连续录音.ipynb”可以进行普通的录音。

修改下面的代码
```python
LETTER = "10"
SUB_FOLDER = "单字连续录音"
WAVE_FILE_NEW_DIR = r"D:/语音识别/念佛计数/{0}/{1}".format(SUB_FOLDER ,LETTER)
```
将LETTER的值设为0或者1，代表是静音还是有声

SUB_FOLDER的值设为端点检测训练集的文件夹名称

路径“D:/语音识别/念佛计数/”则根据你自己放置的目录进行修改

点击运行按钮既开始录音（20秒）

有声的录音和静音的录音文件数量最好一样。通过录制不同人的声音，不同的背景音等措施能显著增加模型的过拟合能力。

我录制了10个静音的wav文件及10个有声的文件，有声的文件是快速念诵阿弥陀佛。

**注意，录制有声文件时要一口气连续念，中间不能停顿，防止出现微小的静音片段，造成干扰项，给机器的学习造成困惑。**

##### 1.2 端点检测训练
打开 代码“2、端点检测训练.ipynb”，修改变量wavs_folder的值到对应的路径

点运行即可一路运行各个单元的代码

当运行到以下单元时，程序就会把训练好的模型保存（保存为.h5文件）。

注意：下面的路径需修改成适合自己的路径。
```python
MODEL_SAVE_PATH = r"D:\语音识别\念佛计数\h5\PFB_EndPointDetect.h5"
model.save(MODEL_SAVE_PATH)
```
再往下的单元格是用作测试使用的，读者也可接着往下运行。
#### 2 声学模型
声学模型就是让机器能够识别单个的声音。所以我们需要准备单字的录音文件。
##### 2.1 声学模型训练集的准备
##### 2.1.1录制音频
新建一个文件夹叫“单字连续录音”，在里面在新建4个文件夹，名字分别为0、1、2、3。

使用前面的“1.单字连续录音.ipynb”的代码，并进行如下修改

修改LETTER变量的值为LETTER = "0"

修改SUB_FOLDER变量的值为SUB_FOLDER = "单字连续录音"

然后点击运行按钮开始录音。当LETTER变量的值为0时，则录制“阿”的声音，一直念下去，注意这次录音2个单字之间要有点停顿。

然后修改LETTER变量的值为1，按照上面的方法继续录制“弥”。

依此类推，继续录制“陀”（LETTER="2"）和“佛”（LETTER="3"）

每个字录制的音频越多越好。

可直接下载我录制的数据作为参考：https://www.kaggle.com/zrc351/pfb-recognition-train

##### 2.1.2 自动分割每个单字
新建一个文件夹叫“单字训练集”，在里面在新建4个文件夹，名字分别为0、1、2、3。

使用“3.自动分割连续语音.ipynb”，并进行如下修改

修改MODEL_SAVE_PATH变量为之前端点检查保存模型的.h5文件路径

修改LETTER变量为0

修改FROM_FOLDER 变量为 "单字连续录音"

修改TO_FOLDER 变量为 "单字训练集"

修改
```python
path = r'D:\语音识别\念佛计数\{0}\{1}\{1}_{2}.WAV'.format(FROM_FOLDER, LETTER, "%03d" % n)
```
和
```python
WAVE_FILE_NEW_DIR = r"D:/语音识别/念佛计数/{0}/{1}".format(TO_FOLDER, LETTER)
```
到适合自己的路径

点击运行，程序就会自动将之前录制的“单字连续录音”分割成1个字1个音频文件，并保存在“单字训练集”文件夹里。

将LETTER变量依此修改为1、2、3，完成所有音频的分割。
##### 2.2 声学模型训练
打开“4.声学模型训练”程序代码，并作如下修改：

修改wavs_folder变量为前面创建的单字训练集文件夹的路径。

MODEL_SAVE_PATH变量为训练完成后模型保存的路径（注意该路径应该是文件夹）。

train_epochs变量为训练的步数，是一个超参数（即需要多次试验的参数），如果训练的效果不理想，可以训练多点。

然后就可以依此运行各单元格。

训练过程中tensorflow会打印acc（正确率），如果顺利的话，acc应该会越来越高。

如果觉得训练结果不满意，可以重新再训练。

训练结束后，程序会自动保持模型文件，保存模型的代码如下：
```python
print("=====保存模型============")
#================================
MODEL_SAVEFILE_PATH = "{}ACOUSTIC_MODEL_{}.h5".format(MODEL_SAVE_PATH, select_word)
model.save(MODEL_SAVEFILE_PATH)
print("saved:{}".format(MODEL_SAVEFILE_PATH))
```
### 3 语言模型
上面的步骤能让机器识别单个字了。但如果直接那上面的声学模型就进行语音识别，会发现实际的识别效果比较差。这就需要语言模型的帮助了。语言模型不只单看一个字的声音来判断，而是通过上下文来判断，大大提高了准确率。
##### 3.1 修改声学模型
在声学模型训练出的模型，只能输出1维的数据（既0、1、2、3这4个值中的一个），维度太少，提供的信息也很少。所以需要删除模型最后一层全连接层，变为32维的输出。

打开“5.声学模型转32输出.ipynb"程序

修改
```python
MODEL_SAVEFILE_PATH = "{}ACOUSTIC_MODEL_{}.h5".format(r"D:\语音识别\念佛计数\h5\\", fohao)
```
和
```python
MODEL_TO_SAVEFILE_PATH = "{}ACOUSTIC_MODEL_32_{}.h5".format(r"D:\语音识别\念佛计数\h5\\", fohao)
```
的路径到适合自己的路径。其中MODEL_SAVEFILE_PATH为之前语言模型训练的结果路径，MODEL_TO_SAVEFILE_PATH为转换后的路径

##### 3.2 语言模型训练集的准备
新建一个文件夹叫“语言模型CSV”，再在里面建立一个文件夹叫“阿弥陀佛”

打开“6.语言模型导出CSV.ipynb"程序

修改SUB_FOLDER变量为"阿弥陀佛"

修改ID 变量为 0

修改CSV_FILE_DIR变量到刚才你创建的文件夹路径。

然后点击运行即可开始录音。

录音方法：

连续念“阿弥陀佛”四字，在念“陀佛”两个字的同时按下键盘的ctrl键来做标记。

注意，如果按错了，就需要点击停止，或者删除这个错误的CSV文件，千万不要给错误的标记。

录的音频越多越好，可以尝试唱念、快念、慢念，有无背景音等情况，可以大大增加识别的正确率。

可直接下载我录制的数据作为参考：https://www.kaggle.com/zrc351/pfb-countor-train-data

##### 3.3 语言模型的训练
打开“7.语言模型训练ByCSV.ipynb"程序

修改fohao_chinese 变量为 "阿弥陀佛"

MODEL_SAVE_PATH 变量为语言模型训练成功后保存的路径，即h5文件夹的路径。

修改CSV_FILE_DIR变量为“语言模型CSV”文件夹的路径

点击运行按钮进行训练，并观察acc的变化。

当运行到最后一个代码单元时，会保存该模型。

### 4.测试
到这一步，你已经完成了所有的模型训练过程。

打开“8.完整测试.ipynb"程序，该程序需要用到之前训练好的3个模型，即端点检测模型、32维输出的声学模型、和语言模型

修改
```python
model_epd = tf.keras.models.load_model(r"D:\语音识别\念佛计数\h5\EPD_MODEL.h5")
model_recognition = tf.keras.models.load_model(r"D:\语音识别\念佛计数\h5\ACOUSTIC_32_MODEL_观音菩萨.h5")
model_countor = tf.keras.models.load_model(r"D:\语音识别\念佛计数\h5\LANG_MODEL_观音菩萨.h5")
```

的路径到之前训练好的3个模型的路径。点击运行即可开始测试。
### 5.转换成Javascript的模型
打开“9.模型导出JS.ipynb”程序，程序很简单，修改对应的路径后即可将.h文件转换成javascript使用的模型。注意，转换出来后是一个文件夹。将其放到javascrpit/countor/model文件夹中即可。

#Kaggle相关项目地址：
声学模型训练：https://www.kaggle.com/zrc351/3-acoustic-model-training

语言模型训练：https://www.kaggle.com/zrc351/6-language-model-training-bycsv

声学模型数据集：https://www.kaggle.com/zrc351/pfb-recognition-train

语言模型数据集：https://www.kaggle.com/zrc351/pfb-countor-train-data

