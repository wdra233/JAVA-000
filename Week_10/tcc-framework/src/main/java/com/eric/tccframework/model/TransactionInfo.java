package com.eric.tccframework.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "t_txinfo", indexes = {@Index(name = "IDX_TXID", columnList = "txid")})
@Entity
@Data
@NoArgsConstructor
public class TransactionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String txid;
    private int status;
    private String className;
    private String confirmMethod;
    private String cancelMethod;
    private Timestamp createTime;
    private Timestamp updateTime;
}
