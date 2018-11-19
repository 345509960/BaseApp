

# Recyclerview是使用和问题总结 #


## Recyclerview简述和兼容包 ##

Recyclerview是Android官方5.0以后提出的一个新Widgets，以解决ListView、GridView在复用优化时需要编写大量冗余代码的问题。

使用前需使用版本兼容包：

	com.android.support:recyclerview-v7:${supportVersion}	

## 为什么Recyclerview比ListView、GridView更好用 ##

优点

- Recyclerview专注于布局的复用机制，而不是专注于布局本身。
- 一个Recyclerview组件通过改变LayoutManager就能实现ListView、GridView、瀑布流三种展示效果
- 轻量级实现水平滑动展示效果
- Recyclerview扩展性更好、更灵活

缺点

- 没有实现onItemClick点击事件，需手动实现

## Recyclerview的基础概念 ##

RecyclerView.Adapter：数据适配器，包装数据集合并且为每个条目创建视图

RecyclerView.ViewHolder：复用View的对象，保存用于显示每个数据条目的子View

RecyclerView.LayoutManager：布局管理器，控制RecyclerView水平展示、网格展示、瀑布三种效果的切换

RecyclerView.ItemDecoration：子项间隔的扩展

RecyclerView.ItemAnimator：控制添加、删除、重排序的动画效果（注意只有执行notifyItemInserted、notifyItemRemoved等操作才有效）


android:clipToPadding：默认为true,为true时RecyclerView的子View会包含padding重复计算，比较通俗的场景是为true间隔线会考虑RecyclerView的Padding、为false时不考虑

## Recyclerview的基础使用-ListView效果的实现 ##

样例参考于：[https://www.cnblogs.com/anni-qianqian/p/6587329.html](https://www.cnblogs.com/anni-qianqian/p/6587329.html "RecyclerView样例")

第一步-编写布局：
	
	<android.support.v7.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

第二步-编写Adapter：
	
	class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {	
		private List<String> mLists;
		
		HomeAdapter(List<String> lists){
			if(lists==null){
				lists=Arrlist();
			}
			mLists=lists;
		}		

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    HomeActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
	

第三步-编写R.layout.item_home复用布局

省略改操作.....

第四步-adapter和布局进行绑定
	
	 mRecyclerView = (RecyclerView) findViewById(R.id.rv);
     mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     mRecyclerView.setAdapter(mAdapter = new HomeAdapter(mDatas));

第五步-添加默认的间隔和默认的动画
	
	
	// 设置默认item动画
	mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	//添加默认的间隔
	mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
	DividerItemDecoration.VERTICAL_LIST));


## Recyclerview的基础使用-GridView效果的实现 ##

样例参考于：[https://www.cnblogs.com/anni-qianqian/p/6587329.html](https://www.cnblogs.com/anni-qianqian/p/6587329.html "RecyclerView样例")

Recyclerview的扩展性好在于，当我们需要从ListView切换到GridView、或者其他效果时，只需要很少的改动即可完成，比如我们需要把上面的代码改写成GridView

1、更改Adapter中onCreateViewHolder复用布局以适应网格效果展示

2、更改布局管理器为
		
		//this为上下文、4为横排展示的子Item数量
	    mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
3、更改间隔展示（请移步后面对间隔线的专题）

## Recyclerview的基础使用-水平滑动、瀑布的实现 ##

通过更改布局适配器为StaggeredGridLayoutManager我们能很简单的实现水平滑动

StaggeredGridLayoutManager具有两个参数：

- orientation 规定滑动的方向 StaggeredGridLayoutManager.VERTICAL垂直滑动 StaggeredGridLayoutManager.HORIZONTAL水平滑动
- spanCount 根据spanCount定义行列的个数，当orientation为StaggeredGridLayoutManager.VERTICAL时，定义列数

水平滑动效果：

	mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));

垂直滑动效果：
	
	mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
类似于
	
	mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
	
当我们需要实现瀑布流效果时，只需要Item布局不固定高度、就可以实现瀑布流效果。


## Recyclerview的ItemDecoration ##

### ItemDecoration简述 ###

Recyclerview.ItemDecoration作为抽象接口扩展了修饰 Recyclervie 展示的行为，常用于实现该类以达到添加子项的间隔。

当我们需要自定义间隔线的时候，只需要实现Recyclerview.ItemDecoration然后设置就可以显示间隔线，扩展性非常好，默认情况下没有间隔线。

