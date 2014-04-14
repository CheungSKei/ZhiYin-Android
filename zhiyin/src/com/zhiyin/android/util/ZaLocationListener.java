package com.zhiyin.android.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * 百度定位返回结果监听
 * @version 1.0.0
 * @author liulinbo
 * @date 2013.04.15
 * */
public class ZaLocationListener implements BDLocationListener{
	private ReturnLocationListener mListener;
	
	public void setReturnListener(ReturnLocationListener listener){
		mListener= listener;
	}
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		// 接收异步返回的定位结果，参数是BDLocation类型参数
		if(mListener == null){
			return ;
		}
		if (location == null)
			return ;
		if(location.getLatitude()==4.9E-324||location.getLongitude()==4.9E-324)
			return ;
		mListener.success(location.getLatitude(),location.getLongitude(), location);
	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
		// 接收异步返回的POI查询结果，参数是BDLocation类型参数
		if (poiLocation == null){
			return ;
		}		
	}
	
	/**成功返回Location对象后，回调通知Activity*/
	public static interface ReturnLocationListener{
		/**Location成功
		 * @param latitude 经度
		 * @param lontitude 纬度
		 * */
		public void success(double latitude,double lontitude, BDLocation location);
	}
	
}