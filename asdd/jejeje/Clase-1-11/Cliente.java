package practicas2.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private Rol rol;
    private boolean estado;
    @OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
    Set<Cuenta> cuentas = new HashSet<>();
    @OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
    Set<ClientePrestamo> clientePrestamos = new HashSet<>();
    @OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
    Set<Tarjeta> tarjetas = new HashSet<>();

    public Cliente(){
        estado = true;
    }
    public Cliente(String nombre, String apellido, String correo, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.estado = true;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Set<ClientePrestamo> getClientePrestamos() {
        return clientePrestamos;
    }

    public void setClientePrestamos(Set<ClientePrestamo> clientePrestamos) {
        this.clientePrestamos = clientePrestamos;
    }

    public List<Prestamo> getPrestamos(){
        return clientePrestamos.stream().map(clientePrestamo -> clientePrestamo.getPrestamo()).collect(Collectors.toList());
    }

    public Set<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(Set<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public void addCuenta(Cuenta cuenta){
        cuenta.setCliente(this);
        cuentas.add(cuenta);
    }

    public void addClientePrestamo(ClientePrestamo clientePrestamo){
        clientePrestamo.setCliente(this);
        clientePrestamos.add(clientePrestamo);
    }

    public void addTarjeta(Tarjeta tarjeta){
        tarjeta.setCliente(this);
        tarjetas.add(tarjeta);
    }
}
