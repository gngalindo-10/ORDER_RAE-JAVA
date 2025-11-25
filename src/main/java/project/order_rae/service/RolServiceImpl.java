package project.order_rae.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.order_rae.model.Rol;
import project.order_rae.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol obtenerPorId(String id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + id));
    }

    @Override
    public Rol actualizar(String id, Rol rolActualizado) {
        Rol rol = obtenerPorId(id);
        rol.setNombreRol(rolActualizado.getNombreRol());
        return rolRepository.save(rol);
    }

    @Override
    public void eliminar(String id) {
        rolRepository.deleteById(id);
    }
}



