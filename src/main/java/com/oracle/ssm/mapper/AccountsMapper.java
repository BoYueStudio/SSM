package com.oracle.ssm.mapper;

import com.oracle.ssm.model.Accounts;

public interface AccountsMapper {
    int insert(Accounts record);

    int insertSelective(Accounts record);
}