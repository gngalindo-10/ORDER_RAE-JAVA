package project.order_rae.service;

import project.order_rae.model.SoportePago;
import project.order_rae.repository.SoportePagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoportePagoService {

    @Autowired
    private SoportePagoRepository soportePagoRepository;

    // ✅ Método findById(Long)
    public Optional<SoportePago> findById(Long id) {
        return soportePagoRepository.findById(id);
    }

    // ✅ Método save(SoportePago)
    public SoportePago save(SoportePago soportePago) {
        return soportePagoRepository.save(soportePago);
    }

    // ✅ Método deleteById(Long)
    public void deleteById(Long id) {
        soportePagoRepository.deleteById(id);
    }

    // ✅ Método findAll() (para listar)
    public List<SoportePago> findAll() {
        return soportePagoRepository.findAll();
    }
}