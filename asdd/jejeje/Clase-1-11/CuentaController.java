package practicas2.homebanking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas2.homebanking.dtos.CuentaDTO;
import practicas2.homebanking.models.Cuenta;
import practicas2.homebanking.repositories.CuentaRepository;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CuentaController {
    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping("/cuentas")
    public ResponseEntity<Object> getCuentas(){
        return new ResponseEntity<>(cuentaRepository.findAll().stream().map(cuenta -> new CuentaDTO(cuenta)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Object> getCuenta(@PathVariable int id){
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        if(cuenta == null){
            return new ResponseEntity<>("La cuenta no existe.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CuentaDTO(cuenta), HttpStatus.OK);
    }

    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Object> eliminarCuenta(@PathVariable int id){
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        if(cuenta == null){
            return new ResponseEntity<>("La cuenta no existe.", HttpStatus.NOT_FOUND);
        }
        cuenta.setEstado(false);
        cuentaRepository.save(cuenta);
        return new ResponseEntity<>("La cuenta fue borrada exitosamente.", HttpStatus.OK);
    }
}
