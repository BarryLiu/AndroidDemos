#自定义View
##onDraw
- 用于绘制控件
- canvas的用法

1、填充
public void drawARGB(int a, int r, int g, int b)
 public void drawColor(int color)
 public void drawRGB(int r, int g, int b) 
public void drawColor(int color, PorterDuff.Mode mode)
2、绘制几何图像
canvas.drawArc （扇形）
canvas.drawCircle（圆）
canvas.drawOval（椭圆）
canvas.drawLine（线）
canvas.drawPoint（点）
canvas.drawRect（矩形）
canvas.drawRoundRect（圆角矩形）
canvas.drawVertices（顶点）
cnavas.drawPath（路径）
3、绘制图片
canvas.drawBitmap （位图）
canvas.drawPicture （图片）
4、文本
canvas.drawText

- Paint的用法

mPaint.setColor(Color.RED);//笔的颜色
mPaint.setAntiAlias(true);//是否反锯齿
mPaint.setStyle(Style.STROKE);//描边
mPaint.setTextSize(25);//文字的大小
//设置字体
mPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/katong.TTF"));

- Path的用法
path.moveTo(0, 0); //设置路径的起点
path.lineTo(110, 20);//设置直线节点
path.cubicTo(0, 0, 20, 40, 200, 300);//设置曲线节点
canvas.drawPath(path, mPaint);//使用路径绘制

- cavan的裁剪

    	//将canvas进行保存
        canvas.save();
        //设置裁剪的路径
        Path path = new Path();
        //设置圆心得位置是图片的中心
        path.addCircle(bm.getWidth()/2,
                bm.getHeight()/2,
                bm.getWidth()/2,//半径
                Path.Direction.CCW
                );
        canvas.clipPath(path);

        //绘制图片
        canvas.drawBitmap(bm,0,0,null);

> 使用软件加速

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);//使用软件加速


>  在Activity中设置强制横屏
android:screenOrientation="landscape"

STROKE:描边
FILL:填充
Typeface:文字对象
##onMeasure
- 测量
###三种测量模式
- 测量模式就是针对于layout_width和layout_heigth的中数值
- 精确测量: matchParent,具体数值
- AT_MOST:取最大值,wrapcontent
- UNSPECIFIED:不指定大小:0dp
- 就是实现onMeasure在wrapcontent时候控件的大小

     	//获取当前的测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //更具测量模式进行测算
        int width; //测量之后的数值
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize;
        } else {//wrap_content
            width = bm.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            height = heightSize;
        } else {//wrap_content
            height = bm.getHeight();
        }

        //设置测量的结果
        setMeasuredDimension(width, height);

##onLayout
- 用于实现布局的规则

    	//在onLayout方法中设置子元素的位置
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//遍历所有的子元素，设置其位置
		for (int i = 0; i < getChildCount(); i++)  
       	 { 
	      childView.layout(cl,//左边距离父类容器左边的距离
                          ct, //上面距离父容器上面的位置
                          cr, //右边距离父容器右边的距离
                          cb); //下面距离父容器上面的距离
	｝
｝



##onTouchEvent
- 响应触摸事件
