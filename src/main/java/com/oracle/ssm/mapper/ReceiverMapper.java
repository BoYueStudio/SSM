package com.oracle.ssm.mapper;

import java.util.List;

import com.oracle.ssm.model.Receiver;

public interface ReceiverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Receiver record);

    int insertSelective(Receiver record);

    Receiver selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Receiver record);

    int updateByPrimaryKeyWithBLOBs(Receiver record);

    int updateByPrimaryKey(Receiver record);

	List<Receiver> findReveicersByUserId(int parseInt);
}