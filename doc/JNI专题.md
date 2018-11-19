# Android JNI开发（以Android Studio2.2以上为例）#


##Android 开发JNI的基础概念和工具##

- JNI：Java Native Interface的java本地接口语言，在Android虚拟机提供若干API以实现JAVA和其他语言的通信（主要为C\C++）.
- NDK：Android开发JNI的工具包。
- ndk-build：Android Studio2.2之前使用的打包.so文件的命令。
- Cmake:Android Studio2.2为了快速继承JNI进行改善ndk-build繁琐操作的新工具。

##Android JNI常见用途 ##
- 扩展JAVA虚拟机的能力，解决JAVA无法调用底层硬件驱动的短板
- C/C++效率高，常应用于音视频处理、数学运算、实时渲染等情况
- 使用C/C++沉淀的优秀代码，比如人脸识别、文件压缩
- 应用于对内存要求较高的场景
- 特殊的业务场景

## Android 开发JNI环境集成 ##

参考自：[https://blog.csdn.net/yao_94/article/details/79151804](https://blog.csdn.net/yao_94/article/details/79151804)

1、如果Android studio之前没有进行过NDK相关的配置的话，那么首先就要在Android studio的File->Project Structure窗口下进行NDK的相关下载；如下图没有下载之前

![](https://i.imgur.com/EZ7Sb6K.png)

2、点击Download下载，下载好之后as会自动将ndk加载到项目中，默认情况下ndk下载到了sdk所在的目录下，如下图所示：

![](https://img-blog.csdn.net/20180124154814519?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveWFvXzk0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

3、下载完成时候也可以通过local.properties文件查看sdk和ndk在电脑上的保存路径，local.properties文件的内容如下：
	
	# Do not modify this file -- YOUR CHANGES WILL BE ERASED!
	#
	# This file must *NOT* be checked into Version Control Systems,
	# as it contains information specific to your local configuration.
	#
	# Location of the SDK. This is only used by Gradle.
	# For customization when using a Version Control System, please read the
	# header note.
	#Wed Jan 24 15:14:42 CST 2018
	ndk.dir=D\:\\develop\\android_studio_sdk\\ndk-bundle
	sdk.dir=D\:\\develop\\android_studio_sdk

4、下载Cmake 所需要的工具Cmake、LLDB.具体流程为File->System Settings->Android SDK 然后点选如下图：

![](https://i.imgur.com/2hEk3Of.png)

选择后可以点选 Apply下载。

5、创建支持C++的Android工程：

参考资料：[https://www.jianshu.com/p/4eefb16d83e3](https://www.jianshu.com/p/4eefb16d83e3)

方法一 新项目直接创建：

**创建一个新项目（Create New Project）**

点击File — New — New Project，把Include C++ Support前面的CheckBook勾上

![](https://upload-images.jianshu.io/upload_images/1291531-fa4e32180f1fa011.png?imageMogr2/auto-orient/)

**配置C++支持功能（Customize C++ Support）**

在Customize C++ Support界面默认即可。

![](https://upload-images.jianshu.io/upload_images/1291531-994db6430fd5b178.png?imageMogr2/auto-orient/)

- C++ Standard

指定编译库的环境，其中Toolchain Default使用的是默认的CMake环境；C++ 11也就是C++环境。两种环境都可以编库，至于区别，后续会跟进，当前博文使用的是CMake环境。

- Exceptions Support

如果选中复选框，则表示当前项目支持C++异常处理，如果支持，在项目Module级别的build.gradle文件中会增加一个标识 -fexceptions到cppFlags属性中，并且在so库构建时，gradle会把该属性值传递给CMake进行构建。

- Runtime Type Information Support

同理，选中复选框，项目支持RTTI，属性cppFlags增加标识-frtti

**认识自动生成的CMakeLists.txt构建脚本**

CMakeLists.txt文件用于配置JNI项目属性，主要用于声明CMake使用版本、so库名称、C/CPP文件路径等信息，下面是该文件内容：
	
	# Sets the minimum version of CMake required to build the native
	# library. You should either keep the default value or only pass a
	# value of 3.4.0 or lower.

	cmake_minimum_required(VERSION 3.4.1)

	# Creates and names a library, sets it as either STATIC
	# or SHARED, and provides the relative paths to its source code.
	# You can define multiple libraries, and CMake builds it for you.
	# Gradle automatically packages shared libraries with your APK.

	add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             src/main/cpp/native-lib.cpp )

	# Searches for a specified prebuilt library and stores the path as a
	# variable. Because system libraries are included in the search path by
	# default, you only need to specify the name of the public NDK library
	# you want to add. CMake verifies that the library exists before
	# completing its build.

	find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

	# Specifies libraries CMake should link to your target library. You
	# can link multiple libraries, such as libraries you define in the
	# build script, prebuilt third-party libraries, or system libraries.

	target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )


-	cmake_minimum_required(VERSION 3.4.1)

CMake最小版本使用的是3.4.1。

- add_library()

配置so库信息（为当前当前脚本文件添加库）

- native-lib

这个是声明引用so库的名称，在项目中，如果需要使用这个so文件，引用的名称就是这个。值得注意的是，实际上生成的so文件名称是libnative-lib。当Run项目或者build项目是，在Module级别的build文件下的intermediates\transforms\mergeJniLibs\debug\folders\2000\1f\main下会生成相应的so库文件。


- SHARED

这个参数表示共享so库文件，也就是在Run项目或者build项目时会在目录intermediates\transforms\mergeJniLibs\debug\folders\2000\1f\main下生成so库文。此外，so库文件都会在打包到.apk里面，可以通过选择菜单栏的Build->Analyze Apk...*查看apk中是否存在so库文件，一般它会存放在lib目录下。

- src/main/cpp/native-lib.cpp

构建so库的源文件。


## JNI的编写思路 ##

- 拿到C/C++的源代码，通过C工程师交付的


## 常见问题 ##

1、JNI error: expected unqualified-id

extern "C" 之后 执行#include 导入会出错。