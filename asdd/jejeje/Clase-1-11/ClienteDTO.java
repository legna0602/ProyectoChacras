package practicas2.homebanking.dtos;

import practicas2.homebanking.models.Cliente;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;

    private List<CuentaDTO> cuentas;

    private List<ClientePrestamoDTO> prestamos;
    private List<TarjetaDTO> tarjetas;
    public ClienteDTO(Cliente cliente){
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.correo = cliente.getCorreo();
        this.cuentas = cliente.getCuentas().stream().filter(cuenta -> cuenta.isEstado()).map(cuenta -> new CuentaDTO(cuenta)).collect(Collectors.toList());
        this.prestamos = cliente.getClientePrestamos().stream().map(clientePrestamo -> new ClientePrestamoDTO(clientePrestamo)).collect(Collectors.toList());
        this.tarjetas = cliente.getTarjetas().stream().map(tarjeta -> new TarjetaDTO(tarjeta)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public List<CuentaDTO> getCuentas() {
        return cuentas;
    }

    public List<ClientePrestamoDTO> getPrestamos() {
        return prestamos;
    }

    public List<TarjetaDTO> getTarjetas() {
        return tarjetas;
    }
}
