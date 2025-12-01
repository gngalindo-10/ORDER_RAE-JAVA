package project.order_rae.service;

import project.order_rae.model.Fidelizacion;
import project.order_rae.repository.FidelizacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FidelizacionService {

    private final FidelizacionRepository repo;

    public FidelizacionService(FidelizacionRepository repo) {
        this.repo = repo;
    }

    // Listar todas las fidelizaciones
    public List<Fidelizacion> listar() {
        return repo.findAll();
    }

    // Insertar nueva fidelización
    public Fidelizacion insertar(Fidelizacion f) {
        if (f.getUsuario() == null) { // ✅ Ahora es "usuario", no "cliente"
            throw new RuntimeException("El usuario es obligatorio");
        }
        return repo.save(f);
    }

    // Actualizar fidelización existente
    public Fidelizacion actualizar(Long id, Fidelizacion f) {
        Fidelizacion existente = obtenerPorId(id);

        existente.setFechaFidelizacion(f.getFechaFidelizacion());
        existente.setPuntosAcumulados(f.getPuntosAcumulados());
        existente.setNivelFidelizacion(f.getNivelFidelizacion());
        existente.setUsuario(f.getUsuario()); // ✅ Asigna usuario

        return repo.save(existente);
    }

    // Eliminar por ID
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // Obtener por ID
    public Fidelizacion obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> 
            new RuntimeException("Fidelización no encontrada con ID: " + id));
    }

    // Métodos opcionales de filtrado
    public List<Fidelizacion> findByNivel(String nivel) {
        return repo.findByNivelFidelizacion(nivel);
    }

    public List<Fidelizacion> findByPuntosRange(Integer min, Integer max) {
        return repo.findByPuntosAcumuladosBetween(min, max);
    }
}