package com.eric.tccframework.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_txinfo", indexes = {@Index(name = "IDX_TXID", columnList = "txid")})
@Entity
@Data
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
