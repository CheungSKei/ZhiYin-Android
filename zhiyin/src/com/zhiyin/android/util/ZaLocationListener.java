package com.zhiyin.android.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * �ٶȶ�λ���ؽ������
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
		// �����첽���صĶ�λ�����������BDLocation���Ͳ���
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
		// �����첽���ص�POI��ѯ�����������BDLocation���Ͳ���
		if (poiLocation == null){
			return ;
		}		
	}
	
	/**�ɹ�����Location����󣬻ص�֪ͨActivity*/
	public static interface ReturnLocationListener{
		/**Location�ɹ�
		 * @param latitude ����
		 * @param lontitude γ��
		 * */
		public void success(double latitude,double lontitude, BDLocation location);
	}
	
}