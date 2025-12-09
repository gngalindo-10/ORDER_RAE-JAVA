package project.order_rae.service;

import project.order_rae.model.Pedido;
import project.order_rae.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public long countPedidos() {
        return pedidoRepository.count();
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    public Pedido insertar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizar(Integer id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = obtenerPorId(id);

        pedidoExistente.setFechaCompra(pedidoActualizado.getFechaCompra());
        pedidoExistente.setFechaEntrega(pedidoActualizado.getFechaEntrega());
        pedidoExistente.setMetodoPago(pedidoActualizado.getMetodoPago());
        pedidoExistente.setTotalDePago(pedidoActualizado.getTotalDePago());
        pedidoExistente.setEstadoPedido(pedidoActualizado.getEstadoPedido());

        return pedidoRepository.save(pedidoExistente);
    }

    public void eliminar(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> buscarPorTermino(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listar();
        }
        return pedidoRepository.buscarPorTermino(termino.trim());
    }
}
