package practicas2.homebanking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import practicas2.homebanking.dtos.ClienteDTO;
import practicas2.homebanking.dtos.CrearClienteDTO;
import practicas2.homebanking.dtos.ModificarClienteDTO;
import practicas2.homebanking.models.Cliente;
import practicas2.homebanking.models.Rol;
import practicas2.homebanking.repositories.ClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClienteRepository clienteRepository;
    @GetMapping("/clientes")
    public List<ClienteDTO> getClientes(){
        return clienteRepository.findByEstadoTrue().stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
    }


    @GetMapping("/clientes/{id}")
    public ResponseEntity<Object> getCliente(@PathVariable int id){
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if(cliente == null){
            return new ResponseEntity<>("El usuario buscado no existe.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ClienteDTO(cliente), HttpStatus.OK);
    }

    @GetMapping("/clientes/current")
    public ResponseEntity<Object> getClienteActual(Authentication authentication){
        Cliente cliente = clienteRepository.findByCorreo(authentication.getName());
        return new ResponseEntity<>(new ClienteDTO(cliente), HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<Object> creatCliente(@RequestBody CrearClienteDTO crearClienteDTO){
        if(crearClienteDTO.getNombre().isBlank()){
            return new ResponseEntity<>("El campo nombre no puede estar vacio.", HttpStatus.FORBIDDEN);
        }
        Cliente cliente = new Cliente(crearClienteDTO.getNombre(), crearClienteDTO.getApellido(), crearClienteDTO.getCorreo(), passwordEncoder.encode(crearClienteDTO.getPassword()));
        cliente.setRol(Rol.CLIENTE);
        clienteRepository.save(cliente);

        return new ResponseEntity<>("Usuario creado correctamente.", HttpStatus.OK);
    }

    @PutMapping("/clientes")
    public ResponseEntity<Object> modificarCliente(Authentication authentication, @RequestBody ModificarClienteDTO modificarClienteDTO){
        Cliente cliente = clienteRepository.findByCorreo(authentication.getName());
        cliente.setNombre(modificarClienteDTO.getNombre());
        cliente.setApellido(modificarClienteDTO.getApellido());

        clienteRepository.save(cliente);
        return new ResponseEntity<>("Cliente modificado correctamente.", HttpStatus.OK);
    }


}
