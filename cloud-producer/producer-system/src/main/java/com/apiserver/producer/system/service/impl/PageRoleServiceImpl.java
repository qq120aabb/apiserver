package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SPageRole;
import com.apiserver.model.system.response.PageRoleButtons;
import com.apiserver.producer.system.mapper.PageRoleMapper;
import com.apiserver.producer.system.service.PageRoleService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jarvis
 */
@Repository
public class PageRoleServiceImpl   extends BaseService<PageRoleMapper, SPageRole> implements PageRoleService {


}
