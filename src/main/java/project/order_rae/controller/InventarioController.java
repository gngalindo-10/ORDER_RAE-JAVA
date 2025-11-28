package project.order_rae.controller;

import project.order_rae.model.Inventario;
import project.order_rae.model.Producto;
import project.order_rae.service.InventarioService;
import project.order_rae.service.ProductoService;
import project.order_rae.service.UsuarioService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService servicio;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public InventarioController(InventarioService servicio, ProductoService productoService, UsuarioService usuarioService) {
        this.servicio = servicio;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ASESOR COMERCIAL') or hasRole('GERENTE') or hasRole('JEFE LOGISTICO') or hasRole('ADMINISTRADOR')")
    public String listar(Model modelo) {
        modelo.addAttribute("inventarios", servicio.listar());
        return "inventario/listar-inventario";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ASESOR COMERCIAL') or hasRole('GERENTE') or hasRole('JEFE LOGISTICO') or hasRole('ADMINISTRADOR')")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("inventario", new Inventario());
        modelo.addAttribute("productos", productoService.listar());
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "inventario/formulario-inventario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ASESOR COMERCIAL') or hasRole('GERENTE') or hasRole('JEFE LOGISTICO') or hasRole('ADMINISTRADOR')")
    public String guardar(
            @ModelAttribute Inventario inventario,
            @RequestParam(required = false) String nuevoProducto,
            Model modelo) {

        try {
            // 1. CASO: CREAR PRODUCTO NUEVO
            if (nuevoProducto != null && !nuevoProducto.trim().isEmpty()) {
                Producto productoExistente = productoService.findByReferencia(nuevoProducto.trim());
                if (productoExistente == null) {
                    Producto nuevo = new Producto();
                    nuevo.setReferenciaProducto(nuevoProducto.trim());
                    nuevo.setCodigoProducto("PROD_" + System.currentTimeMillis());
                    nuevo.setColor("N/A");
                    nuevo.setPrecio(0.0);
                    nuevo.setEstadoProducto("ACTIVO");
                    nuevo.setCantidadProducto("0");
                    nuevo.setTipoDeMadera("N/A");
                    // Asigna usuario y categoría por defecto
                    nuevo.setUsuario(inventario.getUsuario());
                    // Si no tienes categoría por defecto, podrías asignar una fija o validar
                    if (productoService.listar().size() > 0) {
                        nuevo.setCategoria(productoService.listar().get(0).getCategoria());
                    }
                    Producto guardado = productoService.insertar(nuevo);
                    inventario.setProducto(guardado);
                } else {
                    inventario.setProducto(productoExistente);
                }
            }
            // 2. CASO: EDITAR PRODUCTO EXISTENTE (solo campos editables)
            else if (inventario.getProducto() != null && inventario.getProducto().getId() != null) {
                Producto productoExistente = productoService.obtenerPorId(inventario.getProducto().getId());
                productoExistente.setReferenciaProducto(inventario.getProducto().getReferenciaProducto());
                productoExistente.setPrecio(inventario.getProducto().getPrecio());
                productoExistente.setColor(inventario.getProducto().getColor());
                productoExistente.setTipoDeMadera(inventario.getProducto().getTipoDeMadera());
                // ✅ Usa el método específico para edición ligera
                Producto actualizado = productoService.actualizarDesdeInventario(productoExistente.getId(), productoExistente);
                inventario.setProducto(actualizado);
            }
            // 3. CASO: NO SE SELECCIONÓ NI SE CREÓ PRODUCTO
            else {
                modelo.addAttribute("error", "Debe seleccionar un producto o ingresar uno nuevo.");
                modelo.addAttribute("inventario", inventario);
                modelo.addAttribute("productos", productoService.listar());
                modelo.addAttribute("usuarios", usuarioService.listar());
                return "inventario/formulario-inventario";
            }

            // Guardar inventario
            servicio.insertar(inventario);
            return "redirect:/inventario";

        } catch (Exception e) {
            modelo.addAttribute("error", "Error al guardar: " + e.getMessage());
            modelo.addAttribute("inventario", inventario);
            modelo.addAttribute("productos", productoService.listar());
            modelo.addAttribute("usuarios", usuarioService.listar());
            return "inventario/formulario-inventario";
        }
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ASESOR COMERCIAL') or hasRole('GERENTE') or hasRole('JEFE LOGISTICO') or hasRole('ADMINISTRADOR')")
    public String editar(@PathVariable Long id, Model modelo) {
        Inventario inventario = servicio.obtenerPorId(id);
        modelo.addAttribute("inventario", inventario);
        modelo.addAttribute("productos", productoService.listar());
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "inventario/formulario-inventario";
    }

    @PostMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ASESOR COMERCIAL') or hasRole('GERENTE') or hasRole('JEFE LOGISTICO') or hasRole('ADMINISTRADOR')")
    public String actualizar(@PathVariable Long id, @ModelAttribute Inventario inventario, Model modelo) {
        try {
            // Si se editó el producto desde el formulario, actualiza solo los campos permitidos
            if (inventario.getProducto() != null && inventario.getProducto().getId() != null) {
                Producto productoExistente = productoService.obtenerPorId(inventario.getProducto().getId());
                productoExistente.setReferenciaProducto(inventario.getProducto().getReferenciaProducto());
                productoExistente.setPrecio(inventario.getProducto().getPrecio());
                productoExistente.setColor(inventario.getProducto().getColor());
                productoExistente.setTipoDeMadera(inventario.getProducto().getTipoDeMadera());
                productoService.actualizarDesdeInventario(productoExistente.getId(), productoExistente);
            }

            servicio.actualizar(id, inventario);
            return "redirect:/inventario";
        } catch (Exception e) {
            modelo.addAttribute("error", "Error al actualizar: " + e.getMessage());
            return "redirect:/inventario/editar/" + id;
        }
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ASESOR COMERCIAL') or hasRole('GERENTE') or hasRole('JEFE LOGISTICO') or hasRole('ADMINISTRADOR')")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/inventario";
    }
}