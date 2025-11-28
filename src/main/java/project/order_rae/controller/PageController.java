package project.order_rae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }
    @GetMapping("/carrito-compra")
    public String carritoCompra() {
        return "carrito-compra";
    }

    @GetMapping("/promociones")
    public String promociones() {
        return "promociones";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/cotiza")
    public String cotiza() {
        return "cotiza";
    }
    

}

