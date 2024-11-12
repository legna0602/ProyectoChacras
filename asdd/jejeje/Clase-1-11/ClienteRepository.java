package practicas2.homebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import practicas2.homebanking.models.Cliente;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByApellido(String apellido);
    Optional<Cliente> findById(int id);
    Cliente findByCorreo(String correo);

    List<Cliente> findByEstadoTrue();
}
