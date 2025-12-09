<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Reporte de Ventas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            font-size: 12px;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .fecha {
            text-align: center;
            margin-bottom: 30px;
            color: #666;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #000;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .text-center {
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>REPORTE DE VENTAS</h1>

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
                <th>Fecha Venta</th>
                <th>Total Venta</th>
                <th>Estado Venta</th>
                <th>ID Pedido</th>
                <th>ID Fidelización</th>
            </tr>
        </thead>
        <tbody>
            <#list datos as v>
            <tr>
                <td>${v.idVenta!"—"}</td>
                <td>
                    <#if v.fechaVenta??>
                        <#if v.fechaVenta?is_date>
                            ${v.fechaVenta?string("yyyy-MM-dd")}
                        <#else>
                            ${v.fechaVenta}
                        </#if>
                    <#else>
                        —
                    </#if>
                </td>
                <td class="text-center">${v.totalVenta!"—"}</td>
                <td>${v.estadoVenta!"—"}</td>
                <td>${v.pedidoId!"—"}</td>
                <td>${v.fidelizacionId!"—"}</td>
            </tr>
            <#else>
            <tr>
                <td colspan="6" class="text-center">No hay ventas registradas.</td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>