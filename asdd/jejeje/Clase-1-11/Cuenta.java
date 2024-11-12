package practicas2.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private String numero;
    private LocalDate fechaCreacion;
    private double saldo;
    private boolean estado;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy="cuenta", fetch=FetchType.EAGER)
    Set<Transaccion> transacciones = new HashSet<>();

    public Cuenta(){
        fechaCreacion = LocalDate.now();
        estado = true;
    }

    public Cuenta(String numero, double saldo){
        this.numero = numero;
        fechaCreacion = LocalDate.now();
        this.saldo = saldo;
        estado = true;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(Set<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public void addTransaccion(Transaccion transaccion){
        transaccion.setCuenta(this);
        transacciones.add(transaccion);
    }
}
