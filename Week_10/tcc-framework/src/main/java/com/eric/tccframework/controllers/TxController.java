package com.eric.tccframework.controllers;

import com.eric.tccframework.service.TxInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tm")
public class TxController {

    @Autowired
    private TxInfoService service;

    @GetMapping("/execNext")
    public Boolean txHandler(@RequestParam(value = "txid") String txid) {
        return service.handleTxInfo(txid);
    }
}
