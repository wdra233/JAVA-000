package io.kimmking.rpcfx.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcfxResponse {
    private Object result;
    private boolean status;
    private Exception exception;
}


