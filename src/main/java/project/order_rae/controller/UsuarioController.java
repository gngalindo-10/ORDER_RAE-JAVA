package project.order_rae.controller;

import project.order_rae.model.Usuario;
import project.order_rae.repository.UsuarioRepository;
import project.order_rae.service.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;

    @GetMapping("/")
    public String redireccionRaiz() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication auth) {
        model.addAttribute("rol", auth.getAuthorities().toString());
        return "home";
    }

    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", repo.findAll());
        return "usuarios";
    }

    @Autowired
    private RolService rolService;

    @GetMapping("/usuarios/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listar());
        return "form";
    }

    @PostMapping("/usuarios/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        // Codificar la contraseña
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        // Guardar directamente (el correo ya es único y actúa como username)
        repo.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", repo.findById(id).orElseThrow());
        return "form";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/usuarios";
    }

    // Perfil del usuario logueado
    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        String correo = auth.getName(); // Spring Security usa el correo como username
        Usuario usuario = repo.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Usuario usuario, Authentication auth) {
        String correoActual = auth.getName();
        Usuario actual = repo.findByCorreo(correoActual)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar campos editables (NO el correo, por seguridad)
        actual.setNombre(usuario.getNombre());
        actual.setApellidos(usuario.getApellidos());
        actual.setTelefono(usuario.getTelefono());
        actual.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));

        repo.save(actual);
        return "redirect:/home?actualizado";
    }
}