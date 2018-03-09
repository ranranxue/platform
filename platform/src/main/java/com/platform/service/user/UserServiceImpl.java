package com.platform.service.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platform.dao.LoginDAO;
import com.platform.dao.Manage_scopeDAO;
import com.platform.dao.UserDAO;
import com.platform.dao.WorksDAO;

import com.platform.data.ApiResultInfo;
import com.platform.data.StaticData;
import com.platform.model.BasicResponse;
import com.platform.model.Login;
import com.platform.model.User;
import com.platform.rmodel.user.AddDetailIntroRequest;
import com.platform.rmodel.user.AddDetailIntroResponse;
import com.platform.rmodel.user.AddUserRequest;
import com.platform.rmodel.user.AddUserResponse;
import com.platform.rmodel.user.AllUserResponse;
import com.platform.rmodel.user.ClassInfoRequest;
import com.platform.rmodel.user.ClassInfoResponse;
import com.platform.rmodel.user.DeleteUserRequest;
import com.platform.rmodel.user.DeleteUserResponse;
import com.platform.rmodel.user.ExitRequest;
import com.platform.rmodel.user.GetUsersRequest;
import com.platform.rmodel.user.GetUsersResponse;
import com.platform.rmodel.user.GradeInfoResponse;
import com.platform.rmodel.user.Head_urlUpdateRequest;
import com.platform.rmodel.user.Head_urlUpdateResponse;
import com.platform.rmodel.user.HomePageResponse;
import com.platform.rmodel.user.JudgeIsLoginRequest;
import com.platform.rmodel.user.JudgeIsLoginResponse;
import com.platform.rmodel.user.LoginRequest;
import com.platform.rmodel.user.LoginResponse;
import com.platform.rmodel.user.PasswordUpdateRequest;
import com.platform.rmodel.user.PersonInfoEditRequest;
import com.platform.rmodel.user.PersonInfoEditResponse;
import com.platform.rmodel.user.UserBasicInfo;
import com.platform.rmodel.user.UserInfo;
import com.platform.rmodel.user.UserInfoRequest;
import com.platform.rmodel.work.WorkInfo;
import com.platform.util.MesDigest;
import com.platform.util.RedisUtil;
import com.platform.util.TimeUtil;
import com.platform.data.TimeData;

