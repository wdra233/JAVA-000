package com.eric.tccframework.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionInfo {
    private Long id;
    private String txid;
    private int status;
    private String className;
    private String confirmMethod;
    private String cancelMethod;
    private Timestamp createTime;
    private Timestamp updateTime;
}
