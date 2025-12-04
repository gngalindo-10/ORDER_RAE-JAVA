package project.order_rae.controller;

import project.order_rae.model.Pedido;
import project.order_rae.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        return "pedido/listarPedido";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "pedido/formPedido";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Pedido pedido, RedirectAttributes redirectAttrs) {
        pedidoService.insertar(pedido);
        redirectAttrs.addFlashAttribute("mensajeExito", "Pedido creado exitosamente.");
        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        model.addAttribute("pedido", pedido);
        return "pedido/formPedido";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Integer id,
            @ModelAttribute Pedido pedido,
            RedirectAttributes redirectAttrs) {

        pedido.setIdPedido(id);
        pedidoService.actualizar(id, pedido);
        redirectAttrs.addFlashAttribute("mensajeExito", "Pedido actualizado exitosamente.");
        return "redirect:/pedidos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        pedidoService.eliminar(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Pedido eliminado exitosamente.");
        return "redirect:/pedidos";
    }
}