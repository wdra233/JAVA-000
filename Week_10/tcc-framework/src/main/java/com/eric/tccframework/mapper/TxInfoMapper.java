package com.eric.tccframework.mapper;

import com.eric.tccframework.model.TransactionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface TxInfoMapper {
    @Select("select id, txid, status, class_name, confirm_method, cancel_method, create_time, update_time from t_txinfo where txid = #{txid}")
    List<TransactionInfo> selectByTXID(String txid);

    @Insert("insert into t_txinfo(txid,status,class_name,confirm_method,cancel_method,create_time,update_time) " +
            "values(#{txid}, #{status}, #{className}, #{confirmMethod}, #{cancelMethod}, #{createTime}, #{updateTime})")
    void insertOne(TransactionInfo transactionInfo);


    @Update("update t_txinfo set status = #{status}, update_time = #{updateTime} where txid = #{txid}")
    void updateOne(TransactionInfo transactionInfo);

}
