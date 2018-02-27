package com.platform.rmodel.upload;

import java.util.List;

import com.platform.model.BasicResponse;

public class UploadFileListResponse extends BasicResponse{
	private List<String> fileNameList;

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}
}
