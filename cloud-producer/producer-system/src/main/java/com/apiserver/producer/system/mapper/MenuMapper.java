package com.apiserver.producer.system.mapper;

import com.apiserver.data.dao.BaseMapper;
import com.apiserver.model.system.entity.SMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jarvis
 */
public interface MenuMapper extends BaseMapper<SMenu> {
    List<SMenu> queryMenuByUserId(@Param("userId") Long userId);

    List<SMenu> queryMenuByRoleId(@Param("roleId") Long roleId);
}