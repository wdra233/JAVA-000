package com.eric.dbtransaction.dao;

import com.eric.dbtransaction.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select * from t_order")
    List<Order> findAllOrders();

    @Insert("insert into t_order (member_id, order_sn, coupon_id, create_time, member_username, total_amount, pay_amount)" +
            "values (#{memberId,jdbcType=BIGINT}, #{orderSn,jdbcType=CHAR}," +
            "#{couponId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}," +
            "#{memberUsername,jdbcType=VARCHAR},#{totalAmount,jdbcType=DECIMAL}, #{payAmount,jdbcType=DECIMAL})")
    int insertWithNoPrimaryKey(Order order);

    @Insert("insert into t_order (id, member_id, order_sn, coupon_id, create_time, member_username, total_amount, pay_amount)" +
            "values (#{id,jdbcType=BIGINT}, #{memberId,jdbcType=BIGINT}, #{orderSn,jdbcType=CHAR}," +
            "#{couponId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}," +
            "#{memberUsername,jdbcType=VARCHAR},#{totalAmount,jdbcType=DECIMAL}, #{payAmount,jdbcType=DECIMAL})")
    int insertWithPrimaryKey(Order order);

    @Select("select * from t_order where id = #{id}")
    Order findOrderById(Long id);
}
