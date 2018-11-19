# 自定义View #

## 自定义View的流程 ##


## 自定义View的方式 ##

-  直接继承

###  ###

## 自定义View的事件机制 ##

### View的事件机制流程 ###
dispatchTouchEvent(MotionEvent ev) ->onInterceptTouchEvent(MotionEvent ev)(仅ViewGroup存在)-> onTouchEvent(MotionEvent event) 

### Touch触摸事件 ###
- 点击(onClick)
- 长按(onLongClick)
- 拖拽(onDrag)
- 滑动(onScroll)

### Touch操作状态 ###

- 按下(ACTION_DOWN)
- 移动(ACTION_MOVE)
- 抬起(ACTION_UP)
- 取消手势(ACTION_CANCEL)
- 划出屏幕(ACTION_OUTSIDE)

### Android触摸事件流程总结 ###

- 一个事件序列从手指触摸屏幕开始，到触摸结束。同一事件序列是以 ACTION_DOWN开始，中间有数量不定的ACTION_MOVE事件，最终以ACTION_UP结束
- 事件传递顺序是：Activity——>Window——>View；最后顶级View接收到事件后，就会按照事件分发机制去分发事件
- 事件传递过程是由外向内的，即事件总是有父元素分发给子元素



### Android冲突案例 ###

#### ScrollView里嵌套ViewPager ####

- 滑动方向不同之以ScrollView与ViewPager为例的外部解决法
	举例子：以ScrollView与ViewPager为例
从 父View 着手，重写 onInterceptTouchEvent 方法，在 父View 需要拦截的时候拦截，不要的时候返回false，代码大概如下

	
		public class MyScrollView extends ScrollView {

    	public MyScrollView(Context context) {
        	super(context);
    	}

    	public MyScrollView(Context context, AttributeSet attrs) {
        	super(context, attrs);
    	}

   	 	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        	super(context, attrs, defStyleAttr);
    	}

    	@TargetApi(21)
    	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        	super(context, attrs, defStyleAttr, defStyleRes);
    	}

    	private float mDownPosX = 0;
    	private float mDownPosY = 0;
    	@Override
    	public boolean onInterceptTouchEvent(MotionEvent ev) {
        	final float x = ev.getX();
        	final float y = ev.getY();
        	final int action = ev.getAction();
        	switch (action) {
            	case MotionEvent.ACTION_DOWN:
                	mDownPosX = x;
                	mDownPosY = y;
                	break;
            	case MotionEvent.ACTION_MOVE:
                	final float deltaX = Math.abs(x - mDownPosX);
                	final float deltaY = Math.abs(y - mDownPosY);
                	// 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                	if (deltaX > deltaY) {
                    	return false;
                	}
        	}
        	return super.onInterceptTouchEvent(ev);
    	}}


- 滑动方向不同之以ScrollView与ViewPager为例的内部解决法

