package com.xhn.chat.chatwaveserver.user.service.impl;

import com.xhn.chat.chatwaveserver.user.model.base.BaseProfileEntity;
import com.xhn.chat.chatwaveserver.user.mapper.ProfileMapper;
import com.xhn.chat.chatwaveserver.user.service.IProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 普通用户信息表，存储用户基础资料和状态 服务实现类
 * </p>
 *
 * @author xhn
 * @since 2025-09-15
 */
@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, BaseProfileEntity> implements IProfileService {

}
