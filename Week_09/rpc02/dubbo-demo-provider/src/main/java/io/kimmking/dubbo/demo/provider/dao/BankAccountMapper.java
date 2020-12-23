package io.kimmking.dubbo.demo.provider.dao;

import io.kimmking.dubbo.demo.api.entities.BankAccount;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface BankAccountMapper {
    @Delete("delete from t_bankaccount where id = #{id BIGINT}")
    int deleteByPrimaryKey(Long id);

    @Insert("insert into t_bankaccount " +
            "(id, accountName, rmbAmount, usdAmount, createTime, updateTime) " +
            "values (#{id,jdbcType=BIGINT}, #{accountName,jdbcType=VARCHAR}, #{rmbAmount,jdbcType=DECIMAL}, " +
            "#{usdAmount,jdbcType=DECIMAL}, #{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})")
    @Results( id = "BankAccount", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "accountName", property = "accountName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "rmbAmount", property = "rmbAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "usdAmount", property = "usdAmount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "createTime", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "updateTime", property = "updateTime", jdbcType = JdbcType.TIMESTAMP) }
    )
    int insert(BankAccount record);

    @Select("select id, accountName, rmbAmount, usdAmount, createTime, updateTime from t_bankaccount where id = #{id,jdbcType=BIGINT}")
    BankAccount selectByPrimaryKey(Long id);

    @Select("select id, accountName, rmbAmount, usdAmount, createTime, updateTime from t_bankaccount")
    List<BankAccount> selectAll();

    @Update("update t_bankaccount " +
            "set rmbAmount = #{rmbAmount,jdbcType=DECIMAL} " +
            "where id = #{id,jdbcType=BIGINT}")
    int updateRMBAmountById(Long id, BigDecimal rmbAmount);

    @Update("update t_bankaccount " +
            "set usdAmount = #{usdAmount,jdbcType=DECIMAL} " +
            "where id = #{id,jdbcType=BIGINT}")
    int updateUSDAmountById(Long id, BigDecimal usdAmount);
}