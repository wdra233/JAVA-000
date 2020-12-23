package io.kimmking.dubbo.demo.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BankAccount {
    private Long id;
    private String accountName;
    private BigDecimal rmbAmount;
    private BigDecimal usdAmount;
    private Date createTime;
    private Date updateTime;
}