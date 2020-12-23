package io.kimmking.dubbo.demo.consumer;

import io.kimmking.dubbo.demo.api.entities.TransactionInfo;
import io.kimmking.dubbo.demo.consumer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DubboClientApplication {

    @Autowired
    private TransactionService transactionService;

    public static void main(String[] args) {
        SpringApplication.run(DubboClientApplication.class, args).close();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            TransactionInfo transactionInfo = TransactionInfo.builder()
                    .fromAccountId(1L).fromFreezeId(UUID.randomUUID().toString())
                    .toAccountId(2L).toFreezeId(UUID.randomUUID().toString())
                    .amount(new BigDecimal(7)).currencyType("RMB")
                    .build();
            transactionService.initTransaction(transactionInfo);
            System.out.println("transaction success");
        };
    }
}