### ItemDecoration的列表间隔实现类-DividerItemDecoration ###

我们先来看一下官方提供给我们的两个定义间隔线的实现类：
#### 构造器 ####

- DividerItemDecoration（Context context, int orientation）

DividerItemDecoration实现了Recyclerview.ItemDecoration完成对Recyclerview中的子项底部和右部的装饰，既当我们想要实现在子项底部添加间隔或者在右边添加间隔时，可以使用该实现类。

DividerItemDecoration的构造器有两个参数，第一个参数是上下文对象，第二个参数是定义的是间隔装饰在子项中的低部还是右边。

- DividerItemDecoration.HORIZONTAL 定义间隔在右边
- DividerItemDecoration.VERTICAL 定义间隔在底部

定义间隔在右边效果图：

![](https://i.imgur.com/Azq3doC.png)

定义间隔在底部效果图：

![](https://i.imgur.com/oBcuQyc.png)

#### 对外开发的方法 ####

- setOrientation(int orientation)：设置间隔的方向，既装饰的展示位置。
- setDrawable(@NonNull Drawable drawable)：设置展示的Drawable,默认为android.R.attr.listDivider

第一个方法，类似于构造器中的参数二。
第二个方法是为了自定义Drawable的展示效果的，真正开发过程中，android.R.attr.listDivider会根据不同的设备和Android系统版本展示不同的效果，为了进行兼容，我们非常有必要对间隔的样式进行统一的定制。

根据setDrawable方法自定义间隔样式的方法：

1、定义展示的xml:
	
	divide_test.xml
	
	<shape xmlns:android="http://schemas.android.com/apk/res/android"
    	xmlns:tools="http://schemas.android.com/tools"
    	tools:ignore="ResourceName"
    	android:shape="rectangle">
    	<gradient
        	android:angle="45"
        	android:endColor="@color/colorPrimary"
        	android:startColor="@color/colorAccent" />
    	<size android:height="4dp" android:width="4dp"/>
	</shape>

案例中，android:height定义间隔的高、android:width定义间隔的宽，是必须的，如果不设置将默认不显示，案例使用了渐变色，也可以定义纯色间隔线，一般使用#efefef，高度为1dp

2、第二步，将xml转化成Drawable并设置：
	
	val divice = ContextCompat.getDrawable(this@TestRvActivity, R.drawable.divide_test)
    if (divice != null) {
          dividerItemDecoration.setDrawable(divice)
    }


 也可以在Activity Theme中通过android:listDivider标签修改全局的间隔展示
	
	<item name="android:listDivider">@drawable/divice_test</item>


### DividerItemDecoration源码解析 ###

在分析DividerItemDecoration之前、我们可以先看Recyclerview.ItemDecoration抽象类的描述，Recyclerview.ItemDecoration其实就是定义了一个添加装饰的模型，通过用户扩展这个模型，在子项中添加额外的布局展示，Recyclerview.ItemDecoration主要为继承他的类实现了三个方法：

- public void onDraw(Canvas c, RecyclerView parent, State state)：绘制装饰视图方法，先于绘制子项视图之前执行，在子项视图之下。

c:画布

parent：子项的父RecyclerView

state：父RecyclerView当前状态


- public void onDrawOver(Canvas c, RecyclerView parent, State state)：绘制装饰视图方法，绘制子项视图之后执行，在子项视图之上。

参数类似onDraw()方法

- public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state):输出偏移量，以满足装饰的Drawable绘制。

outRect：输出left、top、right、bottom偏移量的Rect

view:Drawable装饰的子项View引用。

通过对Recyclerview.ItemDecoration的分析，我们总结出以下几点：

- onDraw、onDrawOver方法用来绘制装饰的展示效果

- onDraw方法执行在子项绘制之前、onDrawOver方法执行在子项绘制之后

- getItemOffsets回来定义上下左右子项的额外偏移量，以方便插入装饰的Drawable

以下是相关内容但是需要通过阅读Recyclerview得出的结论：

- getItemOffsets会在Measure子项的时候根据子项的多少执行N次
- onDraw、onDrawOver方法只根据Recyclerview中的多少每个Recyclerview.ItemDecoration只执行一次

分析了Recyclerview.ItemDecoration获得一些基础数据之后，我们回头来看看DividerItemDecoration的具体实现：
	
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

1、当mDivider == null成立时、不需要偏移量，四个方向都为0

2、当mOrientation == VERTICAL时，类型ListView的形式，需要根据mDivider的高度设置底部的长度，从这里我们可以知道，xml中的height和width属性是必须的

3、否则、会设置右边的偏移量，该种效果类似京东分类的左边的间隔线。


绘制装饰的展示效果是在onDraw方法中绘制，氛围VERTICAL和HORIZONTAL两种样式，因为两种样式实现方法类似，这里只讲解VERTICAL样式的实现过程：

		代码块一：
		if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

		代码块二：
		final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

上述两个代码块是从源码中Copy过来的，代码块一就是计算间隔线left、right的位置，计算方式分为是否考虑父类的Padding,如果考虑公式为：
	
	left=父Recyclerview的左内边距
	right=父Recyclerview的宽度-父Recyclerview的右内边距

代码二是计算装饰的top和bottom，因为onDraw只会执行一次，系统会把间隔线按照子项的数量逐个在Recyclerview视图中绘制出来，所以需要取出子项的数量然后遍历：

- 获取到子项的数量parent.getChildCount()
- 根据子项数量遍历
	- 根据子View获取到插入装饰View后的Rect，根据parent.getDecoratedBoundsWithMargins(child, mBounds)把数据填充到mBounds对象
	- 计算bottom=假如填充后的底部+Y轴上的偏移量
	- 计算top=bottom-填充物的高度
	- 设置mDivider的left、top、right、bttom
	- 绘制到画布中



### DividerGridItemDecoration源码解析和使用 ###

Recyclerview v7库中并没有对应网格显示的间隔线的实现类，庆幸的是洪洋大神编写了相关的DividerGridItemDecoration类，现在我们就对DividerGridItemDecoration类进行简单的解析：

解析之前我们先看看如果使用：

	DividerGridItemDecoration dividerGridItemDecoration =new DividerGridItemDecoration(mContext)
	Rv.addItemDecoration(dividerGridItemDecoration)
	
按照解析 DividerItemDecoration 的逻辑，我们先看getItemOffsets()方法：
	
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int itemPosition=((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        // 如果是最后一行，则不需要绘制底部
        if (isLastRaw(parent, itemPosition, spanCount, childCount))
        {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
        // 如果是最后一列，则不需要绘制右边
        else if (isLastColum(parent, itemPosition, spanCount, childCount))
        {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                    mDivider.getIntrinsicHeight());
        }
    }

综上分析 getItemOffsets完成以下逻辑：

- 判断是否最后一行、是否最后一列
- 最后一行只展示右边间隔
- 最后一列只展示底部间隔
- 其他子项底部和右边间隔都展示

其他知识点类似，主要讲解如果判断是否是最后一行、是否是最后一列
	
	最后一行
	
	private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //childCount - childCount % spanCount计算出饱和的最大position 如果大于这个position说明在最后一行
            childCount = childCount - childCount % spanCount;
            return pos >= childCount;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                return pos >= childCount;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                return (pos + 1) % spanCount == 0;
            }
        }
        return false;
    }

	
	最后一列：

	 private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (pos + 1) % spanCount == 0;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return (pos + 1) % spanCount == 0;
            } else {
                childCount = childCount - childCount % spanCount;
                return pos >= childCount;
            }
        }
        return false;
    }


