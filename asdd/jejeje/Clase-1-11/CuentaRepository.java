package practicas2.homebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas2.homebanking.models.Cuenta;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    List<Cuenta> findByEstadoTrue();
}
