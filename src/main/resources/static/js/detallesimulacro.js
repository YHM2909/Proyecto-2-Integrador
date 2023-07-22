  document.addEventListener("DOMContentLoaded", function() {
    // Obtén todos los botones "Ver más"
    var buttons = document.querySelectorAll(".masinfo_simulacro");

    // Recorre cada botón y agrega un controlador de eventos clic
    buttons.forEach(function(button) {
      button.addEventListener("click", function() {
         const idsimulacro = this.dataset.idSimulacro;
         const fechasimulacro = this.dataset.fechaSimulacro;
         const notasimulacro = this.dataset.notaSimulacro;
         console.log(idsimulacro)
        // Muestra el modal de SweetAlert
        fetch(`/simulacros/${idsimulacro}`,{
            method: "GET",
        })
            .then(response => response.json())
            .then(data => {
                // Manipular los datos recibidos y mostrarlos en el modal
                const resultados = data;
                console.log(resultados);

                // Variables para acumular la suma de correctas e incorrectas
                let sumaCorrectas = 0;
                let sumaIncorrectas = 0;

                // Construir el contenido de la tabla en el modal
                let tablaHtml = `
                    <p class="text-xl font-bold text-gray-500 mb-3">Simulacro del ${fechasimulacro}</p>
                    <p class="text-sm font-bold text-gray-500 mb-1 text-left">Nota: ${notasimulacro}</p>
                    <div class="grid grid-cols-8 w-full">
                        <div class="col-span-4 p-1">
                            <div class="w-full text-xs rounded-lg bg-sky-500 text-white p-2">Curso</div>
                        </div>
                        <div class="col-span-2 p-1">
                            <div class="w-full text-xs rounded-lg bg-green-400 text-white p-2">Correctas</div>
                        </div>
                        <div class="col-span-2 p-1">
                            <div class="w-full text-xs rounded-lg bg-red-400 text-white p-2">Incorrectas</div>
                        </div>
                    </div>
                `;

                resultados.forEach(resultado => {
                    const nombreCurso = resultado.nombreCurso;
                    const pCorrectas = resultado.pCorrectas;
                    const pIncorrectas = resultado.pIncorrectas;

                    // Actualizar las sumas de correctas e incorrectas
                    sumaCorrectas += pCorrectas;
                    sumaIncorrectas += pIncorrectas;

                    tablaHtml += `
                        <div class="grid grid-cols-8 w-full">
                            <div class="col-span-4 p-0.5">
                                <div class="w-full text-xs rounded-lg bg-gray-300 text-gray-600 p-2">
                                    ${nombreCurso}
                                </div>
                            </div>
                            <div class="col-span-2 p-0.5">
                              <div class="w-full text-xs rounded-lg bg-gray-300 text-gray-600 p-2">
                                 ${pCorrectas}
                              </div>
                            </div>
                            <div class="col-span-2 p-0.5">
                                <div class="w-full text-xs rounded-lg bg-gray-300 text-gray-600 p-2">
                                    ${pIncorrectas}
                                </div>
                            </div>
                        </div>
                    `;
                });

                tablaHtml += `
                    <div class="grid grid-cols-8 w-full">
                        <div class="col-span-4 text-xs">Total</div>
                        <div class="col-span-2 text-xs">${sumaCorrectas}</div>
                        <div class="col-span-2 text-xs">${sumaIncorrectas}</div>
                    </div>
                `;

                // Mostrar el modal con la tabla generada
                Swal.fire({
                    html: tablaHtml,
                    showCancelButton: false,
                    confirmButtonText: "Aceptar"
                });
            })
            .catch(error => {
                 // Manejar el error en caso de que la solicitud falle
                 console.error(error);
            });
      });
    });
  });