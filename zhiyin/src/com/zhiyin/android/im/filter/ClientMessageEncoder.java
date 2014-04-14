package com.zhiyin.android.im.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import android.os.Environment;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.model.AbstractRequest;
import com.zhiyin.android.im.model.Entity;
import com.zhiyin.android.im.model.FileContent;
import com.zhiyin.android.im.model.FileRequest;

/**
 * 客户端消息编解码类.<br>
 * {@link ProtocolEncoderAdapter}
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class ClientMessageEncoder extends ProtocolEncoderAdapter {

	private static final Charset CHARSET = Charset.forName("utf-8");

	@Override
	public void encode(IoSession session, Object message,ProtocolEncoderOutput out) throws Exception {
		IoBuffer buff = IoBuffer.allocate(Constants.DEFAULT_BUFFER_SIZE).setAutoExpand(true);
		if(message instanceof FileRequest){
			FileRequest fileRequest = (FileRequest)message;
			// 初始化大小:0
			buff.putInt(0);
		    buff.putString(((AbstractRequest)message).toString(),CHARSET.newEncoder());
			buff.put(Constants.MESSAGE_SEPARATE);
			List<FileContent> files = new ArrayList<FileContent>();
			// 文件1
			FileContent fileContent = new FileContent();
			File file1 = new File(Environment.getExternalStorageDirectory().getPath()+"/yuanlai_avatar.png");
			fileContent.setFileContent(file1);
			fileContent.setFileLength((int)file1.length());
			fileContent.setFileName(file1.getName());
			fileContent.setFileNameLength(file1.getName().length());
			files.add(fileContent);
			fileRequest.setFiles(files);
			// 添加文件
			for(FileContent file : fileRequest.getFiles()){
				buff.putInt(file.getFileNameLength());
				buff.putString(file.getFileName(),CHARSET.newEncoder());
				buff.putInt(file.getFileLength());
				buff.put(inputStreamToByte(new FileInputStream(file.getFileContent())));
			}
			buff.putInt(0,buff.position()-4);
		}else if(message instanceof AbstractRequest){
			System.out.println("开始客户端编码");
			System.out.println("客户端发送的消息内容为：" + ((AbstractRequest)message).toString());
			buff.putString(((AbstractRequest)message).toString(), CHARSET.newEncoder());
			buff.put(Constants.MESSAGE_SEPARATE);
		}else if(message instanceof Entity){
			System.out.println("心跳开始客户端编码");
			System.out.println("心跳发送的消息内容为：" + ((Entity)message).toString());
			buff.putString(((Entity)message).toString(), CHARSET.newEncoder());
			buff.put(Constants.MESSAGE_SEPARATE);
		}else if(message instanceof Byte){	// 心跳一个字节
			buff.put(((Byte) message).byteValue());
		}else if(message instanceof String){
		    buff.putString((String)message, CHARSET.newEncoder());
		}
		buff.flip();
		out.write(buff);
		System.out.println("结束客户端编码");
	}
	
	/**
	 * 转换成字节数组
	 * @param inStream	输入流
	 * @return 返回文件字节数据
	 * @throws IOException
	 */
	private byte[] inputStreamToByte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int rc;
		while ((rc = inStream.read()) != -1) {
			  swapStream.write(rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
}
