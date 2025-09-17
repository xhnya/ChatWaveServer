package com.xhn.chat.chatwaveserver.user.service.impl;

import com.xhn.chat.chatwaveserver.user.model.base.BaseAccountEntity;
import com.xhn.chat.chatwaveserver.user.mapper.AccountMapper;
import com.xhn.chat.chatwaveserver.user.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账号表，存储用户登录信息和状态 服务实现类
 * </p>
 *
 * @author xhn
 * @since 2025-09-15
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, BaseAccountEntity> implements IAccountService {

}
