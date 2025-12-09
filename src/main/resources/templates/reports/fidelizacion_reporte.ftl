<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Reporte de Fidelización</title>
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
    <h1>REPORTE DE FIDELIZACIÓN</h1>

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
                <th>Usuario</th>
                <th>Fecha de Fidelización</th>
                <th>Puntos Acumulados</th>
                <th>Nivel de Fidelización</th>
            </tr>
        </thead>
        <tbody>
            <#list datos as f>
            <tr>
                <td>${f.id!"—"}</td>
                <td>
                    <#if f.usuario??>
                        ${f.usuario.nombre!"—"} ${f.usuario.apellidos!"—"}
                    <#else>
                        —
                    </#if>
                </td>
                <td>
                    <#if f.fechaFidelizacion??>
                        <#if f.fechaFidelizacion?is_date>
                            ${f.fechaFidelizacion?string("yyyy-MM-dd")}
                        <#else>
                            ${f.fechaFidelizacion}
                        </#if>
                    <#else>
                        —
                    </#if>
                </td>
                <td class="text-center">${f.puntosAcumulados!"—"}</td>
                <td>${f.nivelFidelizacion!"—"}</td>
            </tr>
            <#else>
            <tr>
                <td colspan="5" class="text-center">No hay fidelizaciones registradas.</td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>