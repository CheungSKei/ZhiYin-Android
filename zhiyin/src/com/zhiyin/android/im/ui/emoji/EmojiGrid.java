package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyin.android.im.R;
import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.ScreenUtils;

/**
 * Emoji GridView�ؼ� ������ʾEmojiͼ��ľŹ���
 * 
 * @version 1.0.0
 * @date 2014-05-19
 * @author S.Kei.Cheung
 */
public class EmojiGrid extends GridView implements AdapterView.OnItemClickListener{

	private final String TAG = "MicroMsg.EmojiGrid";
	
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
	// Emoji����
	private int mEmojiTotal;
	
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
	 * ����verticalSpacing
	 * @param verticalSpacing
	 */
	public final void setVerticalSpacing(int verticalSpacing)
	{
		if (verticalSpacing <= 0) {
			return;
		}
		
		setPadding(ScreenUtils.fromDPToPix(6), verticalSpacing, ScreenUtils.fromDPToPix(6), 0);
		
		setVerticalSpacing(verticalSpacing / 2);
	}
	
	/**
	 * ��ʼ��
	 */
	private void initData()
	{
		this.mEmojiGridAdapter = new EmojiGridAdapter();
		setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundResource(0);
		setStretchMode(STRETCH_COLUMN_WIDTH);
		setEmojiColumnWidth();
		setAdapter(this.mEmojiGridAdapter);
		setOnItemClickListener(this);
		int paddingLeft = ScreenUtils.fromDPToPix(6);
		int paddingRight = ScreenUtils.fromDPToPix(6);
		int paddingTop = ScreenUtils.fromDPToPix(6);
		DebugUtils.debug(TAG, "mItemWidthInPix:%d", new Integer[]{this.mItemWidthInPix});
		DebugUtils.debug(TAG, "paddingLeft:%d,paddingRight:%d,paddingTop:%d",new Integer[]{paddingLeft,paddingRight,paddingTop});
		setPadding(paddingLeft, paddingRight, paddingTop, 0);
		
		// ֪ͨԤ����Emoji
		if (this.mEmojiGridAdapter != null) {
//			this.mEmojiGridAdapter.arl();
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
	public final void setPageInfo(int type, int curPage, int emojiTotal, int itemsPerPage, int totalPage, int numColumns)
	{
		DebugUtils.info(TAG, "type:" + type + " emojiTotal:"+ emojiTotal + " itemsPerPage:" + itemsPerPage + " totalPage:" + totalPage + " curPage:" + this.mCurPage);
		this.mEmojiType = type;
		this.mEmojiTotal = emojiTotal;
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
	public final void setPageInfo(int type, int curPage,int emojiTotal, int itemsPerPage, int totalPage, int numColumns, int gridViewWidth)
	{
		DebugUtils.info(TAG, "type:" + type + " emojiTotal:"+ emojiTotal + " itemsPerPage:" + itemsPerPage + " totalPage:" + totalPage + " curPage:" + this.mCurPage);
		this.mEmojiType = type;
		this.mEmojiTotal = emojiTotal;
		this.mCurPage = curPage;
		this.mItemsPerPage = itemsPerPage;
		this.mTotalPage = totalPage;
		this.mNumColumns = numColumns;
		if (this.mNumColumns == 7) {
			setColumnWidth(gridViewWidth / 7);
		}else{
			setColumnWidth(gridViewWidth / 14);
		}
		
		DebugUtils.debug(TAG, "gridViewWidth:%d", new Integer[]{gridViewWidth});
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

	/**
	 * Emoji ������
	 * 
	 * @version 1.0.0
	 * @date 2014-05-19
	 * @author S.Kei.Cheung
	 */
	public class EmojiGridAdapter extends BaseAdapter {

		public EmojiGridAdapter(){
			super();
		}
		
		@Override
		public int getCount() {
			int count = mItemsPerPage;
			if (mEmojiType == 20) {
				count = mItemsPerPage;
				return count;
			}
			
			if (mEmojiType == 23 || mEmojiType == 25)
			{
//				if (this.fiV.dIo == -1 + SmileyGrid.g(this.fiV))
//				{
//					if (SmileyGrid.d(this.fiV) - this.fiV.dIo * this.fiV.fiL == -1) {
//						count = 0;
//					} else {
//						count = SmileyGrid.d(this.fiV) - this.fiV.dIo * this.fiV.fiL;
//					}
//				}
//				else {
//					count = mItemsPerPage;
//				}
			}
			else {
				count = mItemsPerPage;
			}
			return count;
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO:΢��smiley������:ͨ��smileyGrid��ʼ��type:25��23��20
			ViewHolder holder = null;
			if (convertView == null) {
				 holder=new ViewHolder();
				 if(mEmojiType == 25){
					 convertView = View.inflate(mContext,R.layout.smiley_grid_item_l, null);
					 holder.smiley_grid_item_bg = convertView.findViewById(R.id.smiley_grid_item_bg);
					 holder.art_emoji_icon_iv = (ImageView)convertView.findViewById(R.id.art_emoji_icon_iv);
					 holder.art_emoji_icon_text = (TextView)convertView.findViewById(R.id.art_emoji_icon_text);
					 holder.art_emoji_del_tv = (TextView)convertView.findViewById(R.id.art_emoji_del_tv);
					 holder.art_emoji_new_tv = (TextView)convertView.findViewById(R.id.art_emoji_new_tv);
					 holder.art_emoji_icon_mask = convertView.findViewById(R.id.art_emoji_icon_mask);
					 holder.smiley_grid_control_btn = (ImageView)convertView.findViewById(R.id.smiley_grid_control_btn);
					 holder.smiley_grid_control_area = convertView.findViewById(R.id.smiley_grid_control_area);
					 holder.art_emoji_icon_iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
				 }else if(mEmojiType == 20 || mEmojiType == 23){
					 convertView = View.inflate(mContext, R.layout.smiley_grid_item_s, null);
					 holder.art_emoji_icon_iv = (ImageView)convertView.findViewById(R.id.art_emoji_icon_iv);
					 holder.art_emoji_icon_iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				 }
				 convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.art_emoji_icon_iv.setImageDrawable(null);
			if(mEmojiType == 25){
				holder.smiley_grid_control_area.setVisibility(View.GONE);
				holder.art_emoji_icon_text.setVisibility(View.VISIBLE);
				if (position == 0)
				{
					holder.smiley_grid_control_area.setVisibility(View.VISIBLE);
					holder.smiley_grid_control_btn.setBackgroundResource(R.drawable.app_panel_setting_icon);
					holder.smiley_grid_item_bg.setBackgroundDrawable(null);
				}else{
					// ɾ����ť
					holder.smiley_grid_item_bg.setBackgroundResource(R.drawable.smiley_item_bg2);
					
					if (position == getCount()-1)
					{
						holder.art_emoji_icon_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.del_btn));
					}else
					{
						holder.art_emoji_icon_iv.setImageDrawable(EmojiResourceReChange.getSmileyDrawable(mContext,position + (-1 + mItemsPerPage) * mCurPage));
					}
				}
			}else if(mEmojiType == 20 || mEmojiType == 23){
				if (position == getCount()-1)
				{
					holder.art_emoji_icon_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.del_btn));
				}else
				{
					holder.art_emoji_icon_iv.setImageDrawable(EmojiResourceReChange.getSmileyDrawable(mContext,position + (-1 + mItemsPerPage) * mCurPage));
				}
			}
			return convertView;
		}
		
		public final void update()
		{
			super.notifyDataSetChanged();
		}

		private final class ViewHolder{
			ImageView art_emoji_icon_iv;
			TextView art_emoji_icon_text;
			View smiley_grid_item_bg;
			TextView art_emoji_del_tv;
			TextView art_emoji_new_tv;
			View art_emoji_icon_mask;
			ImageView smiley_grid_control_btn;
			View smiley_grid_control_area;
		}
	}
}
