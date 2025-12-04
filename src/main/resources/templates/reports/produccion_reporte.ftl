<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Reporte de Producción</title>

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
    <h1>REPORTE DE PRODUCCIÓN</h1>

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
                <th>Código</th>
                <th>Categoría</th>
                <th>Referencia</th>
                <th>Color</th>
                <th>Material</th>
                <th>Cantidad</th>
                <th>Estado</th>
                <th>Usuario</th>
            </tr>
        </thead>
        <tbody>
            <#list datos as p>
            <tr>
                <td>${p.idProduccion!"—"}</td>
                <td>${p.codigoProducto!"—"}</td>
                <td>${p.categoriaProducto!"—"}</td>
                <td>${p.referenciaProducto!"—"}</td>
                <td>${p.colorProducto!"—"}</td>
                <td>${p.materialProducto!"—"}</td>
                <td class="text-center">${p.cantidadProducto!"—"}</td>
                <td>${p.estadoProducto!"—"}</td>
                <td>
                    <#if p.usuario??>
                        ${p.usuario.nombre!"—"} ${p.usuario.apellidos!"—"}
                    <#else>
                        —
                    </#if>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>
