document.addEventListener("DOMContentLoaded", function() {
  const btnrealizarexamen = document.getElementById("realizar_examen");
  btnrealizarexamen.addEventListener("click", function () {
    const cursoId = this.dataset.cursoId;
    const temariosSeleccionados = [];
    fetch(`/temarioscurso?idcurso=${cursoId}`)
      .then(response => response.json())
      .then(data => {
        // Recorrer los temarios y generar los elementos de los checkbox
        const formEmpezarExamen = document.querySelector('.form-empezarexamen');
        data.forEach(temario => {
          const checkboxDiv = document.createElement('div');
          checkboxDiv.className = 'flex items-center my-2 bg-gray-200 py-3 px-3 rounded-lg';
          checkboxDiv.innerHTML = `
            <input id="checkbox-${temario.idTemario}" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2">
            <label for="checkbox-${temario.idTemario}" class="ml-2 text-sm font-medium text-gray-900">${temario.nombre}</label>
          `;
          formEmpezarExamen.appendChild(checkboxDiv);
          // Escuchar el evento change de los checkboxes
          const checkbox = checkboxDiv.querySelector(`#checkbox-${temario.idTemario}`);
          checkbox.addEventListener('change', function() {
            if (this.checked) {
              temariosSeleccionados.push(temario.idTemario);
            } else {
              const index = temariosSeleccionados.indexOf(temario.idTemario);
              if (index > -1) {
                temariosSeleccionados.splice(index, 1);
              }
            }
          });
        });
      })
      .catch(error => {
        console.error('Error:', error);
      });
    Swal.fire({
      html: `
        <p class="text-xl text-gray-700 mb-5">Elige los temas que deseas incluir en el examen</p>
        <form class="form-empezarexamen" enctype="multipart/form-data">
        </form>
      `,
      showCancelButton: true,
      confirmButtonText: "Comenzar"
    }).then(result => {
         if (result.isConfirmed) {
           // Crear un formulario para enviar los datos al controlador
           const form = document.createElement('form');
           form.method = 'POST';
           form.action = '/examencurso';

           // Obtener el ID del alumno de sessionStorage
           const idAlumno = sessionStorage.getItem('idalumno');

           // Agregar campo oculto con el ID del alumno al formulario
           const idAlumnoInput = document.createElement('input');
           idAlumnoInput.type = 'hidden';
           idAlumnoInput.name = 'idAlumno';
           idAlumnoInput.value = idAlumno;
           form.appendChild(idAlumnoInput);

           // Agregar los campos ocultos con los IDs de los temarios seleccionados y el ID del curso
           temariosSeleccionados.forEach(idTemario => {
             const input = document.createElement('input');
             input.type = 'hidden';
             input.name = 'temariosSeleccionados';
             input.value = idTemario;
             form.appendChild(input);
           });

           const cursoInput = document.createElement('input');
           cursoInput.type = 'hidden';
           cursoInput.name = 'cursoId';
           cursoInput.value = cursoId;
           form.appendChild(cursoInput);

           // Agregar el formulario al documento y enviarlo
           document.body.appendChild(form);
           form.submit();
         }
    });
  });
});
