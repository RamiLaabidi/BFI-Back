package com.bfi.authentification.Client;

import com.bfi.authentification.DTO.CreditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "credit-service", url = "${application.config.credit-url}")
public interface CreditClient {
    @GetMapping("/user/{idUser}")
    List<CreditDTO> findAllCreditsByUser(@PathVariable("idUser") Long idUser);
}
