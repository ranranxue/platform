package com.platform.data;

public class ApiResultInfo {

	private ApiResultInfo() {

	}

	public class ResultMsg {

		public final static String JsonPaserError = "json paser error";
		public final static String RequiredParasError = "lack necessary parameters";
		public final static String UnkownMethodError = "api method unknown";
		public final static String UnkownError = "system unkown error";
		public final static String ServerError = "system server error";
		public final static String DataTypePaserError = "Data Type Paser Error";
		public final static String FailToLogin = "Fail to Login";
		public final static String ConfirmUserExist = "user does not exist!";
		public final static String PasswordError = "password is error！";
		public final static String UserSessionError="user session error";
		public final static String UserExitError="user exit error";
		public final static String duplicateEmail="the email is duplicate !";
		public final static String duplicatePhone="the phone is duplicate!";
		public final static String duplicateStuid="the stuid  is duplicate!";
		public final static String closetopic="the topic is closed";
		public final static String userforbidden="The user is forbidden";
		public final static String UploadFileError="Upload File Error";
		public final static String SaveLocalFileError="save local file error";
		public final static String SessionKeyInvalid="sessionKey invalid";
		
	}
	public class ResultCode {

		public final static int JsonPaserError = 1;
		public final static int RequiredParasError = 2;
		public final static int UnkownMethodError = 3;
		public final static int UnkownError = 4;
		public final static int ServerError = 5;
		public final static int DataTypePaserError = 6;
		public final static int FailToLogin = 7;
		public final static int ConfirmUserExist = 8;
		public final static int PasswordError = 9;
		public final static int UserSessionError=10;
		public final static int UserExitError=11;
		public final static int duplicateEmail=12;
		public final static int duplicatePhone=13;
		public final static int duplicateStuid=14;
		public final static int closetopic=15;
		public final static int userforbidden=16;
		public final static int UploadFileError=17;
		public final static int SaveLocalFileError=18;
		public final static int SessionKeyInvalid=19;
	}

}
