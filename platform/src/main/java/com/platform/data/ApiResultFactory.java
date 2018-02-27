package com.platform.data;


public class ApiResultFactory {

	public static ApiResult getUnkownMethod() {

		return new ApiResult(ApiResultInfo.ResultCode.UnkownMethodError, ApiResultInfo.ResultMsg.UnkownMethodError);
	}

	public static ApiResult getUnkownError() {

		return new ApiResult(ApiResultInfo.ResultCode.UnkownError, ApiResultInfo.ResultMsg.UnkownError);
	}

	public static ApiResult getLackParasError() {

		return new ApiResult(ApiResultInfo.ResultCode.RequiredParasError, ApiResultInfo.ResultMsg.RequiredParasError);
	}

	public static ApiResult getJsonPaserError() {

		return new ApiResult(ApiResultInfo.ResultCode.JsonPaserError, ApiResultInfo.ResultMsg.JsonPaserError);
	}

	public static ApiResult getServerError() {

		return new ApiResult(ApiResultInfo.ResultCode.ServerError, ApiResultInfo.ResultMsg.ServerError);
	}

	public static ApiResult getDataTypePaserError() {

		return new ApiResult(ApiResultInfo.ResultCode.DataTypePaserError, ApiResultInfo.ResultMsg.DataTypePaserError);
	}
	public static ApiResult getUploadFileError() {

		return new ApiResult(ApiResultInfo.ResultCode.UploadFileError, ApiResultInfo.ResultMsg.UploadFileError);
	}
	public static ApiResult getSessionKeyError(){
		
		return new ApiResult(ApiResultInfo.ResultCode.SessionKeyInvalid, ApiResultInfo.ResultMsg.SessionKeyInvalid);
		
	}

	
}
