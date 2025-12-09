package project.order_rae.repository;

import project.order_rae.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreo(String correo);

    boolean existsByDocumento(String documento);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.correo = :correo")
    Optional<Usuario> findByCorreo(@Param("correo") String correo);

    // Búsqueda Multicriterio
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE " +
       "LOWER(CAST(u.id AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.documento) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.correo) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.genero) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.telefono) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.estado) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.rol.nombreRol) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Usuario> buscarPorTermino(@Param("termino") String termino);
    
    // Filtro por estado 
    List<Usuario> findByEstadoIgnoreCase(String estado);
    // Filtro por rol 
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE LOWER(u.rol.nombreRol) = LOWER(:rol)")
    List<Usuario> findByRolNombre(@Param("rol") String rol);
}

