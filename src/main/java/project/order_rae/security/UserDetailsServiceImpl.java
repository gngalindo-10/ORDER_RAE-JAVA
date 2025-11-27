package project.order_rae.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

        // El rol
        String nombreRol = usuario.getRol().getCargo(); 

        String rolNormalizado = "ROLE_" + nombreRol.trim().toUpperCase().replace(" ", "_");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rolNormalizado);

        return new User(
            usuario.getCorreo(),
            usuario.getPassword(),
            Collections.singletonList(authority)
        );
    }
}
