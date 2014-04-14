 
package com.zhiyin.android.network.socket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


/**
 * 与服务端连接服务
 * 
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public class MessageConnectorService extends Service {

   
    MessageConnectorManager manager;

    private IBinder binder=new MessageConnectorService.LocalBinder();
    @Override
    public void onCreate() {
       
    	manager = MessageConnectorManager.getManager();
    	manager.createExceptionCaughtHandler();
    	manager.createMessageReceivedHandler();
    	manager.createMessageSentFailedHandler();
    	manager.createMessageSentSuccessfulHandler();
    	manager.createReplyReceivedHandler();
    	manager.createSessionClosedHandler();
    	manager.createSessionCreatedHandler();
    	manager.createConnectionFailedHandler();
    }

    @Override
    public void onStart(Intent intent, int startId) {
    	if(intent!=null)
    	{
	    	String host = intent.getStringExtra(MessageConnectorManager.MESSAGE_SERVIER_HOST);
	    	int port = intent.getIntExtra(MessageConnectorManager.MESSAGE_SERVIER_PORT, 1234);
	    	if(host!=null)
	    	{
	    	   manager.connect(host,port);
	    	}
    	}
    }
   
    public void connect(String host,int port)
    {
    	manager.connect(host,port);
    }

    @Override
    public void onDestroy() {
    	manager.destroy();
    }

    public void stop() {
    	this.stopSelf();
    }
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

    public class LocalBinder extends Binder{
    	
    	public MessageConnectorService getService()
    	{
            return MessageConnectorService.this;
        }
    }
	
 
}
