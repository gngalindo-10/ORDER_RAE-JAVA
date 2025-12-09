package project.order_rae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.order_rae.model.Usuario;
import project.order_rae.repository.UsuarioRepository;
import project.order_rae.service.RolService;
import project.order_rae.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import project.order_rae.utils.ExcelGenerator;
import project.order_rae.utils.PdfGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UsuarioController {

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private ExcelGenerator excelGenerator;

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/home")
    public String home(Model model, Authentication auth) {
        model.addAttribute("rol", auth.getAuthorities().toString());
        return "home";
    }

    @GetMapping("/usuarios")
    public String listar(
            @RequestParam(required = false) String termino,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String rol,
            Model model) {

        List<Usuario> usuarios;

        if (estado != null && !estado.trim().isEmpty()) {
            usuarios = usuarioService.findByEstado(estado.trim());
            model.addAttribute("estadoFiltro", estado.trim());
        } else if (rol != null && !rol.trim().isEmpty()) {
            usuarios = usuarioService.findByRol(rol.trim());
            model.addAttribute("rolFiltro", rol.trim());
        } else if (termino != null && !termino.trim().isEmpty()) {
            usuarios = usuarioService.buscarPorTermino(termino.trim());
            model.addAttribute("termino", termino.trim());
        } else {
            usuarios = usuarioService.listar();
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", rolService.listar());
        return "usuario/usuarios";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listar());
        return "usuario/form";
    }

    // Pdf con filtros
    @GetMapping("/usuarios/reporte")
    public void generarReporteUsuarios(
            @RequestParam(required = false) String termino,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String rol,
            HttpServletResponse response) throws Exception {

        List<Usuario> usuarios = filtrarUsuarios(termino, estado, rol);
        pdfGenerator.generarPdf("reports/usuarios_reporte", usuarios, response);
    }

    // Excel con filtros
    @GetMapping("/usuarios/excel")
    public void exportarExcel(
            @RequestParam(required = false) String termino,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String rol,
            HttpServletResponse response) throws Exception {

        List<Usuario> usuarios = filtrarUsuarios(termino, estado, rol);
        excelGenerator.generarExcelUsuarios(usuarios, response);
    }

    // MÉTODO AUXILIAR PARA FILTRAR (reutiliza la lógica)
    private List<Usuario> filtrarUsuarios(String termino, String estado, String rol) {
        if (estado != null && !estado.trim().isEmpty()) {
            return usuarioService.findByEstado(estado.trim());
        } else if (rol != null && !rol.trim().isEmpty()) {
            return usuarioService.findByRol(rol.trim());
        } else if (termino != null && !termino.trim().isEmpty()) {
            return usuarioService.buscarPorTermino(termino.trim());
        } else {
            return usuarioService.listar();
        }
    }

    @PostMapping("/usuarios/guardar")
    public String guardar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttrs) {
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        repo.save(usuario);
        redirectAttrs.addFlashAttribute("mensajeExito", "Usuario registrado exitosamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listar());
        model.addAttribute("returnUrl", "/usuarios"); // Para administradores, vuelve a la lista
        return "usuario/form";
    }

    @PostMapping("/usuarios/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Usuario usuario, RedirectAttributes redirectAttrs) {
        Usuario existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existente.setNombre(usuario.getNombre());
        existente.setApellidos(usuario.getApellidos());
        existente.setDocumento(usuario.getDocumento());
        existente.setCorreo(usuario.getCorreo());
        existente.setGenero(usuario.getGenero());
        existente.setTelefono(usuario.getTelefono());
        existente.setEstado(usuario.getEstado());
        existente.setRol(usuario.getRol());

        // Solo encriptar si se proporciona una nueva contraseña
        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
            existente.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        }

        repo.save(existente);
        redirectAttrs.addFlashAttribute("mensajeExito", "Usuario actualizado exitosamente.");
        return "redirect:/usuarios";
    }

    // Eliminación segura con POST
    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        repo.deleteById(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Usuario eliminado exitosamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        String correo = auth.getName();
        Usuario usuario = repo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listar());
        return "usuario/form";
    }


    @PostMapping("/perfil/guardar")
    public String guardarPerfil(
        @ModelAttribute Usuario usuario,
        Authentication auth,
        RedirectAttributes redirectAttrs) {

        String correoActual = auth.getName();
        Usuario actual = repo.findByCorreo(correoActual)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar datos editables
        actual.setNombre(usuario.getNombre());
        actual.setApellidos(usuario.getApellidos());
        actual.setTelefono(usuario.getTelefono());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            actual.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        }

        repo.save(actual);
        redirectAttrs.addFlashAttribute("mensajeExito", "Perfil actualizado exitosamente.");

        // Determinar el rol del usuario autenticado
        String rol = actual.getRol().getNombreRol(); 

        // Redirección según el rol
        if ("Cliente".equalsIgnoreCase(rol.trim())) {
            return "redirect:/"; 
        } else {
        return "redirect:/dashboard"; 
        }
    }

    @GetMapping("/dashboard/usuarios")
    public String dashboardUsuarios(Model model) {
        List<Usuario> todos = repo.findAll();

        long total = todos.size();
        long activos = todos.stream().filter(u -> "Activo".equals(u.getEstado())).count();
        long totalRoles = rolService.listar().size();

        List<Usuario> ultimos = todos.stream()
                .sorted((u1, u2) -> u2.getCreatedAt().compareTo(u1.getCreatedAt()))
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("totalUsuarios", total);
        model.addAttribute("usuariosActivos", activos);
        model.addAttribute("totalRoles", totalRoles);
        model.addAttribute("ultimosUsuarios", ultimos);

        return "dashboardUsuarios";
    }
}