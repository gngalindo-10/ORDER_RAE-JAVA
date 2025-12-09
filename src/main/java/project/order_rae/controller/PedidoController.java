package project.order_rae.controller;

import project.order_rae.model.Pedido;
import project.order_rae.service.PedidoService;
import project.order_rae.utils.ExcelGenerator;
import project.order_rae.utils.PdfGenerator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private ExcelGenerator excelGenerator;

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String termino, Model model) {
        List<Pedido> pedidos = pedidoService.buscarPorTermino(termino);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("termino", termino);
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

    @GetMapping("/reporte")
    public void generarReporte(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        List<Pedido> pedidos = pedidoService.buscarPorTermino(termino);
        pdfGenerator.generarPdf("pedido_reporte", pedidos, response);
    }

    @GetMapping("/excel")
    public void exportarPedidosExcel(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        List<Pedido> pedidos = pedidoService.buscarPorTermino(termino);
        excelGenerator.generarExcelPedidos(pedidos, response);
    }
}