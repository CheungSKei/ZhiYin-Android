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
 * Emoji GridView�ؼ� ������ʾEmojiͼ��ľŹ���
 * 
 * @version 1.0.0
 * @date 2014-05-19
 * @author S.Kei.Cheung
 */
public class EmojiGrid extends GridView implements AdapterView.OnItemClickListener{

	private Context mContext;
	// Emoji����: 20:��ͨEmoji,25 gif����ͼ, 23 �ؼ�
	private int mEmojiType = 20;
	// Gridview Adapter
	public EmojiGridAdapter mEmojiGridAdapter;
	// ����Emoji���
	private int mItemWidthInPix;
	// ��ǰҳ
	private int mCurPage;
	// ÿҳEmoji����
	private int mItemsPerPage;
	// ��ҳ��
	private int mTotalPage;
	// ����
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
	 * �����п�
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
	 * ��ʼ��
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
		
		// ֪ͨԤ����Emoji
		if (this.mEmojiGridAdapter != null) {
			//	this.mEmojiGridAdapter.arl();
		}
	}
	
	/**
	 * ���õ�ǰҳ����
	 * @param type			Emoji����
	 * @param curPage		��ǰҳ��
	 * @param itemsPerPage	Emoji����
	 * @param totalPage		��ҳ��
	 * @param numColumns	������
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
	 * ���õ�ǰҳ����
	 * @param type			Emoji����
	 * @param curPage		��ǰҳ��
	 * @param itemsPerPage	Emoji����
	 * @param totalPage		��ҳ��
	 * @param numColumns	������
	 * @param gridViewWidth GridView�ܿ��
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
		// ����Emoji
		this.mEmojiGridAdapter.update();
	}
	
	public int getTotalPage(){
		return this.mTotalPage;
	}
	
	/**
	 * ����Emoji����
	 * @return
	 */
	public final int getEmojitype()
	{
		return this.mEmojiType;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO:ͨ�������ӦEmoji��λ��,ͨ��c.java�õ���Ӧ��Դ
		
	}

}
