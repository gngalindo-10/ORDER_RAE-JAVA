package project.order_rae.controller;

import project.order_rae.model.Fidelizacion;
import project.order_rae.service.FidelizacionService;
import project.order_rae.service.UsuarioService;
import project.order_rae.utils.ExcelGenerator;
import project.order_rae.utils.PdfGenerator;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/fidelizacion")
public class FidelizacionController {

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private ExcelGenerator excelGenerator;

    private final FidelizacionService servicio;
    private final UsuarioService usuarioService;

    public FidelizacionController(FidelizacionService servicio, UsuarioService usuarioService) {
        this.servicio = servicio;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String termino, Model model) {
        var fidelizaciones = servicio.buscarPorTermino(termino);
        model.addAttribute("fidelizaciones", fidelizaciones);
        model.addAttribute("termino", termino);
        return "fidelizacion/listarFidelizacion";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("fidelizacion", new Fidelizacion());
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "fidelizacion/formFidelizacion";
    }

    @GetMapping("/reporte")
    public void generarReporte(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        var fidelizaciones = servicio.buscarPorTermino(termino);
        pdfGenerator.generarPdf("fidelizacion_reporte", fidelizaciones, response);
    }

    @GetMapping("/excel")
    public void exportarExcel(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        var fidelizaciones = servicio.buscarPorTermino(termino);
        excelGenerator.generarExcelFidelizacion(fidelizaciones, response);
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam Long usuarioId,
            @ModelAttribute Fidelizacion fidelizacion,
            RedirectAttributes redirectAttrs) {

        fidelizacion.setUsuario(usuarioService.obtenerPorId(usuarioId));
        servicio.insertar(fidelizacion);
        redirectAttrs.addFlashAttribute("mensajeExito", "Fidelización creada exitosamente.");
        return "redirect:/fidelizacion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Fidelizacion fidelizacion = servicio.obtenerPorId(id);
        modelo.addAttribute("fidelizacion", fidelizacion);
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "fidelizacion/formFidelizacion";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @RequestParam Long usuarioId,
            @ModelAttribute Fidelizacion fidelizacion,
            RedirectAttributes redirectAttrs) {

        fidelizacion.setId(id);
        fidelizacion.setUsuario(usuarioService.obtenerPorId(usuarioId));
        servicio.actualizar(id, fidelizacion);
        redirectAttrs.addFlashAttribute("mensajeExito", "Fidelización actualizada exitosamente.");
        return "redirect:/fidelizacion";
    }

    // Cambiado a @PostMapping
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        servicio.eliminar(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Fidelización eliminada exitosamente.");
        return "redirect:/fidelizacion";
    }
}