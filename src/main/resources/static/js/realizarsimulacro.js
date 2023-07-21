document.addEventListener('DOMContentLoaded', function() {
  document.getElementById("terminar_examen").addEventListener("click", function() {
    var respuestas = [];

    document.querySelectorAll(".pregunta").forEach(function(pregunta) {
      var preguntaId = pregunta.querySelector(".idpregunta").value;
      var idcurso = pregunta.querySelector(".idcurso").value;

      var idevaluacionsimulacro = document.getElementById("idevaluacionsimulacro").value;

      var respuestaSeleccionada = pregunta.querySelector("input[type='radio']:checked");

      if (respuestaSeleccionada) {
        var respuestaId = respuestaSeleccionada.id.split("-")[2];
        var respuestaValor = respuestaSeleccionada.value;

        respuestas.push({
          idcurso: idcurso,
          preguntaId: preguntaId,
          respuestaId: respuestaId,
          respuestaValor: respuestaValor
        });
      } else {
        // Si no se seleccionó ninguna respuesta, agregar valores predeterminados
        respuestas.push({
          idcurso: idcurso,
          preguntaId: preguntaId,
          respuestaId: 0,
          respuestaValor: 0
        });
      }
    });

    var objetoDatos = {
        idevaluacionsimulacro:idevaluacionsimulacro,
        listarespuestas: respuestas
    };

    var datosJSON = JSON.stringify(objetoDatos);
    console.log(datosJSON);

  });
});
