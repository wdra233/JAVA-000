package io.kimmking.dubbo.demo.api.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionInfo {
    private BigDecimal amount;
    private String currencyType;
    private Long fromAccountId;
    private Long fromFreezeId;
    private Long toAccountId;
    private Long toFreezeId;
}