查看两段代码块，我们可以总结出一个算法：

判断是否最后一行时，如果是垂直滚动使用itemPosition>=childCount - childCount % spanCount,如果是水平滚动(itemPosition + 1) % spanCount == 0,判断最后一列反之。

其中childCount - childCount % spanCount计算出了倒数第二行最后一个Position,那么如果当前itemPositin>=该值就是最后一行，水平滚动时，StaggeredGridLayoutManager中的spanCount代表行数，所以 (itemPositin + 1) % spanCount == 0成立时是最后一行


### 使用Recyclerview.ItemDecoration 实现炫酷吸顶效果 ###

源码位置为：

![](https://i.imgur.com/ieQ0Hio.gif)

gif是网上抄录的，吸顶效果就是这样，这样的效果我们可以分为一下几种情况：

- 显示的主体是根据分组显示组名Text，并定义显示背景
- 当组子项向上滑动部分子项隐藏、下一个组还没有到来之前，显示组名固定到顶部
- 当下一个组到来，需要接替替代上一个组的显示浮层

当我们需要绘制Draw之前，我们需要给子项添加间隔，重写getItemOffsets方法：
	
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);

        String groupId = mDataList.get(pos).getSuctionTopDecorationGroup();
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mHeight;
        } else {
            outRect.top = 0;
        }
    }

