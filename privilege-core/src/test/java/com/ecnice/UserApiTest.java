package com.ecnice;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ecnice.privilege.api.privilege.IUserRoleApi;
import com.ecnice.privilege.constant.PrivilegeConstant;

public class UserApiTest {
	
	private IUserRoleApi userRoleApi;
	
	@Before
	public  void setUpBeforeClass()  {
		try {
			@SuppressWarnings("resource")
			ApplicationContext cxt = new ClassPathXmlApplicationContext(
					new String[] { "file:src/main/resources/config/spring_common.xml"});
			userRoleApi = (IUserRoleApi) cxt.getBean("userRoleApiImpl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(MD5Util.getMD5String(PrivilegeConstant.USER_PASSWORD_FRONT + PrivilegeConstant.DEFAULT_USER_PASSWORD));
	}
}
