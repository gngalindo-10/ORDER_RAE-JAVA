package project.order_rae.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import project.order_rae.model.Pedido;
import project.order_rae.model.Produccion;
import project.order_rae.model.Venta;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component 
public class ExcelGenerator {

    public void generarExcelProduccion(List<Produccion> datos, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=produccion_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Producción");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "ID", "Código", "Categoría", "Referencia", "Color", "Material", "Cantidad", "Estado", "Usuario"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }

            // Datos
            int rowNum = 1;
            for (Produccion produccion : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(produccion.getIdProduccion());
                row.createCell(1).setCellValue(produccion.getCodigoProducto());
                row.createCell(2).setCellValue(produccion.getCategoriaProducto());
                row.createCell(3).setCellValue(produccion.getReferenciaProducto());
                row.createCell(4).setCellValue(produccion.getColorProducto());
                row.createCell(5).setCellValue(produccion.getMaterialProducto());
                row.createCell(6).setCellValue(produccion.getCantidadProducto() != null ? produccion.getCantidadProducto() : 0);

                String estado = produccion.getEstadoProducto() != null ? produccion.getEstadoProducto() : "";
                row.createCell(7).setCellValue(estado);

                String usuario = "";
                if (produccion.getUsuario() != null) {
                    usuario = produccion.getUsuario().getNombre() + " " + produccion.getUsuario().getApellidos();
                }
                row.createCell(8).setCellValue(usuario);
            }

            // Ajustar ancho de columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

        public void generarExcelPedidos(List<Pedido> datos, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=pedidos_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Pedidos");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "ID", "Fecha Compra", "Fecha Entrega", "Método Pago", "Total Pago", "Estado Pedido"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }

            // Datos
            int rowNum = 1;
            for (Pedido pedido : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(pedido.getIdPedido());
                row.createCell(1).setCellValue(pedido.getFechaCompra() != null ? pedido.getFechaCompra().toString() : "");
                row.createCell(2).setCellValue(pedido.getFechaEntrega() != null ? pedido.getFechaEntrega().toString() : "");
                row.createCell(3).setCellValue(pedido.getMetodoPago() != null ? pedido.getMetodoPago() : "");
                row.createCell(4).setCellValue(pedido.getTotalDePago() != null ? pedido.getTotalDePago().doubleValue() : 0.0);
                row.createCell(5).setCellValue(pedido.getEstadoPedido() != null ? pedido.getEstadoPedido() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }

        public void generarExcelVentas(List<Venta> datos, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ventas_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Ventas");
            Row header = sheet.createRow(0);
            String[] cols = {"ID", "Fecha Venta", "Total Venta", "Estado", "ID Pedido", "ID Fidelización"};
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
                header.getCell(i).setCellStyle(createHeaderStyle(workbook));
            }

            int rowIdx = 1;
            for (Venta v : datos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(v.getIdVenta());
                row.createCell(1).setCellValue(v.getFechaVenta() != null ? v.getFechaVenta().toString() : "");
                row.createCell(2).setCellValue(v.getTotalVenta() != null ? v.getTotalVenta().doubleValue() : 0.0);
                row.createCell(3).setCellValue(v.getEstadoVenta() != null ? v.getEstadoVenta() : "");
                row.createCell(4).setCellValue(v.getPedidoId() != null ? v.getPedidoId() : 0);
                row.createCell(5).setCellValue(v.getFidelizacionId() != null ? v.getFidelizacionId() : 0);
            }

            for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);
            workbook.write(response.getOutputStream());
        }
    }
}