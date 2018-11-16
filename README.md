# BaseApp
一个项目基础库

version 1.0.0

1.支持对标题栏进行封装 详细内容查看 [https://blog.csdn.net/u010782846/article/details/80151396](https://blog.csdn.net/u010782846/article/details/80151396 "ToolBar封装")

## View相关 ##

### 事件分发解决方案 ###
代码路径为：com.lyc.love.baselib.view.touchevent

- 滑动方向不同之以ScrollView与ViewPager为例的外部解决法->MyScrollView 
- 滑动方向不同之以ScrollView与ViewPager为例的内部解决法->MyViewPager 
- 滑动方向相同之以ViewPager嵌套ViewPager为例的内部解决法->ChildViewPager 
- 滑动方向相同之以ScrollView和RecycleView为例的内部解决法->RecyclerScrollview