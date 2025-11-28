package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Long id;

    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @Column(name = "estado_categoria")
    private String estadoCategoria;
}