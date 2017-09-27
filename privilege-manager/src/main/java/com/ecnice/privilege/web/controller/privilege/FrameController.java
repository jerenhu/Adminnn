package com.ecnice.privilege.web.controller.privilege;

import com.ecnice.privilege.cache.CacheEntity;
import com.ecnice.privilege.cache.CacheListHandler;
import com.ecnice.privilege.common.SessionMap;
import com.ecnice.privilege.constant.PrivilegeConstant;
import com.ecnice.privilege.model.privilege.ACL;
import com.ecnice.privilege.model.privilege.ICSystem;
import com.ecnice.privilege.model.privilege.Module;
import com.ecnice.privilege.model.privilege.User;
import com.ecnice.privilege.model.system.SystemConfig;
import com.ecnice.privilege.service.privilege.IAclService;
import com.ecnice.privilege.service.privilege.IICSystemService;
import com.ecnice.privilege.service.privilege.IModuleService;
import com.ecnice.privilege.service.system.ISystemConfigService;
import com.ecnice.privilege.utils.DateUtil;
import com.ecnice.privilege.utils.JsonUtils;
import com.ecnice.privilege.utils.WebUtils;
import com.ecnice.privilege.vo.privilege.Node;
import com.ecnice.privilege.web.controller.BaseController;
import com.ecnice.tools.common.ReadProperty;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title:
 * @Description:后台中心
 * @Author:Bruce.Liu
 * @Since:2014年4月3日
 * @Version:1.1.0 浙江蘑菇加电子商务有限公司 2014 ~ 2015 版权所有
 */
@Controller
@RequestMapping("/managment/frame")
public class FrameController extends BaseController {
    private static final Logger logger = Logger.getLogger(FrameController.class);
    @Resource
    private IICSystemService iIcSystemService;
    @Resource
    private IModuleService moduleService;
    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private ReadProperty readProperty;
    @Resource
    private IAclService aclService;

    public static void main(String[] args) {
        String url = "http://192.168.50.174:8080/privilege-manager/managment/frame/index.do?sessionId=CBDF9A3C9656DF7119AD9164BCCF676D";
        url = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
        System.out.println(url);
    }

    // 展示系统树
    @ResponseBody
    @RequestMapping("/systemTree")
    public String systemTree(HttpServletRequest request, String systemId, String url, String sessionId) {
        List<Node> nodes = null;
        try {
            SystemConfig config = systemConfigService.getSystemConfigsByKey(PrivilegeConstant.IS_PROD).get(0);
            //
            if (config.getConfigValue().equals("true") && url.indexOf(config.getConfigSn()) == -1
                    && url.indexOf(getIpAddr(request)) == -1) {
                String projectName = url.substring(url.lastIndexOf(":"));
                // url = "http://"+getIpAddr(request)+projectName;
                url = "http://127.0.0.1" + projectName;
            }

            // 1：判断用户是否管理员还是普通用户.
            User user = null;
            CacheEntity cacheEntity = CacheListHandler.getCache(sessionId);
            if (null == cacheEntity) {
                logger.info("从缓存中获取cacheEntity对象失败，请确认!");
                return JsonUtils.toJson(new ArrayList<Node>());
            }
            SessionMap sessionMap = (SessionMap) cacheEntity.getCacheContext();
            if (null == sessionMap) {
                logger.info("从缓存中获取sessionMap对象失败，请确认!");
                return JsonUtils.toJson(new ArrayList<Node>());
            }
            Object objUser = sessionMap.get(PrivilegeConstant.LOGIN_USER);
            logger.info("userJson：" + (objUser == null ? "" : objUser));
            if (objUser == null) {
                logger.info("从缓存中获取user对象失败，请确认!");
                return JsonUtils.toJson(new ArrayList<Node>());
            }

            String userJson = (String) objUser;
            logger.info("userJson：" + userJson);
            user = (User) JsonUtils.jsonToObj(userJson, User.class);
            // user = (User)
            // WebUtils.getSession(request).getAttribute(PrivilegeConstant.LOGIN_USER);
            // Set<ACL> acls = (Set<ACL>)
            // WebUtils.getSession(request).getAttribute(PrivilegeConstant.LOGIN_USER_ACLS);
            Object objAcls = sessionMap.get(PrivilegeConstant.LOGIN_USER_ACLS);
            if (objAcls == null) {
                logger.info("从缓存中获取Acls对象失败，请确认!");
                return JsonUtils.toJson(new ArrayList<Node>());
            }
            String aclJson = (String) objAcls;
            Type type = new TypeToken<ArrayList<ACL>>() { }.getType();
            
            List<ACL> list = JsonUtils.getGson().fromJson(aclJson, type);
            Set<ACL> acls = new HashSet<ACL>(list);
            List<Module> modules = null;
            if (user != null) {
                if (StringUtils.isBlank(systemId)) {
                    logger.info("用户：" + user.getUsername() + "的系统id为空！！");
                } else {
                    modules = moduleService.getTreeModuleBySystemIdAndAcls(acls, systemId);
                }
            }
            nodes = this.getNodes(modules, url, sessionId);

        } catch (Exception e) {
            logger.error("FrameController-systemTree:", e);
            e.printStackTrace();
        }

        if (CollectionUtils.isEmpty(nodes)) {
            nodes = new ArrayList<Node>();
        }
        return JsonUtils.toJson(nodes);
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // 得到模块树
    private List<Node> getNodes(List<Module> modules, String url, String sessionId) throws Exception {
        if (modules == null) {
            return null;
        }
        List<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < modules.size(); i++) {
            Module m = modules.get(i);
            Node node = new Node();
            if (StringUtils.isNotBlank(m.getPid())) {
                node.setId(m.getId());
                node.setPid(m.getPid());
                node.setText(m.getName());
                if (m.getUrl().indexOf("?") == -1) {
                    node.setUrl(url + m.getUrl() + "?sessionId=" + sessionId);
                } else {
                    node.setUrl(url + m.getUrl() + "&sessionId=" + sessionId);
                }
                nodes.add(node);
            } else {
                if (moduleService.checkChildren(m.getId())) {
                    node.setId(m.getId());
                    node.setText(m.getName());
                    node.setIsLeaf(false);
                    node.setExpanded(true);
                    nodes.add(node);
                } else {
                    node.setId(m.getId());
                    node.setPid(m.getPid());
                    node.setText(m.getName());
                    if (m.getUrl().indexOf("?") == -1) {
                        node.setUrl(url + m.getUrl() + "?sessionId=" + sessionId);
                    } else {
                        node.setUrl(url + m.getUrl() + "&sessionId=" + sessionId);
                    }
                    nodes.add(node);
                }
            }
        }
        return nodes;
    }

