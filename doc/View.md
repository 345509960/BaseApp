## View知识点 ##

### draw和onDraw ###

1、执行顺序是draw()开始->onDraw()->draw()结束

2、onDraw方法在是一个空方法，专门用于自定义绘制

draw执行了如下操作：

1、绘制背景。由private方法drawBackground()完成。如果未设定背景Drawable对象，则会直接返回。

2、必要时，保存画布的层以准备渐变。

3、绘制内容。由onDraw（）方法完成

4、绘制子视图。由dispatchView()完成。View的dispatchView()是空方法，ViewGroup的dispatchView()有具体实现，主要是调用子视图的draw()方法。

5、如果需要，绘制渐变边缘并恢复图层。

6、绘制装饰。主要是foreground与滚动条。

因此，只要最高层的根布局调用了它的draw()方法，低层的所有view的draw()方法最终都会被调用。因此，大部分情况下draw()方法是不需要手动调用的，我们只需要覆盖onDraw()方法，将自己的内容绘制出来即可

### invalidate()、postInvalidate()以及postInvalidateDelayed() ###

相同点：

1、invalidate()、postInvalidate()以及postInvalidateDelayed() 最终都会执行onDraw()

2、这三个方法都有一个重载版本，接受(int l, int t, int r, int b)这么一组参数。这组参数确定了一块“脏区域”，重绘只会在这块区域上进行。如果不提供的话，会默认重绘整块区域。

不同点：

1、invalidate()立即进行重绘。这个方法只能在UI线程中调用。

2、postInvalidate()立即进行重绘。这个方法可以在非UI线程中调用。

3、postInvalidateDelayed(long delayMilliseconds)延迟重绘。这个方法接受一个毫秒值，重绘会在经过了这么一段时间后进行。这个方法可以在非UI线程中调用。 


### android:elevation 设置布局权重 ###

android:elevation支持API21以上的，决定阴影的大小和顺序，是Material Design一个非常重要的特性。
Material Design主题中，除了X、Y外，还引入了Z值的概念，因为Z=elevation+translationZ，所以android:elevation也可以用来作为布局权重的设置。


### 获取状态栏的高度 ###

方法一（反射）：
	
	private int getStatusBarHeight(Context context) {
        int stateHeight = 0;
        // 反射R类
        try {
            // 指定目标地址
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            // 实例化
            Object object = clazz.newInstance();
            // 获取属性值
            String heightStr = clazz.getField("status_bar_height").get(object).toString();
            // 转换
            int height = Integer.parseInt(heightStr);
            // dp --- px
            stateHeight = context.getResources().getDimensionPixelOffset(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return stateHeight;
    }