@Service("userService")
public class UserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private WorksDAO worksDAO;
	@Autowired
	private Manage_scopeDAO manage_scopeDAO;

	public Manage_scopeDAO getManage_scopeDAO() {
		return manage_scopeDAO;
	}

	public void setManage_scopeDAO(Manage_scopeDAO manage_scopeDAO) {
		this.manage_scopeDAO = manage_scopeDAO;
	}

	public WorksDAO getWorksDAO() {
		return worksDAO;
	}

	public void setWorksDAO(WorksDAO worksDAO) {
		this.worksDAO = worksDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public LoginResponse login(LoginRequest request) {
		// TODO Auto-generated method stub
		User user = null;
		try {
			logger.debug("check the user whether exists or not using loginName" + request.getLoginName());
			user = userDAO.getUserInfo(request.getLoginName());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO service error ", e);
			LoginResponse response = new LoginResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (user == null) {
			// 用户为空
			logger.debug("user is not existed ! ");
			LoginResponse response = new LoginResponse();
			response.setCode(ApiResultInfo.ResultCode.ConfirmUserExist);
			response.setMsg(ApiResultInfo.ResultMsg.ConfirmUserExist);
			return response;
		} else {
			// 用户存在
			Integer loginId = -1;
			try {
				logger.debug(" start to get the login id from login db using loginName" + request.getLoginName()
						+ " and password" + request.getPassword());
				loginId = loginDAO.getLoginId(request.getLoginName(), MesDigest.SHA(request.getPassword()));// ################这里登陆加密了
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("loginDAO service error ", e);
				LoginResponse response = new LoginResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (loginId == 0) {
				logger.error("fail to login ");
				LoginResponse response = new LoginResponse();
				response.setCode(ApiResultInfo.ResultCode.PasswordError);
				response.setMsg(ApiResultInfo.ResultMsg.PasswordError);
				return response;
			}

			// 登陆成功之后在此处设置session

			logger.debug("userLogin genarate the session key");
			StringBuilder buffer = new StringBuilder("");
			buffer.append(request.getLoginName()).append(TimeUtil.getCurrentTime(TimeData.TimeFormat.YMDHM));
			String key = MesDigest.SHA(buffer.toString());
			try {
				logger.debug("userlogin set the session key to the cache");
				RedisUtil.setx(key, TimeData.RedisTimeOut.SessionOut, request.getLoginName());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("set logon session error");
				LoginResponse response = new LoginResponse();
				response.setCode(ApiResultInfo.ResultCode.UserSessionError);
				response.setMsg(ApiResultInfo.ResultMsg.UserSessionError);
				return response;
			}
			LoginResponse response = new LoginResponse();
			response.setCode(0);
			response.setMsg("login successfully !");
			response.setSessionKey(key);// 将生成的session中的key传出去；
			response.setLoginName(user.getStuid());
			response.setName(user.getName());
			response.setNickname(user.getNickname());
			response.setSelf_introduction(user.getSelf_introduction());
			response.setGender(user.getGender());
			response.setPhone(user.getPhone());
			response.setEmail(user.getEmail());
			response.setHead_url(StaticData.QiNiuFilePath + user.getHead_url());
			response.setIsManager(user.getIsManager());
			return response;
		}
	}

	public BasicResponse exit(ExitRequest request) {
		// TODO Auto-generated method stub
		String key = request.getSessionKey();

		if (key == null) {
			logger.error("userexit sessionKey is null");
			BasicResponse response = new BasicResponse();
			response.setCode(ApiResultInfo.ResultCode.UserExitError);
			response.setMsg(ApiResultInfo.ResultMsg.UserExitError);
			return response;
		}
		int result = 0;
		try {

			logger.debug("userexit service from redis");
			result = RedisUtil.removex(key);// 移除redis中的session;
		} catch (Exception e) {
			logger.error("userexit redis error");
		}

		if (result <= 0) {

			BasicResponse response = new BasicResponse();
			response.setCode(ApiResultInfo.ResultCode.UserExitError);
			response.setMsg(ApiResultInfo.ResultMsg.UserExitError);
			return response;
		}

		BasicResponse response = new BasicResponse();
		response.setCode(0);
		response.setMsg("user exit successfully!");
		return response;
	}

	public HomePageResponse getPersonalHomePage(UserInfoRequest request) {
		// TODO Auto-generated method stub
		User user = null;
		logger.debug(" get the userInfo using stuid" + request.getStuid() + "from user db");
		try {
			logger.debug(" get the userInfo using stuid" + request.getStuid() + "from user db");
			user = userDAO.getUserInfo(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO service error", e);
			HomePageResponse response = new HomePageResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (user == null) {
			logger.error("fail to get the userInfo");
			HomePageResponse response = new HomePageResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		String managerId="0";
		if(request.getIs_manageId()==1){
			//需要获取该用户的管理员的id
			try {
				logger.debug("get the managerId using grade");
				managerId=manage_scopeDAO.getManagerIdByGrade(user.getGrade());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("manage_scopeDAO service error", e);
				HomePageResponse response = new HomePageResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if(managerId=="0"){
				logger.error("fail to get managerId");
				HomePageResponse response = new HomePageResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;	
			}
		}
		List<WorkInfo> worksList=null;
		try {
			logger.debug("start to get my works from works db using stuid");
			worksList=worksDAO.getMyAllWorks(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("worksDAO service error", e);
			HomePageResponse response = new HomePageResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if(worksList==null){
			logger.debug(" fail to get my works");
			HomePageResponse response = new HomePageResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		HomePageResponse response = new HomePageResponse();
		response.setCode(0);
		response.setMsg("get the home page info successfully!");
		response.setStuid(user.getStuid());
		response.setName(user.getName());
		response.setGender(user.getGender());
		response.setSelf_introduction(user.getSelf_introduction());
		response.setHead_url(StaticData.QiNiuFilePath + user.getHead_url());
		response.setNickname(user.getNickname());
		response.setIsManager(user.getIsManager());
		response.setDetail_introduction(user.getDetail_introduction());
		response.setManagerId(managerId);
		response.setWorksList(worksList);
		return response;
	}

	public Head_urlUpdateResponse updateHead_url(Head_urlUpdateRequest request) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			logger.debug("update the head_url using head_url" + request.getHead_url() + "stuid" + request.getStuid());
			num = userDAO.modifyHeadImg(request.getHead_url(), request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error ", e);
			Head_urlUpdateResponse response = new Head_urlUpdateResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (num == 0) {
			logger.error("fail to update the headImg");
			Head_urlUpdateResponse response = new Head_urlUpdateResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			logger.debug("the num is 1");
			Head_urlUpdateResponse response = new Head_urlUpdateResponse();
			response.setCode(0);
			response.setMsg("update the headImg sucessfully!");
			response.setHead_url(StaticData.QiNiuFilePath + request.getHead_url());
			return response;
		}

	}

	public BasicResponse updatePassword(PasswordUpdateRequest request) {
		// TODO Auto-generated method stub
		Integer checkNum = -1;
		try {
			logger.debug("start to check the user from login db using stuid" + request.getStuid() + "olsPassword"
					+ request.getOldPassword());
			checkNum = loginDAO.checkUserLogin(request.getStuid(), MesDigest.SHA(request.getOldPassword()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			BasicResponse response = new BasicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (checkNum == -1) {
			logger.debug("fail to check the user");
			BasicResponse response = new BasicResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else if (checkNum == 0) {
			BasicResponse response = new BasicResponse();
			response.setCode(ApiResultInfo.ResultCode.PasswordError);
			response.setMsg(ApiResultInfo.ResultMsg.PasswordError);
			return response;
		} else {
			Integer updateNum = 0;
			try {
				logger.debug("start to update user login using stuid" + request.getStuid() + "newPassword"
						+ request.getNewPassword());
				updateNum = loginDAO.updatePassword(MesDigest.SHA(request.getNewPassword()), request.getStuid());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("userService error", e);
				BasicResponse response = new BasicResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (updateNum == 0) {
				logger.debug("fail to update the user password");
				BasicResponse response = new BasicResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;

			} else {
				BasicResponse response = new BasicResponse();
				response.setCode(0);
				response.setMsg("update password successfully!");
				return response;

			}

		}
	}

	public AllUserResponse getAllUser(UserInfoRequest request) {
		// TODO Auto-generated method stub
		// 首先得到这个管理员的管理的学生范围，筛选出来这些年级的学生
		List<String> gradeIdList = null;
		List<UserBasicInfo> allUsersList = null;
		try {
			logger.debug("get the manager manage student limit from manage_scope db using stuid" + request.getStuid());
			gradeIdList = manage_scopeDAO.getManageGradeList(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" userService error", e);
			AllUserResponse response = new AllUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (gradeIdList == null) {
			logger.debug("fail to get manage student limit");
			AllUserResponse response = new AllUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		} else if (gradeIdList.size() == 0) {
			// 没有管理的学生
			allUsersList = new ArrayList<UserBasicInfo>();
		} else {
			// 存在管理的年纪
			try {
				logger.debug(" get all users info from user db using gradeIdList");
				allUsersList = userDAO.getUsersByMultiGrade(gradeIdList);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(" userService error", e);
				AllUserResponse response = new AllUserResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (allUsersList == null) {
				logger.debug(" fail to get the all user info list");
				AllUserResponse response = new AllUserResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (allUsersList.size() == 0) {
				allUsersList = new ArrayList<UserBasicInfo>();
			}
		}
		AllUserResponse response = new AllUserResponse();
		response.setCode(0);
		response.setMsg("get all userList successfully!");
		response.setStuList(allUsersList);
		return response;
	}

	public AddUserResponse addUser(AddUserRequest request) {
		// TODO Auto-generated method stub
		Integer checkNum = -1;
		try {
			logger.debug("check the stuid whether exists or not");
			checkNum = userDAO.getStuNum(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			AddUserResponse response = new AddUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (checkNum == -1) {
			logger.debug(" fail to get the stu num by stuid");
			AddUserResponse response = new AddUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else if (checkNum > 0) {
			// 说明此学号已经存在
			logger.debug(" the stuid is already exists !");
			AddUserResponse response = new AddUserResponse();
			response.setCode(ApiResultInfo.ResultCode.duplicateStuid);
			response.setMsg(ApiResultInfo.ResultMsg.duplicateStuid);
			return response;
		} else {
			Integer num = 0;
			User user = new User();
			user.setStuid(request.getStuid());
			user.setName(request.getName());
			user.setNickname(request.getName());
			user.setIsManager(0);
			user.setGrade(request.getGrade());
			user.setClass_info(request.getClassInfo());
			user.setCreate_time((int) TimeUtil.getCurrentTime(TimeData.TimeFormat.YMD));

			try {
				logger.debug("Insert the user in user db");
				num = userDAO.insertUser(user);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("userService error", e);
				AddUserResponse response = new AddUserResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			}
			if (num == 0) {
				logger.debug("fail to insert the user in user db");
				AddUserResponse response = new AddUserResponse();
				response.setCode(ApiResultInfo.ResultCode.ServerError);
				response.setMsg(ApiResultInfo.ResultMsg.ServerError);
				return response;
			} else {
				// 插入login db
				Login login = new Login();
				login.setStuid(request.getStuid());
				login.setPassword(MesDigest.SHA("123456"));
				int loginNum = 0;
				try {
					logger.debug("insert the login info");
					loginNum = loginDAO.insertLogin(login);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("userService error", e);
					AddUserResponse response = new AddUserResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
				if (loginNum == 0) {
					logger.debug("fail to insert the login db");
					AddUserResponse response = new AddUserResponse();
					response.setCode(ApiResultInfo.ResultCode.ServerError);
					response.setMsg(ApiResultInfo.ResultMsg.ServerError);
					return response;
				}
			}
		}
		AddUserResponse response = new AddUserResponse();
		response.setCode(0);
		response.setMsg("insert the user successfully!");
		response.setStuid(request.getStuid());
		response.setName(request.getName());
		response.setGrade(request.getGrade());
		response.setClass_info(request.getClassInfo());
		response.setCreate_time((int) TimeUtil.getCurrentTime(TimeData.TimeFormat.YMD));
		return response;
	}

	public DeleteUserResponse deleteUser(DeleteUserRequest request) {
		// TODO Auto-generated method stub
		// 涉及到user 、login 、authority db
		Integer userNum = 0;
		try {
			logger.debug("delete the user from user db using stuid" + request.getStuid());
			userNum = userDAO.deleteUser(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			DeleteUserResponse response = new DeleteUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (userNum == 0) {
			logger.debug("fail to delete user from user db");
			DeleteUserResponse response = new DeleteUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		Integer loginNum = 0;
		try {
			logger.debug("delete the user from login db using stuid" + request.getStuid());
			loginNum = loginDAO.deleteUser(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			DeleteUserResponse response = new DeleteUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (loginNum == 0) {
			logger.debug("fail to delete user from login db");
			DeleteUserResponse response = new DeleteUserResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}

		DeleteUserResponse response = new DeleteUserResponse();
		response.setCode(0);
		response.setMsg("delete user successfully!");
		response.setStuid(request.getStuid());
		return response;
	}

	public PersonInfoEditResponse modifyPersonInfo(PersonInfoEditRequest request) {
		// TODO Auto-generated method stub
		Integer duplicate_phone = 0;
		try {
			logger.debug("check the phone whether duplicate from user db using phone" + request.getPhone());
			duplicate_phone = userDAO.getPhoneNum(request.getPhone(), request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO service error", e);
			PersonInfoEditResponse response = new PersonInfoEditResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (duplicate_phone != 0) {
			logger.debug(" the phone  already exists!");
			PersonInfoEditResponse response = new PersonInfoEditResponse();
			response.setCode(ApiResultInfo.ResultCode.duplicatePhone);
			response.setMsg(ApiResultInfo.ResultMsg.duplicatePhone);
			return response;
		}

		Integer duplicate_email = 0;
		try {
			logger.debug("check the email whether duplicate from user db using email" + request.getEmail());
			duplicate_email = userDAO.getEmailNum(request.getEmail(), request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO service error", e);
			PersonInfoEditResponse response = new PersonInfoEditResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		}
		if (duplicate_email != 0) {
			logger.debug(" the email  already exists!");
			PersonInfoEditResponse response = new PersonInfoEditResponse();
			response.setCode(ApiResultInfo.ResultCode.duplicateEmail);
			response.setMsg(ApiResultInfo.ResultMsg.duplicateEmail);
			return response;
		}
		Integer updateNum = 0;
		try {
			logger.debug("start to update the person info from user db using stuid" + request.getStuid());
			updateNum = userDAO.modifyUserInfo(request.getNickName(), request.getGender(), request.getName(),
					request.getSelf_introduction(), request.getPhone(), request.getEmail(), request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			PersonInfoEditResponse response = new PersonInfoEditResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (updateNum == 0) {
			logger.debug("update the user info unsuccessfully!");
			PersonInfoEditResponse response = new PersonInfoEditResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		logger.debug("update the user info successfully!");
		PersonInfoEditResponse response = new PersonInfoEditResponse();
		response.setCode(0);
		response.setMsg("update the user info successfully!");
		response.setNickName(request.getNickName());
		response.setName(request.getName());
		response.setGender(request.getGender());
		response.setSelf_introduction(request.getSelf_introduction());
		response.setPhone(request.getPhone());
		response.setEmail(request.getEmail());
		return response;
	}

	public AddDetailIntroResponse addDetailIntro(AddDetailIntroRequest request) {
		// TODO Auto-generated method stub
		Integer updateNum = -1;
		try {
			logger.debug("update the detai_intro from user db using stuid " + request.getStuid());
			updateNum = userDAO.updateUserDetailIntro(request.getStuid(), request.getContent());

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			AddDetailIntroResponse response = new AddDetailIntroResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (updateNum < 1) {
			logger.debug("update the user detai_intro error");
			AddDetailIntroResponse response = new AddDetailIntroResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;

		} else {
			logger.debug("update the detail_intro successfully!");
			AddDetailIntroResponse response = new AddDetailIntroResponse();
			response.setCode(0);
			response.setMsg(" update the detail_intro successfully!");
			return response;

		}
	}

	public JudgeIsLoginResponse judgeIsLogin(JudgeIsLoginRequest request) {
		// TODO Auto-generated method stub
		Integer isManager = -1;
		try {
			logger.debug(" start to get user isManager info from user db using stuid");
			isManager = userDAO.getIsManager(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			JudgeIsLoginResponse response = new JudgeIsLoginResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (isManager == -1) {
			logger.debug("fail to get isManager from user db");
			JudgeIsLoginResponse response = new JudgeIsLoginResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		JudgeIsLoginResponse response = new JudgeIsLoginResponse();
		response.setCode(0);
		response.setMsg("get the isManager info successfully!");
		response.setIsManager(isManager);
		return response;
	}

	public GradeInfoResponse getGradeInfo() {
		// TODO Auto-generated method stub
		List<String> grades = null;
		try {
			logger.debug("get all grade from user db");
			grades = userDAO.getGrade();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			GradeInfoResponse response = new GradeInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (grades == null) {
			logger.debug("fail to get all grade");
			GradeInfoResponse response = new GradeInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			logger.debug("get gradeList successfully!");
			GradeInfoResponse response = new GradeInfoResponse();
			response.setCode(0);
			response.setMsg("get gradeList successfully!");
			response.setGradeList(grades);
			return response;
		}

	}

	public ClassInfoResponse getClassInfo(ClassInfoRequest request) {
		// TODO Auto-generated method stub
		List<String> classList = null;
		try {
			logger.debug("get classInfo from user by grade");
			classList = userDAO.getClassByGrade(request.getGrade());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			ClassInfoResponse response = new ClassInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (classList == null) {
			logger.debug("fail to get class info by grade");
			ClassInfoResponse response = new ClassInfoResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			logger.debug("get the class successfully!");
			ClassInfoResponse response = new ClassInfoResponse();
			response.setCode(0);
			response.setMsg("get the class successfully!");
			response.setClassList(classList);
			return response;
		}
	}

	public GetUsersResponse getUserListByClass(GetUsersRequest request) {
		// TODO Auto-generated method stub
		List<UserInfo> studentList = null;
		try {
			logger.debug("get the students from user db by class_info");
			studentList = userDAO.getUserInfoByClass(request.getClass_info());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error", e);
			GetUsersResponse response = new GetUsersResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if (studentList == null) {
			logger.debug("fail to get the studentList");
			GetUsersResponse response = new GetUsersResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		} else {
			if (studentList.size() > 0) {
				for (int i = 0; i < studentList.size(); i++) {
					String head_url = StaticData.QiNiuFilePath + studentList.get(i).getHead_url();
					studentList.get(i).setHead_url(head_url);
				}
			}
			GetUsersResponse response = new GetUsersResponse();
			response.setCode(0);
			response.setMsg("get the studentList successfully!");
			response.setUserList(studentList);
			return response;
		}
	}

	public LoginResponse GetPersonInfo(UserInfoRequest request) {
		// TODO Auto-generated method stub
		User user=null;
		try {
			logger.debug("start to get the person info from userdb using stuid"+request.getStuid());
			user=userDAO.getUserInfo(request.getStuid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDAO error",e);
			LoginResponse response = new LoginResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}
		if(user==null){
			logger.debug("fail to get the LoginResponse ");
			LoginResponse response = new LoginResponse();
			response.setCode(ApiResultInfo.ResultCode.ServerError);
			response.setMsg(ApiResultInfo.ResultMsg.ServerError);
			return response;
		}else{
			LoginResponse response = new LoginResponse();
			response.setLoginName(user.getStuid());
			response.setNickname(user.getNickname());
			response.setName(user.getName());
			response.setHead_url(StaticData.QiNiuFilePath+user.getHead_url());
			response.setGender(user.getGender());
			response.setSelf_introduction(user.getSelf_introduction());
			response.setPhone(user.getPhone());
			response.setEmail(user.getEmail());
			response.setIsManager(user.getIsManager());
			return response;
		}
	}
}
