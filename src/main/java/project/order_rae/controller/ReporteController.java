package project.order_rae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.order_rae.service.ProductoService;

import java.util.List;

@Controller
public class ReporteController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/reportes")
    public String mostrarReportes(
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) Double precioMin,
        @RequestParam(required = false) Double precioMax,
        @RequestParam(required = false) String busqueda,
        Model model) {

        List<?> productosFiltrados = productoService.findFiltered(estado, precioMin, precioMax, busqueda);
        model.addAttribute("productos", productosFiltrados);

        // Pasar los parámetros de filtro para mantenerlos en el formulario
        model.addAttribute("estado", estado);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("busqueda", busqueda);

        return "reportes";
    }
}