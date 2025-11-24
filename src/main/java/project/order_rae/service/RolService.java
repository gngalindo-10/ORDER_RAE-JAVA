package project.order_rae.service;

import java.util.List;
import project.order_rae.model.Rol;

public interface RolService {

    List<Rol> listar();

    Rol guardar(Rol rol);

    Rol obtenerPorId(Long id);

    Rol actualizar(Long id, Rol rolActualizado);

    void eliminar(Long id);
}


