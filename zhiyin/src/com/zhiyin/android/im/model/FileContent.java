package com.zhiyin.android.im.model;

import java.io.File;

import com.zhiyin.android.im.model.Entity.Builder;


/**
 * 文件内容实体
 * {@code fileName} 文件名
 * {@code fileNameLength} 文件名长度
 * {@code fileLength} 文件长度
 * {@code content} 文件
 * 
 * @version 1.0.0
 * @date 2014-02-20
 * @author S.Kei.Cheung
 */
public class FileContent extends Entity implements Builder {

	private static final long serialVersionUID = 4734133655754164403L;

	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件名长度
	 */
	private int fileNameLength;
	
	/**
	 * 文件长度
	 */
	private int fileLength;
	
	/**
	 * 文件
	 */
	private File fileContent;

	public String getFileName() {
        return fileName;  
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public int getFileNameLength() {
        return fileNameLength;  
    }

    public void setFileNameLength(int fileNameLength) {
        this.fileNameLength = fileNameLength;
    }
	
    public int getFileLength() {  
        return fileLength;  
    }  

    public void setFileLength(int fileLength) {  
        this.fileLength = fileLength;  
    }  

    public File getFileContent() {  
        return fileContent;  
    }  

    public void setFileContent(File fileContent) {  
        this.fileContent = fileContent;
    }

}
