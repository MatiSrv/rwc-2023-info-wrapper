package ar.edu.utn.frc.tup.lciii.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.tup.lciii.dtos.PoolDTO;
import ar.edu.utn.frc.tup.lciii.services.RwcService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/rwc/2023")
public class RwcController {
    
    @Autowired
    private RwcService rwcService;

    @GetMapping("pools")
    public ResponseEntity<List<PoolDTO>> getPools() {
        return ResponseEntity.ok(rwcService.getAllPools());
    }

     @GetMapping("/pools/{id}")
    public ResponseEntity<PoolDTO> getPoolById(@PathVariable String id) {
        return ResponseEntity.ok(rwcService.getPoolById(id));
    }

}
