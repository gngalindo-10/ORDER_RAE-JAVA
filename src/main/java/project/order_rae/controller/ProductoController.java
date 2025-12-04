package project.order_rae.controller;

import project.order_rae.model.Producto;
import project.order_rae.service.ProductoService;
import project.order_rae.service.UsuarioService;
import project.order_rae.service.CategoriaService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import project.order_rae.utils.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService servicio;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;
    private final PdfGenerator pdfGenerator;

    public ProductoController(ProductoService servicio,
                          UsuarioService usuarioService,
                          CategoriaService categoriaService,
                          PdfGenerator pdfGenerator) {
        this.servicio = servicio;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.pdfGenerator = pdfGenerator;
    }

    // Método para listar con búsqueda
    @GetMapping
    public String listar(@RequestParam(required = false) String termino, Model model) {
        List<Producto> productos = servicio.buscarPorTermino(termino);
        model.addAttribute("productos", productos);
        model.addAttribute("termino", termino); // Para mantener el valor en el input
        return "producto/listarProducto";
    }

    // Método para generar PDF (con filtro)
    @GetMapping("/reporte")
    public void generarReporte(@RequestParam(required = false) String termino,
                            HttpServletResponse response) throws Exception {
        List<Producto> productos = servicio.buscarPorTermino(termino);
        pdfGenerator.generarPdf("producto_reporte", productos, response);
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("producto", new Producto());
        modelo.addAttribute("usuarios", usuarioService.listar());
        modelo.addAttribute("categorias", categoriaService.listar());
        return "producto/formProducto";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam Long usuarioId,
            @RequestParam Long categoriaId,
            @ModelAttribute Producto producto,
            RedirectAttributes redirectAttrs) {

        producto.setUsuario(usuarioService.obtenerPorId(usuarioId));
        producto.setCategoria(categoriaService.obtenerPorId(categoriaId));

        servicio.insertar(producto);
        redirectAttrs.addFlashAttribute("mensajeExito", "Producto creado exitosamente."); 
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Producto producto = servicio.obtenerPorId(id);
        modelo.addAttribute("producto", producto);
        modelo.addAttribute("usuarios", usuarioService.listar());
        modelo.addAttribute("categorias", categoriaService.listar());
        return "producto/formProducto";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @RequestParam Long usuarioId,
            @RequestParam Long categoriaId,
            @ModelAttribute Producto producto,
            RedirectAttributes redirectAttrs) {

        producto.setId(id);
        producto.setUsuario(usuarioService.obtenerPorId(usuarioId));
        producto.setCategoria(categoriaService.obtenerPorId(categoriaId));

        servicio.actualizar(id, producto);
        redirectAttrs.addFlashAttribute("mensajeExito", "Producto actualizado exitosamente."); 
        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        servicio.eliminar(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Producto eliminado exitosamente.");
        return "redirect:/productos";
    }
}