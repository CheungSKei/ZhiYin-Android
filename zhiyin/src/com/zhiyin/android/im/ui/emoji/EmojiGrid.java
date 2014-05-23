package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.ScreenUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Emoji GridView控件 用于显示Emoji图标的九宫格
 * 
 * @version 1.0.0
 * @date 2014-05-19
 * @author S.Kei.Cheung
 */
public class EmojiGrid extends GridView implements AdapterView.OnItemClickListener{

	private Context mContext;
	// Emoji类型: 20:普通Emoji,25 gif类贴图, 23 控件
	private int mEmojiType = 20;
	// Gridview Adapter
	public EmojiGridAdapter mEmojiGridAdapter;
	// 单个Emoji宽度
	private int mItemWidthInPix;
	// 当前页
	private int mCurPage;
	// 每页Emoji个数
	private int mItemsPerPage;
	// 总页数
	private int mTotalPage;
	// 列数
	private int mNumColumns;
	
	public EmojiGrid(Context context)
	{
		super(context);
		this.mContext = context;
		initData();
	}
	
	public EmojiGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initData();
	}
	
	/**
	 * 设置列宽
	 */
	private void setEmojiColumnWidth()
	{
		switch (this.mEmojiType)
		{
			case 25:
				this.mItemWidthInPix = ScreenUtils.fromDPToPix(43);
				break;
			default:
				this.mItemWidthInPix = ScreenUtils.fromDPToPix(80);
				break;
		}
		
		setColumnWidth(this.mItemWidthInPix);
	}
	
	/**
	 * 初始化
	 */
	private void initData()
	{
		this.mEmojiGridAdapter = new EmojiGridAdapter(mContext,this.mItemsPerPage,this.mCurPage);
		setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundResource(0);
		setStretchMode(STRETCH_COLUMN_WIDTH);
		setEmojiColumnWidth();
		setAdapter(this.mEmojiGridAdapter);
		setOnItemClickListener(this);
		int paddingLeft = ScreenUtils.fromDPToPix(6);
		int paddingRight = ScreenUtils.fromDPToPix(6);
		int paddingTop = ScreenUtils.fromDPToPix(6);
		DebugUtils.debug("MicroMsg.SmileyGrid", "mItemWidthInPix:%d", new Integer[]{this.mItemWidthInPix});
		DebugUtils.debug("MicroMsg.SmileyGrid", "paddingLeft:%d,paddingRight:%d,paddingTop:%d",new Integer[]{paddingLeft,paddingRight,paddingTop});
		setPadding(paddingLeft, paddingRight, paddingTop, 0);
		
		// 通知预加载Emoji
		if (this.mEmojiGridAdapter != null) {
			//	this.mEmojiGridAdapter.arl();
		}
	}
	
	/**
	 * 设置当前页数据
	 * @param type			Emoji类型
	 * @param curPage		当前页码
	 * @param itemsPerPage	Emoji个数
	 * @param totalPage		总页数
	 * @param numColumns	总列数
	 */
	public final void setPageInfo(int type, int curPage, int itemsPerPage, int totalPage, int numColumns)
	{
		DebugUtils.info("MicroMsg.SmileyGrid", "type:" + type + " itemsPerPage:" + itemsPerPage + " totalPage:" + totalPage + " curPage:" + this.mCurPage);
		this.mEmojiType = type;
		this.mCurPage = curPage;
		this.mItemsPerPage = itemsPerPage;
		this.mTotalPage = totalPage;
		this.mNumColumns = numColumns;
		setEmojiColumnWidth();
		setNumColumns(numColumns);
		this.mEmojiGridAdapter.update();
	}
	
	/**
	 * 设置当前页数据
	 * @param type			Emoji类型
	 * @param curPage		当前页码
	 * @param itemsPerPage	Emoji个数
	 * @param totalPage		总页数
	 * @param numColumns	总列数
	 * @param gridViewWidth GridView总宽度
	 */
	public final void setPageInfo(int type, int curPage, int itemsPerPage, int totalPage, int numColumns, int gridViewWidth)
	{
		DebugUtils.info("MicroMsg.SmileyGrid", "type:" + type + " itemsPerPage:" + itemsPerPage + " totalPage:" + totalPage + " curPage:" + this.mCurPage);
		this.mEmojiType = type;
		this.mCurPage = curPage;
		this.mItemsPerPage = itemsPerPage;
		this.mTotalPage = totalPage;
		this.mNumColumns = numColumns;
		if (this.mNumColumns == 7) {
			setColumnWidth(gridViewWidth / 7);
		}else{
			setColumnWidth(gridViewWidth / 14);
		}
		
		DebugUtils.debug("MicroMsg.SmileyGrid", "gridViewWidth:%d", new Integer[]{gridViewWidth});
		setNumColumns(numColumns);
		// 更新Emoji
		this.mEmojiGridAdapter.update();
	}
	
	public int getTotalPage(){
		return this.mTotalPage;
	}
	
	/**
	 * 返回Emoji类型
	 * @return
	 */
	public final int getEmojitype()
	{
		return this.mEmojiType;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO:通过点击相应Emoji和位置,通过c.java得到相应资源
		
	}

}
