package project.order_rae.controller;

import project.order_rae.model.Categoria;
import project.order_rae.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService servicio;

    public CategoriaController(CategoriaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("categorias", servicio.listar());
        return "categoria/listarCategoria";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("categoria", new Categoria());
        return "categoria/formCategoria";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttrs) {
        servicio.insertar(categoria);
        redirectAttrs.addFlashAttribute("mensajeExito", "Categoría registrada exitosamente.");
        return "redirect:/categoria";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Categoria categoria = servicio.obtenerPorId(id);
        modelo.addAttribute("categoria", categoria);
        return "categoria/formCategoria";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Categoria categoria, RedirectAttributes redirectAttrs) {
        servicio.actualizar(id, categoria);
        redirectAttrs.addFlashAttribute("mensajeExito", "Categoría actualizada exitosamente.");
        return "redirect:/categoria";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        servicio.eliminar(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Categoría eliminada exitosamente.");
        return "redirect:/categoria";
    }

    @GetMapping("/reportes")
    public String reporte(Model modelo) {
        modelo.addAttribute("categorias", servicio.listar());
        return "categoria/reporteCategoria";
    }
}