mHeight是我们定义的额外添加浮层的高度，在如下情况我们需要在顶部添加间隔：

- position为第一个，索引为0

- 是同一组的第一个子项

- 除此之外、因为如果组名为空的情况下不需要绘制，所以如果组名为空，不管是否符合以上两种情况都不需要配置间隔

判断当前position是否是组内的第一个子项，只需要取出之前的组名和当前的组名进行判断，如果相等既不符合条件，反之符合：
	
	String prevGroupName = mDataList.get(pos - 1).getSuctionTopDecorationGroup();
    String groupName = mDataList.get(pos).getSuctionTopDecorationGroup();
    //判断前一个字符串 与 当前字符串 是否相同
    if (prevGroupName.equals(groupName)) {
        return false;
    } else {
        return true;
     }
	
	

重写间隔方法后，我们需要重写onDarwOver方法，在onDarw和onDrawOver的选择取可以基于以下条件看重写那个方法：是否需要覆盖子项Item的显示，浮层在子项之上，如果需要，就使用onDrawOve，反之onDarw.

	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
		
		代码块一：
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        String preGroupId = "";
        String groupId = "";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
			
			代码块二：
            preGroupId = groupId;
            groupId = mDataList.get(position).getSuctionTopDecorationGroup();
            if (TextUtils.isEmpty(groupId) || groupId.equals(preGroupId)) {
                continue;
            }
			
			代码块三：
            String text = mDataList.get(position).getSuctionTopDecorationGroup().toUpperCase();
            int viewBottom = view.getBottom();
            float textY = Math.max(mHeight, view.getTop());

			代码块四：
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextGroupId = mDataList.get(position + 1).getSuctionTopDecorationGroup();
                //组内最后一个view进入了header
                if (!nextGroupId.equals(groupId) && viewBottom < textY) {
                    textY = viewBottom;
                }
            }
            //textY - mHeight决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - mHeight, right, textY, mBgPaint);

            //解决高度绘制不居中
            Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
            int startY = (int) (textY - mHeight / 2 - fm.descent + (fm.bottom - fm.top) / 2);

            c.drawText(text, left + mAlignLeft, startY, mTextPaint);
        }
    }

分析如下：
最终我们需要绘制一个矩阵的背景和绘制Text:

绘制矩阵，我们需要求出letf、top、right、bottom四个位置

绘制text，需要求出x、y方向上开始绘制内容的位置
	
- 代码块一、中RecyclerView.State.getItemCount()和RecyclerView.getChildCount()位置的区别在于，RecyclerView.getChildCount()提供的是屏幕所能显示区域的子项数量，并且我们只需要重绘显示的区域，所以我们只需要考虑遍历绘制显示区域中的子项、left、right等于父类。
-  代码块二中 是判断是否是组项中的第一个项，如果不是不需要绘制展示效果
-  代码四三、是计算绘制矩阵的底部，这里有两种情况
	- 组项不是第一个显示组的情况：bottom=view.getTop()
	- 组项为第一个显示组的情况时，向上滚动，假设第一个组项的子项还没有完全离开屏幕，我们需要装饰的矩阵以mHeight固定显示于顶部,如果子项都没有离开屏幕，跟第一种情况类似，因为随着屏幕向上滑动，第一个组项的第一子项的view.getTop()会越来越小，正常情况等于mHeight，如果少于，则说明该子项开始被压迫装饰的矩阵，基于此得到bottom=Max(mHeight,view.getTop())这个计算装饰矩阵底部的公式
- 得到底部后，计算top就简单了，公式为top=bottom-mHeight
- 代码四：吸顶效果还有一个动态效果就是，当下一个组项装饰矩阵的浮层移动到当前组项装饰矩阵的浮层bottom时，形成下一个矩阵把上一个矩阵推出屏幕的效果，如代码四，先判断下一个数据是否存在，如果这已经是最后一个数据了防止下标越界，不考虑这种情况，取出下一个组名后进行判断，如果不相等并且viewBottom<textY的情况，说明当前处于上一个组项最后一个子项和下一个组项的第一个子项交界处，让当前组项的矩阵随着viewBottom波动。



	
### 使用Recyclerview.ItemDecoration 扩展类ItemTouchHelper 实现item的拖拽，平移等操作 ###

