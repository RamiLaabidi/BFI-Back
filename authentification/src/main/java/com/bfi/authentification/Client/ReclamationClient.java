package com.bfi.authentification.Client;

import com.bfi.authentification.DTO.Reclamation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reclamation-service", url = "${application.config.reclamation-url}")


public interface ReclamationClient {

    @GetMapping("/user/{idUser}")
    List<Reclamation> findAllReclamationByUser(@PathVariable("idUser") Long idUser);
}