package project.order_rae.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import project.order_rae.model.Usuario;
import project.order_rae.repository.UsuarioRepository;

@Component
@ControllerAdvice
public class GlobalUserAttributes {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @ModelAttribute("nombreCompleto")
    public String nombreCompletoDelUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            return usuarioRepository.findByCorreo(email)
                .map(usuario -> usuario.getNombre() + " " + usuario.getApellidos())
                .orElse(null);
        }
        return null;
    }
}