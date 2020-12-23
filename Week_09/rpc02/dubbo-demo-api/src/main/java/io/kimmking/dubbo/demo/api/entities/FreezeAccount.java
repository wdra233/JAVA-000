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
public class FreezeAccount {
    private Long id;
    private String state;
    private String freezeId;
    private Long accountId;
    private BigDecimal amount;
    private String currencyType;
    private Long version;
    private Date createTime;
    private Date updateTime;
}
