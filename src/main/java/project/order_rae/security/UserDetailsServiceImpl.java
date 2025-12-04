package project.order_rae.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

import project.order_rae.model.Usuario;
import project.order_rae.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (usuario.getRol() == null) {
            throw new UsernameNotFoundException("Rol no asociado al usuario");
        }

        String nombreRol = usuario.getRol().getNombreRol().trim();
        
        String rolNormalizado = "ROLE_" + nombreRol; 

        System.out.println("🔍 Rol original: [" + nombreRol + "]");
        System.out.println("🔑 Rol normalizado: [" + rolNormalizado + "]");

        return User.builder()
            .username(usuario.getCorreo())
            .password(usuario.getPassword()) 
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(rolNormalizado)))
            .build();
    }
}