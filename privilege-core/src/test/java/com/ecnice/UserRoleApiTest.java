package com.ecnice;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ecnice.privilege.api.privilege.IUserRoleApi;
import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.Role;
import com.ecnice.privilege.model.privilege.UserRole;
import com.ecnice.privilege.vo.ReturnVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/config/spring_common.xml")
public class UserRoleApiTest {
	private static Logger logger = Logger.getLogger(UserRoleApiTest.class);

	@Autowired
	private IUserRoleApi userRoleApi;
	
	
	@Test
	public void testGetAllUserRole(){
		UserRole userRole = new UserRole();
		userRole.setUserNo("00000005");
		ReturnVo<UserRole> returnVo = this.userRoleApi.getUserRolesByQuery(userRole);
		logger.info(returnVo.getDatas());
		if(PrivilegeConstant.SUCCESS_CODE.equals(returnVo.getStatus())){
			List<UserRole> lst = returnVo.getDatas();
			for (UserRole ur : lst) {
				System.out.println(ur);
			}
		}
	}
	@Test
	public void getRolePageModel(){
		UserRole userRole = new UserRole();
		userRole.setUserNo("00004845");
		ReturnVo<UserRole> returnVo = this.userRoleApi.getUserRolesByQuery(userRole);
		logger.info(returnVo.getDatas());
		if(PrivilegeConstant.SUCCESS_CODE.equals(returnVo.getStatus())){
			List<UserRole> lst = returnVo.getDatas();
			for (UserRole ur : lst) {
				System.out.println(ur);
			}
		}
	}
	
	
	@Test
	public void testGetRolesByPageModel(){
		Role role = new Role();
		Query query = new Query();
		ReturnVo<PagerModel<Role>> returnVo = this.userRoleApi.getRolesByQuery(role, query);
		logger.info(returnVo.getDatas());
		if(PrivilegeConstant.SUCCESS_CODE.equals(returnVo.getStatus())){
			PagerModel<Role> pm = returnVo.getData();
			if(CollectionUtils.isNotEmpty(pm.getDatas())){
				for (Role r : pm.getDatas()) {
					System.out.println(r);
				}
			}
		}
	}
	
}
