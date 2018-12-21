package com.apiserver.producer.system.controller;

import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.*;
import com.apiserver.model.system.entity.SUserLog;
import com.apiserver.model.system.entity.SUsers;
import com.apiserver.producer.system.service.UserLogService;
import com.apiserver.producer.system.service.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@Api(description = "用户信息管理", tags = "UsersController", basePath = "/users")
@RestController
@RequestMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    UserLogService userLogService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据用户ID，查询用户的详细信息", notes = "返回单个用户的信息,菜单详细信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public SUsers get(@RequestParam(value = "id") Long id) {
        return usersService.dynaSelectByPrimaryKey(SUsers.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据查询的参数，查询用户的列表信息", notes = "返回用户的列表信息")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public PageInfo<SUsers> getAll(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize,
                                   @RequestParam(value = "account", required = false) String account, @RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "loginType", required = false) Integer loginType) {


        PageHelper.startPage(pageNum, pageSize);
        List<String> likeParam = new ArrayList<>();
        likeParam.add("account");
        likeParam.add("email");
        likeParam.add("mobile");

        Map<String, Object> orderMap = new LinkedHashMap<>();
        orderMap.put("registerTime", "desc");
        List<SUsers> sUsersList = usersService.dynaSelectAllLike(SUsers.builder().account(account)
                .mobile(mobile)
                .email(email)
                .status(status)
                .loginType(loginType)
                .build(), likeParam, orderMap);

        PageInfo<SUsers> pageInfo = new PageInfo<>(sUsersList);
        return pageInfo;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存用户的信息", notes = "保存用户的信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody SUsers usersEntity) {
        Map<String, Object> hashMap = new HashMap<>();
        SUsers users = usersService.dynaSelectByPrimaryKey(SUsers.builder().id(usersEntity.getId()).build());
        if (users == null) {
            Map<String, Object> orMap = new HashMap<>();
            orMap.put("account", usersEntity.getAccount());
            orMap.put("mobile", usersEntity.getMobile());
            orMap.put("email", usersEntity.getEmail());
            int count = usersService.fetchByAllOrParameters(orMap);
            if (count > 0) {
                hashMap.put("statusCode", StatusCode.AccountAlreadyExists.getCode().toString());
                hashMap.put("statusMessage", StatusCode.AccountAlreadyExists.getMeaning());
            } else {
                if (StringUtils.isEmpty(usersEntity.getPwd())) {
                    String pwd = null;
                    if (StringUtils.isNotEmpty(usersEntity.getMobile())) {
                        pwd = usersEntity.getMobile();
                    } else if (StringUtils.isNotEmpty(usersEntity.getAccount())) {
                        pwd = usersEntity.getAccount();
                    } else if (StringUtils.isNotEmpty(usersEntity.getEmail())) {
                        pwd = usersEntity.getEmail();
                    }
                    if (null != pwd) {
                        String encryptedPwd = MD5Util.getMD5String(pwd);
                        usersEntity.setPwd(encryptedPwd);
                    }
                } else {
                    String encryptedPwd = MD5Util.getMD5String(usersEntity.getPwd());
                    usersEntity.setPwd(encryptedPwd);
                }
                usersService.dynaInsert(usersEntity);
            }

        }
        return hashMap;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存用户的信息", notes = "保存用户的信息")
    @RequestMapping(value = "edite", method = RequestMethod.POST)
    public Map<String, Object> edite(@RequestBody SUsers usersEntity) {

        Map<String, Object> result = new HashMap<>();
        if (null == usersEntity.getId()) {
            result.put("statusCode", StatusCode.primaryKey_IdNotNull.getCode().toString());
            result.put("statusMessage", StatusCode.primaryKey_IdNotNull.getMeaning());
            return result;
        }
        SUsers users = usersService.dynaSelectByPrimaryKey(SUsers.builder().id(usersEntity.getId()).build());
        if (null == users) {
            result.put("statusCode", StatusCode.NonExistentUser.getCode().toString());
            result.put("statusMessage", StatusCode.NonExistentUser.getMeaning());
            return result;
        } else {
            Map<String, Object> paraMap = new HashMap<>();
            String accountSource = users.getAccount();
            String mobileSource = users.getMobile();
            String emailSource = users.getEmail();

            String account = usersEntity.getAccount();
            String mobile = usersEntity.getMobile();
            String email = usersEntity.getEmail();

            if (StringUtils.isNotEmpty(account) && !account.equals(accountSource)) {
                paraMap.put("account", account);
            } else if (StringUtils.isNotEmpty(mobile) && !mobile.equals(mobileSource)) {
                paraMap.put("mobile", mobile);
            } else if (StringUtils.isNotEmpty(email) && !email.equals(emailSource)) {
                paraMap.put("email", email);
            }

            if (!MapUtils.isEmpty(paraMap)) {
                int count = usersService.fetchByAllOrParameters(paraMap);
                if (count > 0) {
                    result.put("statusCode", StatusCode.AccountAlreadyExists.getCode().toString());
                    result.put("statusMessage", StatusCode.AccountAlreadyExists.getMeaning());
                    return result;
                }
            }
            usersEntity.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
            usersEntity.setPwd(null);
            usersService.dynaUpdateByPrimaryKey(usersEntity);
        }
        return result;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "注册用户信息", notes = "注册用户信息")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public HashMap<String, Object> register(@RequestBody SUsers usersEntity) {
        HashMap<String, Object> hashMap = new HashMap<>();

        Map<String, Object> orMap = new HashMap<>();
        orMap.put("account", usersEntity.getAccount());
        orMap.put("mobile", usersEntity.getMobile());
        orMap.put("email", usersEntity.getEmail());
        if (null != usersEntity.getPwd()) {
            String encryptedPwd = MD5Util.getMD5String(usersEntity.getPwd());
            usersEntity.setPwd(encryptedPwd);
        }
        int count = usersService.fetchByAllOrParameters(orMap);
        if (count > 0) {
            hashMap.put("statusCode", StatusCode.AccountAlreadyExists.getCode().toString());
            hashMap.put("statusMessage", StatusCode.AccountAlreadyExists.getMeaning());
            return hashMap;
        } else {
            usersService.dynaInsert(usersEntity);
        }
        String token = TokenGenerator.sign(usersEntity.getAccount(), usersEntity.getId());
        hashMap.put("token", token);
        hashMap.put("id", usersEntity.getId().toString());
        hashMap.put("account", usersEntity.getAccount());
        hashMap.put("nickName", usersEntity.getNickname());
        hashMap.put("chineseName", usersEntity.getChineseName());
        hashMap.put("imageUrl", usersEntity.getImageUrl());
        return hashMap;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "修改密码", notes = "修改用户密码")
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public HashMap<String, Object> changePassword(@RequestParam("id") Long id,
                                                  @RequestParam("userPwd") String userPwd,
                                                  @RequestParam("pwd") String pwd) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        SUsers entityUser = usersService.fetchOneByParameters(paramMap);
        HashMap<String, Object> userInfo = new HashMap<>();
        if (entityUser != null) {
            String originalPassword = MD5Util.getMD5String(userPwd);
            if (originalPassword.equalsIgnoreCase(entityUser.getPwd())) {
                String changePassword = MD5Util.getMD5String(pwd);
                SUsers user = new SUsers();
                user.setId(id);
                user.setPwd(changePassword);
                usersService.dynaUpdateByPrimaryKey(user);
                userInfo.put("statusCode", StatusCode.ModifiedSuccess.getCode().toString());
                userInfo.put("statusMessage", StatusCode.ModifiedSuccess.getMeaning());
                return userInfo;
            } else {
                userInfo.put("statusCode", StatusCode.OldPasswordMatching.getCode().toString());
                userInfo.put("statusMessage", StatusCode.OldPasswordMatching.getMeaning());
                return userInfo;
            }
        }
        userInfo.put("statusCode", StatusCode.NonExistentUser.getCode().toString());
        userInfo.put("statusMessage", StatusCode.NonExistentUser.getMeaning());
        return userInfo;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录，可使用账户，邮件地址，手机号进行登录")
    @RequestMapping(value = "loginWeb", method = RequestMethod.GET)
    public HashMap<String, Object> loginWeb(ServletRequest request,
                                            @RequestParam(value = "account") String account,
                                            @RequestParam(value = "pwd") String pwd) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String encryptedPwd = MD5Util.getMD5String(pwd);
        HashMap<String, Object> userInfo = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account", account);
        SUsers entity = usersService.fetchOneByParameters(paramMap);
        if (null == entity) {
            Map<String, Object> paramMapPhone = new HashMap<>();
            paramMapPhone.put("mobile", account);
            entity = usersService.fetchOneByParameters(paramMapPhone);
        }
        Long NotFindId = Long.valueOf(0);
        //未查询到账户
        if (entity == null) {
            buildUserLog(account, NotFindId, 1, 1, 0, httpServletRequest, "未找到登录的账户名");
            userInfo.put("statusCode", StatusCode.NotFindAccount.getCode().toString());
            userInfo.put("statusMessage", StatusCode.NotFindAccount.getMeaning());
            return userInfo;

        }
        //密码不正确
        if (!encryptedPwd.equalsIgnoreCase(entity.getPwd())) {
            buildUserLog(account, NotFindId, 1, 1, 0, httpServletRequest, "登录账户的密码不正确");
            userInfo.put("statusCode", StatusCode.PassWordError.getCode().toString());
            userInfo.put("statusMessage", StatusCode.PassWordError.getMeaning());
            return userInfo;
        }
        //非后台用户
        if (entity.getLoginType() != 1) {
            buildUserLog(account, NotFindId, 1, 1, 0, httpServletRequest, "非后台账户尝试登录");
            userInfo.put("statusCode", StatusCode.NoPermissionByConsole.getCode().toString());
            userInfo.put("statusMessage", StatusCode.NoPermissionByConsole.getMeaning());
            return userInfo;
        }
        //账户未激活
        if (entity.getStatus() != 1) {
            buildUserLog(account, NotFindId, 1, 1, 0, httpServletRequest, "尝试登录账户未激活");
            userInfo.put("statusCode", StatusCode.AccountUnActive.getCode().toString());
            userInfo.put("statusMessage", StatusCode.AccountUnActive.getMeaning());
            return userInfo;
        }

        String token = TokenGenerator.sign(account, entity.getId());
        userInfo.put("token", token);
        userInfo.put("id", entity.getId().toString());
        userInfo.put("account", entity.getAccount());
        userInfo.put("nickName", entity.getNickname());
        userInfo.put("chineseName", entity.getChineseName());
        userInfo.put("imageUrl", entity.getImageUrl());

        buildUserLog(account, entity.getId(), 1, 1, 1, httpServletRequest, "管理后台登陆成功");
        return userInfo;
    }

    /**
     * 根据登陆动作，记录登陆日志
     *
     * @param account            访问的账户
     * @param plat               登录的入口 后台，web，移动端
     * @param type               登录，退出，访问码
     * @param flag               是否成功
     * @param httpServletRequest 请求的httpRequest
     * @return
     */
    public Integer buildUserLog(String account, Long id, int plat, int type, int flag, HttpServletRequest httpServletRequest, String operation) {
        SUserLog userLogEntity = new SUserLog();
        userLogEntity.setAccount(account);
        userLogEntity.setUserId(id);
        userLogEntity.setLoginPlatform(plat);
        userLogEntity.setType(type);
        userLogEntity.setOperation(operation);
        userLogEntity.setUserIp(IpAddressUtil.getIpAdrress(httpServletRequest));
        userLogEntity.setSuccessFlag(flag);

        return userLogService.dynaInsert(userLogEntity);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除用户的信息", notes = "根据用户的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public int delete(@RequestBody Long id) {
        return usersService.dynaDeleteByPrimaryKey(SUsers.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "检查是否存在用户的信息", notes = "根据用户的account,mobile,email检查是否存在账户")
    @RequestMapping(value = "checkExistUser", method = RequestMethod.GET)
    public Map<String, Object> checkExistUser(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "account", required = false) String account, @RequestParam(value = "mobile", required = false) String mobile, @RequestParam(value = "email", required = false) String email) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> paraMap = new HashMap<>();
        if (StringUtils.isEmpty(account) && StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) {
            return result;
        }

        if (null != userId) { //编辑修改
            SUsers sUsersSource = usersService.dynaSelectByPrimaryKey(SUsers.builder().id(userId).build());
            if (null != sUsersSource) {
                String accountSource = sUsersSource.getAccount();
                String mobileSource = sUsersSource.getMobile();
                String emailSource = sUsersSource.getEmail();

                if (StringUtils.isNotEmpty(account) && !account.equals(accountSource)) {
                    paraMap.put("account", account);
                } else if (StringUtils.isNotEmpty(mobile) && !mobile.equals(mobileSource)) {
                    paraMap.put("mobile", mobile);
                } else if (StringUtils.isNotEmpty(email) && !email.equals(emailSource)) {
                    paraMap.put("email", email);
                }
            } else {
                result.put("statusCode", StatusCode.NonExistentUser.getCode().toString());
                result.put("statusMessage", StatusCode.NonExistentUser.getMeaning());
                return result;
            }

        } else {
            if (StringUtils.isNotEmpty(account)) {
                paraMap.put("account", account);
            } else if (StringUtils.isNotEmpty(mobile)) {
                paraMap.put("mobile", mobile);
            } else if (StringUtils.isNotEmpty(email)) {
                paraMap.put("email", email);
            }
        }
        if (!MapUtils.isEmpty(paraMap)) {
            int count = usersService.fetchByAllOrParameters(paraMap);
            if (count > 0) {
                result.put("statusCode", StatusCode.AccountAlreadyExists.getCode().toString());
                result.put("statusMessage", StatusCode.AccountAlreadyExists.getMeaning());
                return result;
            }

        }
        return result;
    }


}
