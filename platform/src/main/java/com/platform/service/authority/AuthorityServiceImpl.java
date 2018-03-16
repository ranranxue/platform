package com.platform.service.authority;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.dao.Stu_limitDAO;
import com.platform.data.ApiResultInfo;
import com.platform.model.Stu_limit;
import com.platform.rmodel.authority.StudentAuthorityResponse;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	private Logger logger=Logger.getLogger(AuthorityServiceImpl.class);
	@Autowired
	private Stu_limitDAO stu_limitDAO;
	public Stu_limitDAO getStu_limitDAO() {
		return stu_limitDAO;
	}
	public void setStu_limitDAO(Stu_limitDAO stu_limitDAO) {
		this.stu_limitDAO = stu_limitDAO;
	}
	
	public StudentAuthorityResponse getStudentAuthority() {
		// TODO Auto-generated method stub
		Stu_limit  stu_limit=null;
		try {
			logger.debug("get the stu_limit from stu_limit db");
			stu_limit=stu_limitDAO.getAllStudentAuthority();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("stu_limitDAO error",e);
			StudentAuthorityResponse response= new StudentAuthorityResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if(stu_limit==null){
			logger.debug("fail to get stu_limit");
			StudentAuthorityResponse response= new StudentAuthorityResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		StudentAuthorityResponse response= new StudentAuthorityResponse();
		response.setCode(0);
		response.setMsg("get the  all student authority successfully!");
		response.setIs_forum(stu_limit.getForum());
		response.setIs_editInfo(stu_limit.getEdit_info());
		response.setIs_upload(stu_limit.getUpload());
		return response;
	}
	
	

}
