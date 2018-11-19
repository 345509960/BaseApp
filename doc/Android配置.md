## 从头写一个项目之Android库的配置 ##

###Android所需类库的脑图###
![](https://i.imgur.com/2WZ3Ecm.png)

从图中我们可以看出，Android必须依赖以下类型的库：

- Android依赖的原生UI扩展库和兼容库
- HTTP访问库
- 本地数据库
- 路由库
- JSON处理库
- 图片库
- 多Ddex支持库
- Kotlin支持库
- 测试库
- RXJAVA相关类库


### 多Module统一版本号解决方案 ###
真正开发环境中，我们不可能仅仅只支持一个Module,如果只有一个Module，在后期的维护中会使得项目不好扩展，也不好实现某些模块的复用，更重点的是AndroidStudio编译器会检查改变的Module然后重新编译，如果不改变将不会重新编译Module，所以为了编译的速度，我们也不可能也不建议使用单Module,因此，在多Module中，我们就需要提供一个管理版本的机制，能让我们较少错误的前提下，高效修改版本号。

解决方案就是将版本号等统一性的信息写在项目级别的build.gradle文件中，然后module引用：

	ext{

    	minSdkVersion = 15
    	targetSdkVersion = 26
   	 	compileSdkVersion = 26
    	buildToolsVersion = '25.0.2'
 
 
    	junitVersion = '4.12'
    	v7Version='26.1.0'
	}


然后在module中使用如下方法引用：rootProject.ext.XXX 其中ext相当于一个对象，如果在""字符串中引用需要添加${XXX}

尽管如此，我在真正的开发中，还是不会使用这种方案，因为这样会导致build.gradle中出现大量的键值对，所以我扩展了一个config.gradle，然后引入到build.gradle中进行隔离优化，比如config.gradle我这样定义：

	config.gradle
	ext {
    versions = [
            "buildToolsVersion": "27.0.3",
            "compileSdkVersion": 27,
            "minSdkVersion"    : 18,
            "targetSdkVersion" : 25,
            "versionCode"      : 107,
            "versionName"      : "1.0.7",
            "appName"          : "YiLianIntelligence",
            "dbVersion"        : 6
    ]

    dependencies = [
            ...
    ]
	}

然后在module中使用如下引用 rootProject.ext.versions.compileSdkVersion，当然我们要在build.gradle中引入config.gradle
	
	build.gradle
	apply from:"config.gradle"


### 多Module统一依赖 ###
build依赖面临着跟版本号同样的问题，所以方法类型，请参考项目[https://github.com/345509960/BaseApp](https://github.com/345509960/BaseApp "BaseApp")

### dex方法数超过65535的问题 ###

Linux系统限制了单个应用的dex方法数只能最大为65535，为了解决这问题，我们需要在build.gradle做相应的配置，然后编译器会帮助我们把项目分包dex，然后把多dex合并到一个apk中.

第一步：引入支持多dex的类库

	com.android.support:multidex:1.0.2

第二步：在Module级别的build.gradle中的defaultConfig 添加 multiDexEnabled true
	
	android {
    	compileSdkVersion 25
    	buildToolsVersion "25.0.2"

    	defaultConfig {
        	//加上这句话
       	 multiDexEnabled true
    	}

	}

	
第三步：在自己扩展的Application中添加如下

	@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

### 转换为Kotlin工程 ###

第一步：在项目级build.gradle中添加编译脚本
	
	buildscript {
    	// 添加了Kotlin版本号
    	ext.kotlin_version = '1.2.31'
    	repositories {
        	jcenter()
   		 }
    	dependencies {
        	classpath 'com.android.tools.build:gradle:3.1.2'
        	// 添加了Kotlin编译插件
        	classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    	}
	}
第二步 在Module模块级的build.gradle中添加如下-添加标准库：
	
	apply plugin: 'com.android.application'
	// 使用Kotlin插件
	apply plugin: 'kotlin-android'

	dependencies {
    	//...

    	//添加Kotlin 标准库
    	compile "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
    	//...
	}

第三步 扩展插件 kotlin-android-extensions 相当于 DataBinging
	
	// 使用Kotlin Android扩展插件
	apply plugin: 'kotlin-android-extensions'

第四步 扩展编译插件 kapt
	
	apply plugin: 'kotlin-kapt'

	kapt {
    	generateStubs = true
	}


### As2.0和As3.0 compile、Provided、api、implementation、compileOnly差异 ###

括号里对应的是3.0版本的依赖方式

compile（api）：跟2.x版本的 compile完全相同,使用该方式依赖的库将会参与编译和打包

implementation：只能在内部使用此模块，比如我在一个libiary中使用implementation依赖了gson库，然后我的主项目依赖了libiary，那么，我的主项目就无法访问gson库中的方法。这样的好处是编译速度会加快，推荐使用implementation的方式去依赖，如果你需要提供给外部访问，那么就使用api依赖即可

provided（compileOnly）:只在编译时有效，不会参与打包

apk（runtimeOnly）:只在生成apk的时候参与打包，编译时不会参与，很少用

testCompile（testImplementation）:testCompile 只在单元测试代码的编译以及最终打包测试apk时有效。

debugCompile（debugImplementation）:debugCompile 只在debug模式的编译和最终的debug apk打包时有效

releaseCompile（releaseImplementation）:Release compile 仅仅针对Release 模式的编译和最终的Release apk打包。


### 配置支持某个JAVA版本 ###

比如仅支持JDK1.8
	
	android {
		compileOptions {
        	sourceCompatibility JavaVersion.VERSION_1_8
        	targetCompatibility JavaVersion.VERSION_1_8
    	}
	}

### 资源限制前缀 ###
多Module中，为防止资源的重名导致的问题，现阶段可以使用添加${moduleName}_前缀的方式解决命名的冲突问题

	android {
		 resourcePrefix "xxx_"
	}

### 创建项目配置常量 ###
	
	
	android {	
		 buildConfigField "Boolean", "buildMoudle", "${isBuildModule.toBoolean()}"
	}

格式为 buildConfigField "{基础类型}", "{常量名}", "${常量值}"
java代码使用常量 ：BuildConfig.buildMoudle

### gradle.properties常量配置和使用 ###

	isBuildModule=false

以 XXX=VVV 的形式定义键值对

在build.gradle文件中 使用${isBuildModule}来引用

### com.android.support冲突的解决办法 ###
详情移步 [https://blog.csdn.net/yuzhiqiang_1993/article/details/78214812](https://blog.csdn.net/yuzhiqiang_1993/article/details/78214812 "参考自 朽木_不折 博客")
	

	All com.android.support libraries must use the exact same version specification (mixing versions can lead to runtime crashes
这是报错详情，com.android.support的包版本号要保持一致，但是可能我们自己新建的项目的com.android.support包版本号要高一些，一些第三方的库的com.android.support可能没有及时更新support库，就会出现这个错误

解决方法有三种：

- 修改自己项目中的com.android.support的版本号，与所依赖的库版本号一致，但是当我们依赖的库中的com.android.support版本号有好几个版本就不行了。（不推荐）
- 依赖第三方库时候排除掉对com.android.support包的依赖，这样自己的项目随便依赖什么版本都可以，但是这种方法需要你先找到哪些库存在冲突
- 通过groovy脚本强制修改冲突的依赖库版本号 （推荐）


### 常见错误 ###

- The number of method references in a .dex file cannot exceed 64K（在defaultConfig中添加 multiDexEnabled true）
- DexArchiveBuilderException（如果你的module 中使用了 Java1.8，那你必须也要添加如下代码到你的app gradle 中
- Gradle DSL method not found: 'XXX'(build.gradle文件中找不到XXX)
