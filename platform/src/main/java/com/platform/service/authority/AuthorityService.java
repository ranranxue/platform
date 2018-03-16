package com.platform.service.authority;

import com.platform.rmodel.authority.StudentAuthorityResponse;

public interface AuthorityService {
	/**
	 * 获取学生的权限
	 * @return
	 */
	public StudentAuthorityResponse getStudentAuthority();
	

}
