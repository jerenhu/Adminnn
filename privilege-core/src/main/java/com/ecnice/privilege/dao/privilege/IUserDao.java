package com.ecnice.privilege.dao.privilege;

import java.util.List;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.PrivilegeException;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.vo.privilege.PrivilegeVo;

/**
 * @Title:
 * @Description:用户Dao
 * @Author:Bruce.Liu
 * @Since:2014年3月31日
 * @Version:1.1.0
浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
public interface IUserDao {
	
	/**
	 * 通过系统标识得到系统下面的所有的人
	 * @param systemSn 系统标识
	 * @return
	 */
	public List<User> getSystemUsersBySystemSn(String systemSn) ;

	/**
	 * @param User
	 * @throws Exception
	 * @Description:添加用户
	 */
	public void insertUser(User user) throws Exception;
	
	/**
	 * 批量添加
	 * @param users
	 * @throws Exception
	 * @Description:
	 */
	public void insertUserBatch(List<User> users) throws Exception;
	
	/**
	 * 批量同步数据
	 * @param users
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 上午9:46:23
	 */
	public int updateSyncUserList(List<User> users) throws Exception;
	
	/**
	 * 批量修改
	 * @param users
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午1:14:54
	 */
	public int updateUserBatch(List<User> users) throws Exception;

	/**
	 * @param User
	 * @throws Exception
	 * @Description:更新用户
	 */
	public void updateUser(User user) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 * @Description:删除用户
	 */
	public void delUser(String id) throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @Description:根据id查询用户对象
	 */
	public User getUserById(String id) throws Exception;
	
	/**
	 * 根据用户名查用户
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public User getUserByUserName(String userName) throws Exception;

	/**
	 * @param User
	 * @param query
	 * @return
	 * @throws Exception
	 * @Description: 分页查询用户列表
	 */
	public PagerModel<User> getPagerModel(User user, Query query)
			throws Exception;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 * @Description:获取所有用户列表
	 */
	public List<User> getAll(User user) throws Exception;

	/**
	 * @param username
	 * @param password
	 * @return
	 * @Description:用户登录
	 */
	public User login(String username, String password) throws Exception;

	/**
	 * 
	 * @param roleSn
	 * @return
	 * @throws Exception
	 * @Description:根据角色标识获取用户列表
	 */
	public List<User> getUserByRoleSn(String roleSn) throws Exception;

	/**
	 * 通过Vo查询用户列表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<User> getUsersByPrivilegeVo(PrivilegeVo vo) throws Exception;
	/**
	 * 通过角色标示和部门id得到用户列表
	 * 
	 * @param deptId
	 * @param roleSn
	 * @return
	 */
	public List<User> getUserByDeptIdAndRoleSn(String deptId, String roleSn)throws PrivilegeException;
	
	/**
	 * @Description:通过角色标识获取用户列表
	 * @param roleSns 可传入多个标识
	 * @return
	 * @throws PrivilegeException
	 */
	public List<User> getUserByRoleSns(String... roleSns)throws PrivilegeException;
	
	/**
	 * @Description:通过角色标识获取用户列表
	 * @param roleSns 可传入多个标识
	 * @return
	 * @throws PrivilegeException
	 */
	public List<User> getUserByRoleSns(User user,String... roleSns)throws PrivilegeException;
	
	/**
	 * 
	 * @Description:根据部门id获取该部门下得用户
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public List<User> getUsersByDeptId(String deptId)throws Exception;
	
	//---api---
	/**
	 * 通过用户名查询用户的信息【只返回realName和username】
	 * @param userName
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年5月31日 上午9:30:14
	 */
	public List<User> getUserByUserNames(String... userName);
	
	/**
	 * 得到用户的失效日期(账户和密码)
	 * @param user
	 * @param query
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年6月6日 下午8:45:59
	 */
	public PagerModel<User> getUserFailTime(User user,Query query);

	/**
	 * 通过ids批量得到人员管理
	 * @param ids
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author xietongjian 2017 下午4:23:15
	 */
	public List<User> getUserByIdsList(List<String> ids) throws Exception;

}
