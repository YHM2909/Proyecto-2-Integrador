document.addEventListener('DOMContentLoaded', function() {
    var tiempoExamen = parseInt(document.getElementById("tiempo_examen").innerText);
    var tiempoRestante = tiempoExamen * 60; // Convertir minutos a segundos

    // Función para formatear el tiempo restante en minutos y segundos
    function formatearTiempoRestante() {
        var minutos = Math.floor(tiempoRestante / 60);
        var segundos = tiempoRestante % 60;

        // Agregar ceros a la izquierda si los minutos o segundos son menores a 10
        var minutosFormateados = minutos < 10 ? "0" + minutos : minutos;
        var segundosFormateados = segundos < 10 ? "0" + segundos : segundos;

        return minutosFormateados + ":" + segundosFormateados;
    }

    // Función para actualizar el tiempo restante en el span
    function actualizarTiempoRestante() {
        document.getElementById("tiempo_restante").innerText = formatearTiempoRestante();
        tiempoRestante--;

        if (tiempoRestante < 0) {
            // Aquí puedes agregar lógica adicional cuando se acabe el tiempo
            clearInterval(intervaloTiempo);
        }
    }

    // Actualizar el tiempo restante inicial
    actualizarTiempoRestante();

    // Intervalo para actualizar el tiempo restante cada segundo (1000 milisegundos)
    var intervaloTiempo = setInterval(actualizarTiempoRestante, 1000);

    var nombreEstudiante = sessionStorage.getItem('nombre');
    document.getElementById("nombre_estudiante").innerText = nombreEstudiante;

});
