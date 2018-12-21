package com.apiserver.producer.system.service;

import com.apiserver.data.service.SqlService;
import com.apiserver.model.system.entity.SMenu;

import java.util.List;

/**
 * @author jarvis
 */
public interface MenuService  extends SqlService<SMenu> {
    List<SMenu> queryMenuByUserId(Long userId);

    List<SMenu> queryMenuByRoleId(Long roleId);
}