从子View着手，父View 先不要拦截任何事件，所有的 事件传递给 子View，如果 子View 需要此事件就消费掉，不需要此事件的话就交给 父View 处理。
实现思路 如下，重写 子View 的 dispatchTouchEvent 方法，在 Action_down 动作中通过方法 requestDisallowInterceptTouchEvent（true） 先请求 父View 不要拦截事件，这样保证 子View 能够接受到Action_move事件，再在Action_move动作中根据 自己的逻辑是否要拦截事件，不要的话再交给 父View 处理

	public class MyViewPager extends ViewPager {

    private static final String TAG = "yc";

    int lastX = -1;
    int lastY = -1;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                Log.i(TAG, "dealtX:=" + dealtX);
                Log.i(TAG, "dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
	}


#### ViewPager嵌套ViewPager ####
	
	从 子View ViewPager着手，重写 子View 的 dispatchTouchEvent方法，在 子View 需要拦截的时候进行拦截，否则交给 父View 处理，代码如下

	public class ChildViewPager extends ViewPager {

    private static final String TAG = "yc";
    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int curPosition;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                curPosition = this.getCurrentItem();
                int count = this.getAdapter().getCount();
                Log.i(TAG, "curPosition:=" +curPosition);
                // 当当前页面在最后一页和第0页的时候，由父亲拦截触摸事件
                if (curPosition == count - 1|| curPosition==0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {//其他情况，由孩子拦截触摸事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
        }
        return super.dispatchTouchEvent(ev);
    }
	}
	

#### ScrollView和RecycleView ####

	public class RecyclerScrollview extends ScrollView {

    private int downY;
    private int mTouchSlop;

    public RecyclerScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public RecyclerScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public RecyclerScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

	}

	注意：RecycleView一定要被嵌套里面
	<!-- descendantFocusability该属性是当一个为view获取焦点时，定义viewGroup和其子控件两者之间的关系。
	beforeDescendants：viewgroup会优先其子类控件而获取到焦点
	afterDescendants：viewgroup只有当其子类控件不需要获取焦点时才获取焦点
	blocksDescendants：viewgroup会覆盖子类控件而直接获得焦点-->

	<RelativeLayout
     	android:layout_width="match_parent"
     	android:layout_height="wrap_content"
     	android:descendantFocusability="blocksDescendants">
	<android.support.v7.widget.RecyclerView
    	android:id="@+id/recyclerView"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent"/>
	WW</RelativeLayout>


## Canvas相关技巧 ##

### Text的绘制技巧  ###

#### 垂直Text算法  ####

	Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
	int startY =Rect.height/ 2 - fm.descent + (fm.bottom - fm.top) / 2;


Rect.height：字符占矩阵的高度

startY：得到text开始绘制的垂直方向上的位置



#### 换行绘制Text  ####

	canvas.save();
    //getWidth()表示绘制多宽后换行
    StaticLayout sl = new StaticLayout(mText,mTextPaint,getWidth()/2, Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,true);
    //从0,0开始绘制
    canvas.translate(mXCenter - mTxtWidth / 2,mYCenter + mTxtHeight / 4);
    sl.draw(canvas);
	canvas.restore();

mText：表示绘制的text

mTextPaint:text的画笔

getWidth()/2：参数三表示换行的宽度

canvas.translate(dx,dy)：表示绘制text的起点




## 滑动事件专题 ##

### Touch事件之一 触摸及手势识别GestureDetectorCompat ###

[https://www.jianshu.com/p/33a9ace2d154](https://www.jianshu.com/p/33a9ace2d154)



## 计算相关相关技巧 ##

### 避免获取高度宽度为0的问题 ###

#### 方法一- addOnGlobalLayoutListener####
	
	view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    	@Override
    	public void onGlobalLayout() {
        	if (Build.VERSION.SDK_INT >= 16) {
           	 view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        	}else {
           	 view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
       		}
        	int width = view.getWidth();
    	}
	});
	
ViewTreeObserver，顾名思义，视图树的观察者，可以监听 View 的全局变化事件。比如，layout 变化，draw 事件等。可以阅读源码了解更多事件。


#### 方法二- addOnPreDrawListener####

	view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
    	@Override
    	public boolean onPreDraw() {
        	view.getViewTreeObserver().removeOnPreDrawListener(this);
        	int width = view.getWidth();
        	return false;
   	 	}
	});


#### 方法三- view.post() ####

	view.post(new Runnable() {
   	 	@Override
    	public void run() {
        	int width = view.getWidth();
    	}
	});

利用 Handler 通信机制，添加一个 Runnable 到 message queue 中，当 view layout 处理完成时，自动发送消息，通知 UI 线程。借此机制，巧妙获取 View 的宽高属性。代码简洁，使用简单，相比 ViewTreeObserver 监听处理，还不需要手动移除观察者监听事件。


#### 方法四- onLayout() ####

	
	view = new View(this) {
    	@Override
    	protected void onLayout(boolean changed, int l, int t, int r, int b) {
        	super.onLayout(changed, l, t, r, b);
        	int = view.getWidth(); 
    	}
	};

利用 View 绘制过程中的 onLayout() 方法获取宽高值，这也是为一个不需要借助其他类或者方法，仅靠自己就能完成获取宽高属性的手段。但是局限性在于，在 Activity 中使用代码创建 View 的场景较少，一般都是获取 layout 文件所加载 View 的宽高。

#### 方法五- ViewCompat.isLaidOut(view) ###

	if (ViewCompat.isLaidOut(view)) {
    	int width = view.getWidth();
	}

严格意义上来讲，这不能作为一个获取宽高的方式之一。充其量只能是一个判断条件。只有当 View 至少经历过一次 layout 时，isLaidOut() 方法才能返回 true，继而才能获取到 View 的真实宽高。所以，当我们的代码中有多次调用获取宽高时，才有可能使用这个方法判断处理。