    // 后台系统的首页界面
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model, String sessionId) {
//    	response.setHeader("sessionId", sessionId);
//    	request.getHeader("sessionId");	
    	logger.info(request.getHeader("sessionId"));
        model.addAttribute("sessionId", sessionId);
        model.put("copy", PrivilegeConstant.COMPANY_COPY);
        // 得到系统配置信息
        super.getSystemConfigsInfoToModelMap(request, model);

        //1、检查账号有效期
        User user = null;
        try {
            user = (User) WebUtils.getSession(request).getAttribute(PrivilegeConstant.LOGIN_USER);
            if (null != user && null != user.getFailureTime()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int day = DateUtil.diffDate(sdf.parse(sdf.format(user.getFailureTime())), sdf.parse(sdf.format(new Date())));
                if (day > 0 && day <= 7) {
                    model.put("warnDay", day);
                } else if (day <= 0) { // 过期了-直接登录
                    return "/frame/login";
                }
            }
        } catch (Exception e1) {
            logger.error("验证账号有效期失败【user=" + user + "】", e1);
            return "/frame/login";
        }
        if (user != null) {
            boolean pwdFlag = true;
            try {
                //2、验证密码有效性
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //0.1 初始化密码未修改
                if (user.getPwdInit().intValue() != PrivilegeConstant.SUCCESS) {// 初始化密码未修改
                    pwdFlag = false;
                    model.put("pwdMsg", "初始化密码未修改，请重置密码。");
                }
                if (pwdFlag) {
                    //0.2 密码过期
                    int dayPwd = DateUtil.diffDate(sdf.parse(sdf.format(user.getPwdFtime())), sdf.parse(sdf.format(new Date())));
                    if (dayPwd <= 0) {// 密码过期
                        pwdFlag = false;
                        model.put("pwdMsg", "密码已过期，请重置密码！");
                    } else if (dayPwd <= PrivilegeConstant.PWD_WARN_DAY) {
                        model.put("pwdWarnDay", dayPwd);//过期天数
                    }
                }
            } catch (ParseException e1) {
                logger.error("验证密码有效性失败【user=" + user + "】", e1);
                e1.printStackTrace();
                return "/frame/login";
            }
            if (!pwdFlag) {
                return "/frame/index";
            }

            List<ICSystem> systems = null;
            try {
                if (user.getUsername().equals(readProperty.getValue("system.admin"))) {
                    systems = iIcSystemService.getAllIcSystem(null);
                } else {
//                    systems = iIcSystemService.getICSystemsByUserId(user.getId());
                    systems = iIcSystemService.getICSystemsByAcls(aclService.getSessionAcls(sessionId));
                    // 给登录用户的设置都放里面去
//					ICSystem icSystem = iIcSystemService.getICSystemBySn("setup");
//					if (icSystem != null) {
//						systems.add(icSystem);
//					}
                }
                if (systems != null && systems.size() > 0) {
                    ICSystem system = systems.get(0);
                    model.addAttribute("systemName", system.getName());
                }else {
                    logger.error(user + "没有获取到用户系统");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            model.addAttribute("systems", systems);
        }
        return "/frame/index";
    }
}
