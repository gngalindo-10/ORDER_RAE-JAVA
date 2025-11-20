package project.order_rae.controller;

import project.order_rae.model.Producto;
import project.order_rae.service.ProductoService;
import project.order_rae.service.UsuarioService; 
import project.order_rae.service.CategoriaService; // Debes crearlo
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService servicio;
    private final UsuarioService usuarioService;  
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService servicio, UsuarioService usuarioService, CategoriaService categoriaService) {
        this.servicio = servicio;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("productos", servicio.listar());
        return "listar";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("producto", new Producto());
        modelo.addAttribute("usuarios", usuarioService.listar());
        modelo.addAttribute("categorias", categoriaService.listar());
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        servicio.insertar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Producto producto = servicio.obtenerPorId(id);
        modelo.addAttribute("producto", producto);
        modelo.addAttribute("usuarios", usuarioService.listar());
        modelo.addAttribute("categorias", categoriaService.listar());
        return "formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Producto producto) {
        servicio.actualizar(id, producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/productos";
    }
}