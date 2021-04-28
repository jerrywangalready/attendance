package com.newness.efficient.attendance.balance.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceMapper {

    Float getTotaledBalance(String username);
}
