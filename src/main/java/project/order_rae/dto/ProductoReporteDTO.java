package project.order_rae.dto;

import lombok.Data;

@Data

public class ProductoReporteDTO {
    private Long id;
    private String codigoProducto;
    private String referenciaProducto;
    private String color;
    private String tipoDeMadera;
    private String estadoProducto;
    private String bodega; 
    private String usuarioNombre;
    private String categoriaNombre;

    // Constructores
    public ProductoReporteDTO() {}

    public ProductoReporteDTO(Object[] row) {
        this.id = ((Number) row[0]).longValue();
        this.codigoProducto = (String) row[1];
        this.referenciaProducto = (String) row[2];
        this.color = (String) row[3];
        this.tipoDeMadera = (String) row[4];
        this.estadoProducto = (String) row[5]; 
        this.bodega = (String) row[6];        
        this.usuarioNombre = (String) row[7]; 
        this.categoriaNombre = (String) row[8]; 
    }

}
