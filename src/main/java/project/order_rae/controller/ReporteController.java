package project.order_rae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.order_rae.dto.ProductoReporteDTO;
import project.order_rae.service.ProductoService;

import java.util.List;

@Controller
public class ReporteController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/reportes")
    public String mostrarReportes(
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) String referencia,
        @RequestParam(required = false) Long categoria,
        @RequestParam(required = false) String color,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) String tipoMadera,
        @RequestParam(required = false) String bodega,
        Model model) {

        List<ProductoReporteDTO> productos = productoService.findReporteFiltered(
            codigo, referencia, categoria, color, estado, tipoMadera, bodega);

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", productoService.listarCategorias());

        // Pasar los parámetros de filtro para mantenerlos en el formulario
        model.addAttribute("codigo", codigo);
        model.addAttribute("referencia", referencia);
        model.addAttribute("categoria", categoria);
        model.addAttribute("color", color);
        model.addAttribute("estado", estado);
        model.addAttribute("tipoMadera", tipoMadera);
        model.addAttribute("bodega", bodega);

        return "reportes";
    }
}