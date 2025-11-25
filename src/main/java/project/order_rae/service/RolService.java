package project.order_rae.service;

import java.util.List;
import project.order_rae.model.Rol;

public interface RolService {

    List<Rol> listar();

    Rol guardar(Rol rol);

    Rol obtenerPorId(String id);

    Rol actualizar(String id, Rol rolActualizado);

    void eliminar(String id);
}


