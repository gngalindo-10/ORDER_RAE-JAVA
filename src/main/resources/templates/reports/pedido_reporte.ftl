<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Reporte de Pedidos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; font-size: 12px; }
        h1 { text-align: center; color: #333; margin-bottom: 20px; }
        .fecha { text-align: center; margin-bottom: 30px; color: #666; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #000; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .text-center { text-align: center; }
    </style>
</head>
<body>
    <h1>REPORTE DE PEDIDOS</h1>
    <div class="fecha">
        Generado el: 
        <#if fechaActual??>
            ${fechaActual?string("dd/MM/yyyy HH:mm:ss")}
        <#else>
            -- Fecha no disponible --
        </#if>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Fecha Compra</th>
                <th>Fecha Entrega</th>
                <th>Método Pago</th>
                <th>Total Pago</th>
                <th>Estado Pedido</th>
            </tr>
        </thead>
        <tbody>
            <#list datos as p>
            <tr>
                <td>${p.idPedido!"—"}</td>
                <td>${p.fechaCompra!"—"}</td>
                <td>${p.fechaEntrega!"—"}</td>
                <td>${p.metodoPago!"—"}</td>
                <td class="text-center">${p.totalDePago!"—"}</td>
                <td>${p.estadoPedido!"—"}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>