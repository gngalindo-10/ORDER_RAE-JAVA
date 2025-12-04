<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Reporte de Productos</title>
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
    <h1>REPORTE DE PRODUCTOS</h1>
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
                <th>Referencia</th>
                <th>Color</th>
                <th>Precio</th>
                <th>Estado</th>
                <th>Usuario</th>
                <th>Categoría</th>
            </tr>
        </thead>
        <tbody>
            <#list datos as p>
            <tr>
                <td>${p.id!"—"}</td>
                <td>${p.codigoProducto!"—"}</td>
                <td>${p.referenciaProducto!"—"}</td>
                <td>${p.color!"—"}</td>
                <td class="text-center">${p.precio!"—"}</td>
                <td>${p.estadoProducto!"—"}</td>
                <td>
                    <#if p.usuario??>
                        ${p.usuario.nombre!"—"} ${p.usuario.apellidos!"—"}
                    <#else>
                        —
                    </#if>
                </td>
                <td>
                    <#if p.categoria??>
                        ${p.categoria.nombreCategoria!"—"}
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