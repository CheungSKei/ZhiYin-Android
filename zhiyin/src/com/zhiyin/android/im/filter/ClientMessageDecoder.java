package com.zhiyin.android.im.filter;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.model.AbstractRequest;
import com.zhiyin.android.im.model.AbstractResponse;

/**
 * 客户端消息解解码类.<br>
 * {@link CumulativeProtocolDecoder}
 * 
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class ClientMessageDecoder extends CumulativeProtocolDecoder {

	private final Charset charset = Charset.forName("UTF-8");
	private IoBuffer buff = IoBuffer.allocate(320).setAutoExpand(true);

	@Override
	public boolean doDecode(IoSession iosession, IoBuffer iobuffer,
			ProtocolDecoderOutput out) throws Exception {
		
//		// 心跳(1个字节)
//		if(iobuffer.hasRemaining() && iobuffer.remaining()==1){
//			byte heartbeat = iobuffer.get();
//			out.write(heartbeat);
//			return true;
//		}
//		// 传输内容
//		else if(iobuffer.hasRemaining() && iobuffer.remaining()>=4){
//			iobuffer.mark();
//			int length = iobuffer.getInt();
//
//			if (length > iobuffer.remaining()) { // 当前buffer中的数据不够长,返回false,父类方法中继续等待数据
//				iobuffer.reset();
//				System.out.println(" 当前buffer中的数据长度:" + iobuffer.remaining()
//						+ ",期待长度:" + length);
//				return false;
//			}
//
//			while (iobuffer.hasRemaining()) {
//				byte b = iobuffer.get();
//				if (b == '\b') {
//					break;
//				}
//				buff.put(b);
//			}
//
//			buff.flip();
//			byte[] bytes = new byte[buff.limit()];
//			buff.get(bytes);
//			String message = new String(bytes, charset);
//			buff.clear();
//
//			if (iobuffer.hasRemaining()) {
//				// 开始读取文件
//				while (iobuffer.hasRemaining()) {
//					buff_files.put(iobuffer.get());
//				}
//				buff_files.flip();
//				// 输出文件
//				JsonParser json = new JsonParser();
//				JsonElement je = json.parse(message);
//				JsonObject jsonobject = je.getAsJsonObject();
//				fileCounts = jsonobject.get("data").getAsJsonObject()
//						.get("filecount").getAsInt();
//				for (int i = 0; i < fileCounts; i++) {
//					int fileNameLength = buff_files.getInt();
//					byte[] fileNameBytes = new byte[fileNameLength];
//					buff_files.get(fileNameBytes);
//					String fileName = new String(fileNameBytes, charset);
//					System.out.println(" 当前文件名长度:" + fileNameLength + ",文件名:"
//							+ fileName);
//					int fileLength = buff_files.getInt();
//					System.out.println(" 当前文件长度:" + fileLength);
//					byte[] pngfile = new byte[fileLength];
//					int k = 0;
//					while (buff_files.hasRemaining()) {
//						pngfile[k] = buff_files.get();
//						k++;
//						if (k == fileLength) {
//							k = 0;
//							break;
//						}
//					}
//					FileOutputStream fileOut = new FileOutputStream(new File(fileName));
//					fileOut.write(pngfile);
//					fileOut.flush();
//					fileOut.close();
//				}
//				buff_files.clear();
//			}
//			out.write(message);
//			return true;
//		}
//
//		return false;
		System.out.println("开始客户端解码");
		boolean complete = false;
		while(iobuffer.hasRemaining()){
			byte b = iobuffer.get();
			if(Constants.MESSAGE_SEPARATE == b){
				complete = true;
				break;
			}else{
				buff.put(b);
			}
		}
		 
		if(complete){
			buff.flip();
			byte[] bytes = new byte[buff.limit()];
			buff.get(bytes);
			String receiveMessage = new String(bytes, charset);
			buff.clear();
			
			System.out.println("收到服务端消息：" + receiveMessage);
			
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(receiveMessage,  
	                new TypeToken<Map<String, Object>>() {  
	                }.getType());
			if(map != null){
				String cmd = map.get("cmd").toString();
				if(Constants.IMCmd.IM_RESPONSE_CMD.equals(cmd)){
					AbstractResponse response = new AbstractResponse();
					response = gson.fromJson(receiveMessage, AbstractResponse.class);
					out.write(response);
				}else{
					AbstractRequest request = new AbstractRequest();
					request = gson.fromJson(receiveMessage, AbstractRequest.class);
					out.write(request);
				}
			}
			
			System.out.println("完成客户端消息解码");
		}
		
		return complete;
	}
}
