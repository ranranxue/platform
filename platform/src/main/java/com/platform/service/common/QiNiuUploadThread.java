package com.platform.service.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.platform.data.TimeData;
import com.platform.util.TimeUtil;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


/*import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.resumableio.ResumeableIoApi;
import com.qiniu.api.rs.PutPolicy;
*/

public class QiNiuUploadThread  implements Runnable {
	private Logger logger = Logger.getLogger(QiNiuUploadThread .class);
	private List<MultipartFile> fileMap;
	private List<String> fileNameList;

	public List<MultipartFile> getFileMap() {
		return fileMap;
	}

	public void setFileMap(List<MultipartFile> fileMap) {
		this.fileMap = fileMap;
	}
	

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	public void run() {
		// TODO Auto-generated method stub
		logger.debug(" start to upload the file");
		 //构造一个带指定Zone对象的配置类
		 //华东  Zone.zone0()  
         //华北  Zone.zone1()  
        //华南  Zone.zone2()  
        //北美  Zone.zoneNa0() 
		Configuration cfg = new Configuration(Zone.zone1());
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传 
		
		
		String accessKey="cX_YbLLRZoKRSuNWJ8l7Uiz_WSM5iZgMoRGgHi4L";
		String secretKey="eXumQH65WPU_4PxCGLC8qL22GhEbv4iRVTzDLbCh";
		String bucketName = "beihang-platform";
		String upToken=null;
		try {
			Auth auth = Auth.create(accessKey, secretKey);  
			upToken = auth.uploadToken(bucketName);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("uploadSyn init QiNiuService error",e);
		}
		for(int i=0;i<fileMap.size();i++){
			try {
				   byte[] uploadBytes = fileMap.get(i).getBytes();
				   System.out.println("qiniu start upload the img"+TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHMS));
				   Response response=uploadManager.put(uploadBytes, fileNameList.get(i), upToken);
				//InputStream in=fileMap.get(i).getInputStream();
				//Response response=uploadManager.put(in, fileNameList.get(i), upToken, null, null);
					
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
				System.out.println("qiniu finish upload the img"+TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHMS));
				System.out.println(putRet.key);
		        System.out.println(putRet.hash);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("upload the file error",e);
				
			}
			logger.debug("upload the file"+fileNameList.get(i)+"to qiniu");
			
		}
		logger.debug("upload the file successfully!");
	}
}
