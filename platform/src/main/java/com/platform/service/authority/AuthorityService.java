package com.platform.service.authority;

import com.platform.model.BasicResponse;
import com.platform.rmodel.authority.ManagerAuthorityRequest;
import com.platform.rmodel.authority.ManagerAuthorityResponse;
import com.platform.rmodel.authority.StudentAuthorityResponse;
import com.platform.rmodel.authority.saveManagerAuthorityRequest;
import com.platform.rmodel.authority.saveStudentAuthorityRequest;

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
	/**
	 * 保存学生的权限
	 * @param request
	 * @return
	 */
	public BasicResponse saveStudentAuthority(saveStudentAuthorityRequest request);
	/**
	 * 保存管理员的管理的学生权限
	 * @param request
	 * @return
	 */
	public BasicResponse saveManagerAuthority(saveManagerAuthorityRequest request);

}
