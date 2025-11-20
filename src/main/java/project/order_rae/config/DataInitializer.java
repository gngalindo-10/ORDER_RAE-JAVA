package project.order_rae.config;

import project.order_rae.model.Usuario;
import project.order_rae.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase para inicializar datos por defecto en la base de datos al arrancar la aplicación.
 */
@Configuration
public class DataInitializer {

    @Bean
public CommandLineRunner initDatabase(UsuarioRepository repo) {
    return args -> {
        if (repo.findByCorreo("admin@example.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellidos("System");
            admin.setDocumento("0000000000");
            admin.setCorreo("admin@example.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("123"));
            admin.setGenero("M");
            admin.setTelefono("0000000000");
            admin.setEstado("Activo");
            admin.setRol("ADMIN");

            repo.save(admin);
            System.out.println("Usuario 'admin@example.com' creado con éxito.");
        } else {
            System.out.println("El usuario 'admin@example.com' ya existe.");
        }
    };
}
}

