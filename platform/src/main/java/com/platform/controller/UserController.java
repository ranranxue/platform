package com.platform.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.platform.data.ApiResult;
import com.platform.data.ApiResultFactory;
import com.platform.data.ApiResultInfo;
import com.platform.model.BasicResponse;
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
import com.platform.rmodel.user.UserInfoRequest;
import com.platform.rmodel.user.addManagerRequest;
import com.platform.service.user.UserService;
import com.platform.util.DataTypePaserUtil;
import com.platform.util.RedisUtil;
import com.platform.util.RequestUtil;

@Controller
public class UserController {
	private Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("login")
	private @ResponseBody ApiResult login(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {

		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		// responseHttp.setHeader("Access-Control-Allow-Methods", "POST, GET,
		// OPTIONS, DELETE");
		// responseHttp.setHeader("Access-Control-Max-Age", "3600");
		// responseHttp.setHeader("Access-Control-Allow-Headers","Origin,
		// X-Requested-With, Content-Type, Accept");
		// responseHttp.setHeader("Access-Control-Allow-Headers",
		// "x-requested-with,Authorization,Content-Type,Accept,Origin");
		// responseHttp.setHeader("Access-Control-Allow-Credentials","true");
		// chain.doFilter(requestHttp, responseHttp);

		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "loginName", "password" };// 分别是登录id(即是登录名）,和密码
		// 检查传过来的参数是否完整
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		LoginRequest request = new LoginRequest();
		// request.setLoginName("201492136");
		// request.setPassword("123456");
		request.setLoginName(requestParams.get(paras[0]));
		request.setPassword(requestParams.get(paras[1]));
		LoginResponse response = null;
		try {
			logger.debug(" start to get the login response");
			response = userService.login(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the login reponse", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("login response is null and fail to get the login response");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}

		/*
		 * //设置cookie attention:cookie的设置是前端来设置的
		 * 
		 * try { logger.debug("start to set cookie");
		 * CookieUtil.setCookie(requestHttp, responseHttp, key); } catch
		 * (Exception e) { // TODO: handle exception
		 * logger.error("fail to set cookie",e); }
		 */

		return new ApiResult(response);
	}

	/**
	 * @RequestMapping("helloworld")
	 * 
	 * @ResponseBody public String helloworld() { return "helloworld"; }
	 **/
	@RequestMapping("exit")
	private @ResponseBody ApiResult exit(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "ticket" };
		// 检查传过来的参数是否完整
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}

		ExitRequest request = new ExitRequest();
		request.setSessionKey(requestParams.get(paras[0]));

		BasicResponse response = null;
		try {
			logger.debug("start to exit using userService ");
			response = userService.exit(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the exit reponse", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("exit response is null and fail to get the exit response");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("grade")
	private @ResponseBody ApiResult getAllGrades(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		GradeInfoResponse response = null;
		try {
			logger.debug("start to get all grade using userService");
			response = userService.getGradeInfo();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the gradeInfo reponse", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("gradeInfo response is null and fail to get the gradeInfo response");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		logger.debug("get gradeInfoResponse successfully!");
		return new ApiResult(response);
	}

	@RequestMapping("class")
	private @ResponseBody ApiResult getAllClasses(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "grade" };
		// 检查传过来的参数是否完整
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		ClassInfoRequest request = new ClassInfoRequest();
		request.setGrade(requestParams.get(paras[0]));
		ClassInfoResponse response = null;
		try {
			logger.debug("start to get classes using userService");
			response = userService.getClassInfo(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the classInfoReponse", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("classInfoReponse is null and fail to get the classInfoReponse");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		logger.debug("get classInfoResponse successfully!");
		return new ApiResult(response);
	}

	@RequestMapping("users")
	private @ResponseBody ApiResult getUserByClass(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "class" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		GetUsersRequest request = new GetUsersRequest();
		request.setClass_info(requestParams.get(paras[0]));
		GetUsersResponse response = null;
		try {
			logger.debug("get the userList using userService ");
			response = userService.getUserListByClass(request);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the GetUsersResponse", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("GetUsersResponse is null and fail to get the GetUsersResponse");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		logger.debug("get GetUsersResponse successfully!");
		return new ApiResult(response);
	}

	@RequestMapping("homepage")
	private @ResponseBody ApiResult getUserInfo(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "stuid", "is_manageId" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		UserInfoRequest request = new UserInfoRequest();
		request.setStuid(requestParams.get(paras[0]));
		request.setIs_manageId(DataTypePaserUtil.StringToInteger(requestParams.get(paras[1])));

		HomePageResponse response = null;
		try {
			logger.debug(" start to get the home page info and work list");
			response = userService.getPersonalHomePage(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("fail to get the homepage Info", e);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("HomePage response is null and fail to get the HomePage response");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}

	@RequestMapping("edit")
	private @ResponseBody ApiResult modifyPersonInfo(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "stuid", "nickName", "name", "gender", "self_introduction", "phone", "email" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}

		PersonInfoEditRequest request = new PersonInfoEditRequest();
		request.setStuid(requestParams.get(paras[0]));
		request.setNickName(requestParams.get(paras[1]));
		request.setName(requestParams.get(paras[2]));
		request.setGender(requestParams.get(paras[3]));
		request.setSelf_introduction(requestParams.get(paras[4]));
		request.setEmail(requestParams.get(paras[5]));
		request.setPhone(requestParams.get(paras[6]));

		PersonInfoEditResponse response = null;
		try {
			logger.debug("start update all person info using userService");
			response = userService.modifyPersonInfo(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" userService error", e);
			return ApiResultFactory.getServerError();

		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error("PersonInfoEditResponse  is null and fail to modify the user info");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}

	@RequestMapping("headimg/update")
	private @ResponseBody ApiResult updateHeadPicture(HttpServletRequest requestHttp,
			HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "ticket", "headImg" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		// 除了登陆和退出外都需要检查sessionKey来获取用户的唯一标识stuid
		String stuid = RedisUtil.get(requestParams.get(paras[0]));

		Head_urlUpdateRequest request = new Head_urlUpdateRequest();
		request.setStuid(stuid);
		request.setHead_url(requestParams.get(paras[1]));
		Head_urlUpdateResponse response = null;
		try {
			logger.debug(" start to update head_url using userService");
			response = userService.updateHead_url(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userService error", e);
			return ApiResultFactory.getServerError();
		}
		if (response == null) {
			// 判断服务是否正常返回
			logger.error("Basic response is null and fail to update  the user head_url ");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("password/edit")
	private @ResponseBody ApiResult updatePassword(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "stuid", "oldPassword", "newPassword" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}

		PasswordUpdateRequest request = new PasswordUpdateRequest();
		request.setStuid(requestParams.get(paras[0]));
		request.setOldPassword(requestParams.get(paras[1]));
		request.setNewPassword(requestParams.get(paras[2]));
		BasicResponse response = null;
		try {
			logger.debug("start to update the password using userService");
			response = userService.updatePassword(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		if (response == null) {
			// 判断服务是否正常返回
			logger.error("BgImgUpdateResponse is null and fail to update  the user background_url ");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	/*** 以下是管理员对用户进行的操作 ***/

	@RequestMapping("allusers")
	private @ResponseBody ApiResult getAllUsers(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "ticket" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		String stuid = RedisUtil.get(requestParams.get(paras[0]));
		UserInfoRequest request = new UserInfoRequest();
		request.setStuid(stuid);
		AllUserResponse response = null;
		try {
			logger.debug(" get the alluser info using userService");
			response = userService.getAllUser(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		if (response == null) {
			// 判断服务是否正常返回
			logger.error("AllUserResponse is null and fail to all user info ");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("adduser")
	private @ResponseBody ApiResult addUser(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "stuid", "name", "grade", "classInfo" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		AddUserRequest request = new AddUserRequest();
		request.setStuid(requestParams.get(paras[0]));
		request.setName(requestParams.get(paras[1]));
		request.setGrade(requestParams.get(paras[2]));
		request.setClassInfo(requestParams.get(paras[3]));
		AddUserResponse response = null;
		try {
			logger.debug(" start to add user using user Service");
			response = userService.addUser(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		if (response == null) {
			// 判断服务是否正常返回
			logger.error("add user Response is null and fail to add user  ");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("delete/user")
	private @ResponseBody ApiResult deleteUser(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "list" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		DeleteUserRequest request = new DeleteUserRequest();
		request.setStuid(requestParams.get(paras[0]));
		DeleteUserResponse response = null;
		try {
			logger.debug(" start to delete user using userService");
			response = userService.deleteUser(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		if (response == null) {
			// 判断服务是否正常返回
			logger.error("delete user Response is null and fail to delete user  ");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}

	@RequestMapping("detail_submit")
	private @ResponseBody ApiResult submitUserDetailIntro(HttpServletRequest requestHttp,
			HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "content" };
		AddDetailIntroRequest request = new AddDetailIntroRequest();
		request.setContent(requestParams.get(paras[0]));
		request.setStuid("201492136");
		AddDetailIntroResponse response = null;
		try {
			logger.debug(" start to add detail intro using userService");
			response = userService.addDetailIntro(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		if (response == null) {
			// 判断服务是否正常返回
			logger.error("add detail_intro Response is null and fail to add user detail_intro");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("is_login")
	private @ResponseBody ApiResult topicSetting(HttpServletRequest requestHttp) {
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "ticket" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		String stuid = RedisUtil.get(requestParams.get(paras[0]));
		if (stuid.equals(null)) {
			logger.error("sessionkey invalid !");
			return ApiResultFactory.getSessionKeyError();
		}
		JudgeIsLoginRequest request = new JudgeIsLoginRequest();
		request.setStuid(stuid);
		JudgeIsLoginResponse response = null;
		try {
			logger.debug("start to set topic status from using topicService ");
			response = userService.judgeIsLogin(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {

			logger.error("JudgeIsLogin  response is null");
			return ApiResultFactory.getServerError();
		}

		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {

			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);

	}

	@RequestMapping("userinfo")
	private @ResponseBody ApiResult getPersonInfo(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "stuid" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		UserInfoRequest request = new UserInfoRequest();
		request.setStuid(requestParams.get(paras[0]));
		LoginResponse response = null;
		try {
			logger.debug("start to get the person info using userService");
			response = userService.GetPersonInfo(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error(" LoginResponse  response is null");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}

	@RequestMapping("manager/add")
	private @ResponseBody ApiResult addManager(HttpServletRequest requestHttp, HttpServletResponse responseHttp) {
		responseHttp.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> requestParams = RequestUtil.getParameterMap(requestHttp);
		String[] paras = { "stuid", "name", "homeLink" };
		boolean flag = RequestUtil.validate(paras, requestParams);
		if (flag == false) {
			logger.error(ApiResultInfo.ResultMsg.RequiredParasError);
			return ApiResultFactory.getLackParasError();
		}
		addManagerRequest request = new addManagerRequest();
		request.setStuid(requestParams.get(paras[0]));
		request.setName(requestParams.get(paras[1]));
		request.setHomeLink(requestParams.get(paras[2]));
		BasicResponse response = null;
		try {
			logger.debug("add manager using userService");
			response = userService.addManager(request);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(ApiResultInfo.ResultMsg.ServerError);
			return ApiResultFactory.getServerError();
		}
		// 判断服务是否正常返回
		if (response == null) {
			logger.error(" addManager  response is null");
			return ApiResultFactory.getServerError();
		}
		// 通过返回码的数值，判断服务结果是否为正确的结果
		if (response.getCode() != 0) {
			logger.error("there are errors in service");
			return new ApiResult(response.getCode(), response.getMsg());
		}
		return new ApiResult(response);
	}

}
