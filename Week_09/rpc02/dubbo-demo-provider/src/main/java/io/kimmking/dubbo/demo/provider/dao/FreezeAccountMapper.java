package io.kimmking.dubbo.demo.provider.dao;

import io.kimmking.dubbo.demo.api.entities.FreezeAccount;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FreezeAccountMapper {

    @Insert("insert into t_freezeaccount(id, state, freezeId, accountName, amount, currencyType, version, createTime, updateTime) " +
            "values (#{id, jdbcType=BIGINT}, #{state, jdbcType=VARCHAR}, #{freezeId, jdbcType=VARCHAR}, #{accountName, jdbcType=VARCHAR}, " +
            "#{amount, jdbcType=DECIMAL}, #{currencyType, jdbcType=VARCHAR}, #{version, jdbcType=BIGINT}, #{createTime, jdbcType=TIMESTAMP}, #{updateTime, jdbcType=TIMESTAMP})")
    @Results(id = "freezeMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "freezeId", property = "freezeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "accountName", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "amount", property = "amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "version", property = "version", jdbcType = JdbcType.BIGINT),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR),
            @Result(column = "currencyType", property = "currencyType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "creatTime", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "updateTime", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    int insert(FreezeAccount record);



    @Update("update t_freezeaccount set state = #{state}, version = version + 1 where id = #{id}")
    int updateByPrimaryKey(Long id, String state);
}