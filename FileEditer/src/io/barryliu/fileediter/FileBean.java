package io.barryliu.fileediter;

import java.io.File;

import android.widget.Toast;

/**
 * 携带一个文件夹或文件的信息,实现Comparable接口 再调用Collections.sort(list)方法实现排序，文件夹在上面
 * @author Barry
 *
 */
public class FileBean implements Comparable{
	private String filePath;
	private String fileName;
	@Override
	public String toString() {
		return "FileBean [filePath=" + filePath + ", fileName=" + fileName
				+ "]";
	}
	public FileBean(String filePath, String fileName) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
	}
	public FileBean() {
		super();
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public int compareTo(Object o) {
		FileBean fileBean =(FileBean) o;
		if(fileBean!=null && fileBean.getFilePath()!=null){
			File file =new File(fileBean.getFilePath());
			if(file.isDirectory()){
				return 1;
			}else{
				return -1;
			}
		}
		return 0;
	}
	
}
