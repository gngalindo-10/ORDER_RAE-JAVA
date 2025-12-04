package project.order_rae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.order_rae.model.Produccion;
import project.order_rae.repository.ProduccionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProduccionService {

    @Autowired
    private ProduccionRepository produccionRepository;

    public List<Produccion> listar() {
        return produccionRepository.findAll();
    }

    public Optional<Produccion> buscarPorId(Long id) {
        return produccionRepository.findById(id);
    }

    public Produccion guardar(Produccion produccion) {
        return produccionRepository.save(produccion);
    }

    public void eliminar(Long id) {
        produccionRepository.deleteById(id);
    }

    public List<Produccion> buscarPorTermino(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listar();
        }
        return produccionRepository.buscarPorTermino(termino.trim());
    }
}