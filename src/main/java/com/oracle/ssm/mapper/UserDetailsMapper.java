package com.oracle.ssm.mapper;

import com.oracle.ssm.model.UserDetails;

public interface UserDetailsMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserDetails record);

    int insertSelective(UserDetails record);

    UserDetails selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserDetails record);

    int updateByPrimaryKeyWithBLOBs(UserDetails record);

    int updateByPrimaryKey(UserDetails record);
}