源代码交互

ItemTouchHelper实现了Recyclerview.ItemDecoration,用于对数据的拖拽、左右侧滑的交互效果。


Android Api中，实现了ItemTouchHelper类以方便，我们进行对Recyclerview的拖拽、左右侧滑效果，在使用ItemTouchHelper时，我们要实现ItemTouchHelper.Callback回调，来对数据进行处理。

首先我们先介绍ItemTouchHelper.Callback一些常用的默认实现：
	
	public boolean isLongPressDragEnabled() {
        return true;
    }

是否支持Item长按拖拽，true长按后可以拖拽、false长按后不能使用拖拽。

	@Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

是否支持左右滑动删除效果，true表示支持，false表示不支持

	 public float getSwipeThreshold(ViewHolder viewHolder) {
            return .5f;
     }
左右滑动因子，默认为.5f

	public float getMoveThreshold(ViewHolder viewHolder) {
            return .5f;
        }

移动因子，默认为.5f

下面是一些完成功能必须实现的会回调方法：

	public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //注意：和拖曳的区别就是在这里
            int swipeFlags = ItemTouchHelper.START ;
	//            | ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags);
        }

    }
这个方法是用来定义拖拽和左右滑动的方向。比如，假设当前是使用的是GridLayoutManager布局，那么我们需要完成上下移动和左右移动，可以使用一下标签：
	
	int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

GridLayoutManager布局不支持左右滑动删除 就设置为0，swipeFlags标签有两个定义ItemTouchHelper.START表示支持左滑删除、ItemTouchHelper.END支持右滑删除。设置好dragFlags、swipeFlags后通过makeMovementFlags(dragFlags, swipeFlags)方法设置到后台计算。

	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) 

方法会在长按进入拖拽后，不断进行调用。适合做数据替换的操作：

	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //拖动的 item 的下标
        int fromPosition = viewHolder.getAdapterPosition();
        //目标 item 的下标，目标 item 就是当拖曳过程中，不断和拖动的 item 做位置交换的条目。
        int toPosition = target.getAdapterPosition();
        //对应某些需求，某一个item不能拖拽
        if (toPosition == 0) {
            return false;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                //通过你传入的adapter得到你的数据 并进行交换
                Collections.swap(adapter.getList(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(adapter.getList(), i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

当进行左右滑动删除时，回调以下方法，请做一些数据删除操作：

	 public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) 

实现案例如下：
	
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        adapter.notifyItemRemoved(adapterPosition);
        adapter.getList().remove(adapterPosition);
    }

当我们选择拖拽时，需要对布局进行一些更换，达到高亮的效果：
	
	public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)

实现案例如下：
	
	public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //给被拖曳的 item 设置一个深颜色背景
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

完成拖拽时恢复布局样式：
	
	public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)

### ItemTouchHelper的源码解析 ###
	

## 常见错误 ##

1、ScrollView中Recyclerview高度显示不全

现阶段有两种方案可以解决这个问题

 -  自定义LinearLayoutManager（不推荐）
 -  直接在在Recyclerview添加配置（推荐）

这里我就不是第一种 ，直接说第二种：
	
	 //是否启动嵌套滚动
	 rv.isNestedScrollingEnabled = false
     rv.setHasFixedSize(true)

但是有时候就算是使用上列两种方法中的一种后，依然无法修复这个BUG，这就需要在Recyclerview布局外再做处理
	
	<RelativeLayout
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:descendantFocusability="blocksDescendants">

           <android.support.v7.widget.RecyclerView
               android:id="@+id/rv_measure_result"
               android:layout_width="match_parent"
               android:layout_height="wrap_content"
               android:background="@color/white" />

     </RelativeLayout>

既在Recyclerview外添加RelativeLayout布局，并且添加**android:descendantFocusability="blocksDescendants"**以获取到焦点。

2、ScrollView中Recyclerview不允许其滚动，以解决两个View垂直滚动导致的冲突问题

	 rv.layoutManager = object : LinearLayoutManager(this@MeasureResultActivity) {
            //解决RecyclerView嵌套RecyclerView滑动卡顿的问题
            //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
            override fun canScrollVertically() = false
      }