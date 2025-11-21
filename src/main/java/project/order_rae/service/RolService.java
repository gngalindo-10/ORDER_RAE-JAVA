package project.order_rae.service;

import project.order_rae.model.Rol;
import project.order_rae.repository.RolRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolService {

    private final RolRepository repo;

    public RolService(RolRepository repo) {
        this.repo = repo;
    }

    public List<Rol> listar() {
        return repo.findAll();
    }

    public Rol guardar(Rol rol) {
        return repo.save(rol);
    }

    public Rol actualizar(Long id, Rol rol) {
        Rol existente = obtenerPorId(id);
        existente.setNombreRol(rol.getNombreRol());;
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Rol obtenerPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }
}
