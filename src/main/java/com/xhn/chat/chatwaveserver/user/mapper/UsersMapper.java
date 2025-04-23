package com.xhn.chat.chatwaveserver.user.mapper;

import com.xhn.chat.chatwaveserver.user.model.UsersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 93095
* @description 针对表【users(用户表)】的数据库操作Mapper
* @createDate 2025-04-01 10:28:31
* @Entity com.xhn.chat.chatwaveserver.user.model.UsersEntity
*/
public interface UsersMapper extends BaseMapper<UsersEntity> {

    void selectTest(String name);
}




