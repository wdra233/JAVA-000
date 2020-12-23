package io.kimmking.dubbo.demo.api.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionInfo {
    private BigDecimal amount;
    private String currencyType;
    private Long fromAccountId;
    private String fromFreezeId;
    private Long toAccountId;
    private String toFreezeId;
}
