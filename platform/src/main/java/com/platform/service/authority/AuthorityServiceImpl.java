package com.platform.service.authority;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.dao.Manage_scopeDAO;
import com.platform.dao.Stu_limitDAO;
import com.platform.dao.UserDAO;
import com.platform.data.ApiResultInfo;
import com.platform.model.BasicResponse;
import com.platform.model.Stu_limit;
import com.platform.rmodel.authority.Manage_scope;
import com.platform.rmodel.authority.ManagerAuthorityRequest;
import com.platform.rmodel.authority.ManagerAuthorityResponse;
import com.platform.rmodel.authority.StudentAuthorityResponse;
import com.platform.rmodel.authority.saveManagerAuthorityRequest;
import com.platform.rmodel.authority.saveStudentAuthorityRequest;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	private Logger logger=Logger.getLogger(AuthorityServiceImpl.class);
	@Autowired
	private Stu_limitDAO stu_limitDAO;
	@Autowired
	private UserDAO  userDAO;
	@Autowired
	private Manage_scopeDAO manage_scopeDAO;
	
	
	public Manage_scopeDAO getManage_scopeDAO() {
		return manage_scopeDAO;
	}
	public void setManage_scopeDAO(Manage_scopeDAO manage_scopeDAO) {
		this.manage_scopeDAO = manage_scopeDAO;
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
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
	public ManagerAuthorityResponse getManagerAuthority(ManagerAuthorityRequest request) {
		// TODO Auto-generated method stub
		List<String> managersList=new ArrayList<String>();
		if(request.getManager_level()!=2){
			logger.debug("user is not super manager");
			ManagerAuthorityResponse response= new ManagerAuthorityResponse();
			response.setCode(ApiResultInfo.ResultCode.NoManagerAuthority);
			response.setMsg(ApiResultInfo.ResultMsg.NoManagerAuthority);
			return response;	
		}else{
			List<String> allGradesList=new ArrayList<String>();
			try {
				logger.debug("get all grades from user db ");
				allGradesList=userDAO.getAllGrades();
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("userDAO error",e);
				ManagerAuthorityResponse response= new ManagerAuthorityResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			try {
				logger.debug("start to get all manager from user db ");
				managersList=userDAO.getAllManagers();	
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("userDAO error",e);
				ManagerAuthorityResponse response= new ManagerAuthorityResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if(managersList.size()==0){
				logger.debug("no manager except super manager");
				ManagerAuthorityResponse response= new ManagerAuthorityResponse();
				response.setCode(0);
				response.setMsg("no manager except super manager");
				response.setManageScopeList(new ArrayList<Manage_scope>());
				return response;
			}else{
				List<Manage_scope> manageScopeList=new ArrayList<Manage_scope>();
				for(int i=0;i<managersList.size();i++){
					List<String> gradeList=new ArrayList<String>();
					try {
						logger.debug("get each manager’s manage scope");
						gradeList=manage_scopeDAO.getManageGradeList(managersList.get(i));
					} catch (Exception e) {
						// TODO: handle exception
						logger.error("manage_scopeDAO error",e);
						ManagerAuthorityResponse response= new ManagerAuthorityResponse();
						response.setCode(ApiResultInfo.ResultCode.ServerError);
						response.setMsg(ApiResultInfo.ResultMsg.ServerError);
						return response;
					}
					String managerName=null;
					try {
						logger.debug("get managerName from user db using managerId"+managersList.get(i));
						managerName=userDAO.getNameById(managersList.get(i));
					} catch (Exception e) {
						// TODO: handle exception
						logger.error("userDAO error",e);
						ManagerAuthorityResponse response= new ManagerAuthorityResponse();
						response.setCode(ApiResultInfo.ResultCode.ServerError);
						response.setMsg(ApiResultInfo.ResultMsg.ServerError);
						return response;	
					}
					Manage_scope manage_scope=new Manage_scope();
					manage_scope.setManagerId(managersList.get(i));
					manage_scope.setManagerName(managerName);
					manage_scope.setGradeList(gradeList);
					manageScopeList.add(manage_scope);
				}
				
				ManagerAuthorityResponse response= new ManagerAuthorityResponse();
				response.setCode(0);
				response.setMsg("get all managers and manage grades successfully!");
				response.setAllGradesList(allGradesList);
				response.setManageScopeList(manageScopeList);
				return response;
			}
			
		}
		
	}
	public BasicResponse saveStudentAuthority(saveStudentAuthorityRequest request) {
		// TODO Auto-generated method stub
		Integer classification=request.getClassification();
		Integer update=0;
		
		if(classification==1){
			//说明是禁言论坛
			try {
				logger.debug("start to update forum from stu_limit db ");
				update=stu_limitDAO.updateForum(request.getValue());
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("stu_limitDAO error",e);
				BasicResponse response=new BasicResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
				
			}
			if(update==1){
				BasicResponse response=new BasicResponse();
				response.setCode(0);
				response.setMsg("update the forum successfully!");
				return response;
				
			}
		}else if(classification==2){
			//编辑个人信息  
			try {
				logger.debug("start to update edit_info from stu_limit db ");
				update=stu_limitDAO.updateEdit_info(request.getValue());
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("stu_limitDAO error",e);
				BasicResponse response=new BasicResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if(update==1){
				BasicResponse response=new BasicResponse();
				response.setCode(0);
				response.setMsg("update the edit_info successfully!");
				return response;
				
			}
		}else{
			//上传作品
			try {
				logger.debug("start to update upload from stu_limit db ");
				update=stu_limitDAO.updateUpload(request.getValue());
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("stu_limitDAO error",e);
				BasicResponse response=new BasicResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if(update==1){
				BasicResponse response=new BasicResponse();
				response.setCode(0);
				response.setMsg("update the upload successfully!");
				return response;
			}	
		}
		return null;	
	}
	public BasicResponse saveManagerAuthority(saveManagerAuthorityRequest request) {
		// TODO Auto-generated method stub
		Integer insertNum=0;
		try {
			logger.debug("insert manage_scope object from manage_scope db");
			insertNum=manage_scopeDAO.insertManageScope(request.getManagerId(), request.getGrade(), 1);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("manage_scopeDAO error",e);
			BasicResponse response=new BasicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if(insertNum!=0){
			logger.debug("insert manage_scope db successfully!");
			BasicResponse response=new BasicResponse();
			response.setCode(0);
			response.setMsg("insert manage_scope db successfully!");
			return response;
		}
		return null;
	}
}
