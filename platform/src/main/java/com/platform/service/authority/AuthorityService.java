package com.platform.service.authority;

import com.platform.rmodel.authority.ManagerAuthorityRequest;
import com.platform.rmodel.authority.ManagerAuthorityResponse;
import com.platform.rmodel.authority.StudentAuthorityResponse;

public interface AuthorityService {
	/**
	 * 获取学生的权限
	 * @return
	 */
	public StudentAuthorityResponse getStudentAuthority();
	/**
	 * 获取管理员的权限
	 * @return
	 */
	public ManagerAuthorityResponse getManagerAuthority(ManagerAuthorityRequest request);
	

}
