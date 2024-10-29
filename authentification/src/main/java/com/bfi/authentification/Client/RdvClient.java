package com.bfi.authentification.Client;

import com.bfi.authentification.DTO.Reclamation;
import com.bfi.authentification.DTO.RendezVous;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "rdv-service", url = "${application.config.rdv-url}")
public interface RdvClient {

    @GetMapping("/user/{idUser}")
    List<RendezVous> findAllRdvByUser(@PathVariable("idUser") Long idUser);
}