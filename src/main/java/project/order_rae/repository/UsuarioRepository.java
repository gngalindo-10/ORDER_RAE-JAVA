package project.order_rae.repository;

import project.order_rae.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.correo = :correo")
    Optional<Usuario> findByCorreo(@Param("correo") String correo);
}

