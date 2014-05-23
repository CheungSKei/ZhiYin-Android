package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyin.android.im.R;

/**
 * Emoji 适配器
 * 
 * @version 1.0.0
 * @date 2014-05-19
 * @author S.Kei.Cheung
 */
public class EmojiGridAdapter extends BaseAdapter {

	private Context mContext;
	// 当前页的Emoji个数
	private int mItemsPerPage;
	// 当前页
	private int mCurPage;
	
	public EmojiGridAdapter(Context context,int itemsPerPage,int curPage){
		this.mContext = context;
		this.mItemsPerPage = itemsPerPage;
		this.mCurPage = curPage;
	}
	
	@Override
	public int getCount() {

		return 0;
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO:微信smiley分三类:通过smileyGrid初始化type:25、23、20
		ViewHolder holder = null;
		if (convertView == null) {
			 holder=new ViewHolder();
//			 convertView = View.inflate(this.mContext,R.layout.smiley_grid_item_l, null);
//			 holder.smiley_grid_item_bg = convertView.findViewById(R.id.smiley_grid_item_bg);
//			 holder.art_emoji_icon_iv = (ImageView)convertView.findViewById(R.id.art_emoji_icon_iv);
//			 holder.art_emoji_icon_text = (TextView)convertView.findViewById(R.id.art_emoji_icon_text);
//			 holder.art_emoji_del_tv = (TextView)convertView.findViewById(R.id.art_emoji_del_tv);
//			 holder.art_emoji_new_tv = (TextView)convertView.findViewById(R.id.art_emoji_new_tv);
//			 holder.art_emoji_icon_mask = convertView.findViewById(R.id.art_emoji_icon_mask);
//			 holder.smiley_grid_control_btn = (ImageView)convertView.findViewById(R.id.smiley_grid_control_btn);
//			 holder.smiley_grid_control_area = convertView.findViewById(R.id.smiley_grid_control_area);
//			 holder.art_emoji_icon_iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
//			 convertView.setTag(holder);
			 
			 convertView = View.inflate(this.mContext, R.layout.smiley_grid_item_s, null);
			 holder.art_emoji_icon_iv = (ImageView)convertView.findViewById(R.id.art_emoji_icon_iv);
			 holder.art_emoji_icon_iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			 convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
//		holder.smiley_grid_control_area.setVisibility(View.GONE);
//		holder.art_emoji_icon_text.setVisibility(View.VISIBLE);
//		if (position == 0)
//		{
//			holder.smiley_grid_control_area.setVisibility(View.VISIBLE);
//			holder.smiley_grid_control_btn.setBackgroundResource(R.drawable.app_panel_setting_icon);
//			holder.smiley_grid_item_bg.setBackgroundDrawable(null);
//		}else{
//			// 删除按钮
//			holder.smiley_grid_item_bg.setBackgroundResource(R.drawable.smiley_item_bg2);
//			
//			if (position == getCount()-1)
//			{
//					holder.art_emoji_icon_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.del_btn));
//			}else
//			{
//				//	holder.art_emoji_icon_ivsetImageDrawable(localDrawable2);
//			}
//		}
		
		holder.art_emoji_icon_iv.setImageDrawable(null);
		holder.art_emoji_icon_iv.setImageDrawable(EmojiResourceReChange.getSmileyDrawable(mContext,position + (-1 + this.mItemsPerPage) * this.mCurPage));
		return convertView;
	}
	
	public final void update()
	{
		// 更新emoji icon
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
