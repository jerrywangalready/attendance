package com.newness.efficient.attendance.balance.mapper;

import com.newness.efficient.attendance.balance.bo.Balance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceMapper {

    Balance getTotaledBalance(String username);

}
