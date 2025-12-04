<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Reporte de Usuarios</title>

    <style>
        body { font-family: Arial, sans-serif; margin: 40px; font-size: 12px; }
        h1 { text-align: center; color: #333; margin-bottom: 20px; }
        .fecha { text-align: center; margin-bottom: 30px; color: #666; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #000; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>

<body>

<h1>REPORTE DE USUARIOS</h1>

<div class="fecha">
    Generado el:
    <#if fechaActual??>
        ${fechaActual?string("dd/MM/yyyy HH:mm:ss")}
    <#else> — </#if>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombres</th>
        <th>Apellidos</th>
        <th>Documento</th>
        <th>Correo</th>
        <th>Género</th>
        <th>Teléfono</th>
        <th>Rol</th>
        <th>Estado</th>
        <th>Creado</th>
    </tr>
    </thead>

    <tbody>
    <#list datos as u>
    <tr>
        <td>${u.id!"—"}</td>
        <td>${u.nombre!"—"}</td>
        <td>${u.apellidos!"—"}</td>
        <td>${u.documento!"—"}</td>
        <td>${u.correo!"—"}</td>
        <td>${u.genero!"—"}</td>
        <td>${u.telefono!"—"}</td>
        <td>
            <#if u.rol??>
                ${u.rol.nombre!"—"}
            <#else>—</#if>
        </td>
        <td>${u.estado!"—"}</td>
        <td>
            <#if u.createdAt??>
                ${u.createdAt?string("dd/MM/yyyy HH:mm")}
            <#else>—</#if>
        </td>
    </tr>
    </#list>
    </tbody>
</table>

</body>
</html>
