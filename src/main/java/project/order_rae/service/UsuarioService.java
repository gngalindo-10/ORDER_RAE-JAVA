// src/main/java/project/order_rae/service/UsuarioService.java
package project.order_rae.service;

import project.order_rae.model.Usuario;
import project.order_rae.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se puede eliminar: Usuario con ID " + id + " no existe.");
        }
        usuarioRepository.deleteById(id);
    }

    // MÉTODO NUEVO PARA EL DASHBOARD
    public Long contarUsuarios() {
        return usuarioRepository.count();
    }
}
