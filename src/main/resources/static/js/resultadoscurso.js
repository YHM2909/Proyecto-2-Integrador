function mostrarSolucion(element) {
    var preguntaId = element.getAttribute("data-id-pregunta");

    fetch('/datospregunta?idpregunta=' + preguntaId, {
        method: 'GET'
    })
    .then(function(response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Error en la solicitud');
        }
    })
    .then(function(data) {
        // Mostrar el modal
        Swal.fire({
            html: `
                <p class="text-lg font-bold text-gray-700 mb-5">Resolución</p>
                <p class="text-sm text-gray-700 mb-5">${data.descripcion}</p>
                <img src="/img/${data.imagenRespuesta}" alt="">
            `,
            showCancelButton: false,
            confirmButtonText: "Aceptar"
        });
    })
    .catch(function(error) {
        console.error('Error al obtener los datos de la pregunta:', error);
    });
}

document.addEventListener('DOMContentLoaded', function() {
    var nombreEstudiante = sessionStorage.getItem('nombre');
    document.getElementById("nombre_estudiante").innerText = nombreEstudiante;
})

