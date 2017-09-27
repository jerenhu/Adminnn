package com.ecnice.privilege.service.privilege.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ecnice.privilege.common.PagerModel;
import com.ecnice.privilege.common.Query;
import com.ecnice.privilege.dao.privilege.IAclDao;
import com.ecnice.privilege.dao.privilege.IICSystemDao;
import com.ecnice.privilege.dao.privilege.IModuleDao;
import com.ecnice.privilege.dao.privilege.ISystemPrivilegeValueDao;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Module;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.tools.common.UUIDGenerator;

@Service
public class ICSystemServiceImpl implements IICSystemService {
    private static final Logger logger = Logger.getLogger(ICSystemServiceImpl.class);
    @Resource
    private IICSystemDao iicSystemDao;
    @Resource
    private IModuleDao moduleDao;
    @Resource
    private IAclDao aclDao;
    @Resource
    private ISystemPrivilegeValueDao systemPrivilegeValueDao;

    @Override
    public List<ICSystem> getICSystemsByAcls(Set<ACL> acls) throws Exception {
        Set<String> systemSns = null;
        if (CollectionUtils.isNotEmpty(acls)) {
            systemSns = new HashSet<String>();
            for (ACL acl : acls) {
                systemSns.add(acl.getSystemSn());
            }
        }
        //logger.info("查询到的系统有", (Throwable) systemSns);
        if (CollectionUtils.isNotEmpty(systemSns))
            return iicSystemDao.getICSystemsBySns(systemSns);
        else
            return null;
    }

    @Override
    public ICSystem getICSystemBySn(String sn) {
        return iicSystemDao.getICSystemBySn(sn);
    }

    /**
     * 添加系统
     */
    @Override
    public void insertIcSystem(ICSystem icSystem) throws Exception {
        icSystem.setId(UUIDGenerator.generate());
        this.iicSystemDao.insertIcSystem(icSystem);
        //初始化权限值
        systemPrivilegeValueDao.initPval(icSystem.getId());
    }

    /**
     * 修改系统
     */
    @Override
    public void updateIcSystem(ICSystem icSystem) throws Exception {
        //判断是否需要修改其它模块关联的系统标识
        ICSystem oldIcSystem = this.iicSystemDao.getICSystemById(icSystem.getId());
        if (!oldIcSystem.getSn().equals(icSystem.getSn())) {
            this.aclDao.updateSystemSn(icSystem.getSn(), oldIcSystem.getSn());
        }
        this.iicSystemDao.updateIcSystem(icSystem);
    }

    /**
     * 删除系统
     */
    @Override
    public void deleteIcSystem(String id) throws Exception {
        this.iicSystemDao.deleteIcSystem(id);
    }

    @Override
    public void deleteIcSystems(String[] ids) throws Exception {
        for (String id : ids) {
            this.iicSystemDao.deleteIcSystem(id);
        }
    }

    public boolean exsitsModule(String[] ids) throws Exception {
        for (String id : ids) {
            Module module = new Module();
            module.setSystemId(id);
            List<Module> list;
            list = this.moduleDao.getAllModule(module);
            if (list != null && list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有系统列表
     */
    @Override
    public List<ICSystem> getAllIcSystem(ICSystem icSystem) throws Exception {
        return this.iicSystemDao.getAllIcSystem(icSystem);
    }

    /**
     * 分页获取系统列表
     */
    @Override
    public PagerModel<ICSystem> getPagerIcSystem(ICSystem icSystem, Query query)
            throws Exception {
        return this.iicSystemDao.getPagerIcSystem(icSystem, query);
    }

    /**
     * 根据id获取系统对象
     */
    @Override
    public ICSystem getICSystemById(String id) throws Exception {
        return this.iicSystemDao.getICSystemById(id);
    }

    @Override
    public List<ICSystem> getICSystemsByUserId(String userId) {
        return iicSystemDao.getICSystemsByUserId(userId);
    }
}
