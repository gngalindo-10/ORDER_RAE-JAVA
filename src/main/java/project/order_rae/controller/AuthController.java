package project.order_rae.controller;

import project.order_rae.model.Usuario;
import project.order_rae.model.Rol;
import project.order_rae.repository.UsuarioRepository;
import project.order_rae.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String documento,
            @RequestParam String correo,
            @RequestParam(required = false) String telefono,
            @RequestParam String genero,
            @RequestParam String password,
            @RequestParam String password2,
            RedirectAttributes redirectAttrs) {

        // Validar contraseñas
        if (!password.equals(password2)) {
            redirectAttrs.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "redirect:/registro";
        }

        // Validar correo único
        if (usuarioRepository.existsByCorreo(correo)) {
            redirectAttrs.addFlashAttribute("error", "El correo ya está registrado.");
            return " redirect:/registro";
        }

        // Validar documento único
        if (usuarioRepository.existsByDocumento(documento)) {
            redirectAttrs.addFlashAttribute("error", "El documento ya está registrado.");
            return "redirect:/registro";
        }

        // Obtener rol "Cliente"
        Rol clienteRol = rolRepository.findByNombreRol("Cliente")
            .orElseThrow(() -> new RuntimeException("Rol 'Cliente' no encontrado en la base de datos"));

        // Crear usuario
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setApellidos(apellidos);
        nuevo.setDocumento(documento);
        nuevo.setCorreo(correo);
        nuevo.setTelefono(telefono);
        nuevo.setGenero(genero);
        nuevo.setPassword(passwordEncoder.encode(password));
        nuevo.setEstado("Activo"); 
        nuevo.setRol(clienteRol);

        // Guardar
        usuarioRepository.save(nuevo);

        // Redirigir a la página principal
        return "redirect:/";
    }
}
