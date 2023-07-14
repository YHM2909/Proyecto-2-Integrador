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

    document.getElementById("terminar_examen").addEventListener("click", function() {
      var respuestas = [];

      // Recorrer todas las preguntas
      document.querySelectorAll(".pregunta").forEach(function(pregunta) {
        var preguntaId = pregunta.querySelector(".idpregunta").value;
        var idcurso = document.getElementById("idcurso").value;
        var idevaluacion = document.getElementById("idevaluacion").value;
        // Obtener la respuesta seleccionada para la pregunta actual
        var respuestaSeleccionada = pregunta.querySelector("input[type='radio']:checked");
        if (respuestaSeleccionada) {
          var respuestaId = respuestaSeleccionada.id.split("-")[2];
          var respuestaValor = respuestaSeleccionada.value;

          // Agregar la pregunta y la respuesta a la lista de respuestas
          respuestas.push({
            preguntaId: preguntaId,
            respuestaId: respuestaId,
            respuestaValor: respuestaValor
          });
        }
      });
      // Crear el objeto con los datos
      console.log("---------------IDCURSO--------------")
      console.log(idcurso.value)
      console.log("---------------IDEVALUACION--------------")
      console.log(idevaluacion.value)
      var objetoDatos = {
            idcurso:parseInt(idcurso.value) ,
            idevaluacion: parseInt(idevaluacion.value),
            listarespuestas: respuestas
      };
        // Imprimir el objeto en la consola

       var datosJSON = JSON.stringify(objetoDatos);
       console.log(datosJSON);
        fetch('/resultevaluacioncurso', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: datosJSON
        })
          .then(function(response) {
            if (response.ok) {
              // Obtener el JSON de la respuesta
              return response.json();
            } else {
              throw new Error('Error en la solicitud');
            }
          })
          .then(function(data) {
            // Obtener el ID de evaluación del objeto JSON
            var idevaluacion = data.idevaluacion;

            // Redirigir al usuario a la vista deseada, pasando el ID de evaluación como parámetro
            window.location.href = "/resultadoscurso?idevaluacion=" + idevaluacion;
          })
          .catch(function(error) {
            // Manejo de errores
            console.error(error);
          });
});
})