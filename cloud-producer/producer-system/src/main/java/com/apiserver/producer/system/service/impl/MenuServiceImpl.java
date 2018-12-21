package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SMenu;
import com.apiserver.producer.system.mapper.MenuMapper;
import com.apiserver.producer.system.service.MenuService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jarvischang
 * @date 2018-10-11
 */
@Repository
public class MenuServiceImpl extends BaseService<MenuMapper, SMenu> implements MenuService {


    @Override
    public List<SMenu> queryMenuByUserId(Long userId) {
        return dao.queryMenuByUserId(userId);
    }

    @Override
    public List<SMenu> queryMenuByRoleId(Long roleId) {
        return dao.queryMenuByRoleId(roleId);
    }
}
