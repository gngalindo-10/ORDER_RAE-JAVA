package project.order_rae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.order_rae.model.Produccion;
import project.order_rae.service.ProduccionService;
import project.order_rae.service.UsuarioService;
import project.order_rae.service.ProductoService;
import project.order_rae.utils.ExcelGenerator;
import project.order_rae.utils.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Controller
@RequestMapping("/produccion")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private ExcelGenerator excelGenerator;

    @GetMapping("/reporte")
    public void generarReporte(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        List<Produccion> producciones = produccionService.buscarPorTermino(termino);
        pdfGenerator.generarPdf("produccion_reporte", producciones, response);
    }

    @GetMapping("/excel")
    public void exportarProduccionExcel(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        List<Produccion> producciones = produccionService.buscarPorTermino(termino);
        excelGenerator.generarExcelProduccion(producciones, response);
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String termino, Model model) {
        List<Produccion> producciones = produccionService.buscarPorTermino(termino);
        model.addAttribute("producciones", producciones);
        model.addAttribute("termino", termino); 
        return "produccion/listarProduccion";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("produccion", new Produccion());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("productos", productoService.listar());
        return "produccion/formProduccion";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Produccion produccion,
                          @RequestParam Long usuariosId,
                          @RequestParam(required = false) Long productoId,
                          RedirectAttributes redirectAttrs) {
        produccion.setUsuario(usuarioService.obtenerPorId(usuariosId));
        if (productoId != null) {
            produccion.setProducto(productoService.obtenerPorId(productoId));
        }
        produccionService.guardar(produccion);
        redirectAttrs.addFlashAttribute("mensajeExito", "Producción guardada.");
        return "redirect:/produccion";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Produccion produccion = produccionService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producción editada."));
        model.addAttribute("produccion", produccion);
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("productos", productoService.listar());
        return "produccion/formProduccion";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute Produccion produccion,
                             @RequestParam Long usuariosId,
                             @RequestParam(required = false) Long productoId,
                             RedirectAttributes redirectAttrs) {
        produccion.setIdProduccion(id);
        produccion.setUsuario(usuarioService.obtenerPorId(usuariosId));
        if (productoId != null) {
            produccion.setProducto(productoService.obtenerPorId(productoId));
        }
        produccionService.guardar(produccion);
        redirectAttrs.addFlashAttribute("mensajeExito", "Producción actualizada.");
        return "redirect:/produccion";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        produccionService.eliminar(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Producción eliminada.");
        return "redirect:/produccion";
    }
}
