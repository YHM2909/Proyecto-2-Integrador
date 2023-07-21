    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');

    togglePassword.addEventListener('click', function () {
      const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
      passwordInput.setAttribute('type', type);
      this.querySelector('i').classList.toggle('fa-solid fa-eye');
      this.querySelector('i').classList.toggle('fa-solid fa-eye-slash');
    });

    function login() {
        var usuario = document.getElementById("usuario").value;
        var password = document.getElementById("password").value;

        var data = {
            usuario: usuario,
            password: password
        };
        fetch("/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
        .then(function(response) {
            if (response.ok) {
                response.json().then(function(data) {
                    console.log(data);
                    var usuario = data.usuario;
                    var nombres = data.nombres;
                    var idalumno = data.idAlumno;
                    var iduniversidad = data.iduniversidad;
                    sessionStorage.setItem('usuario', usuario);
                    sessionStorage.setItem('idalumno', idalumno);
                    sessionStorage.setItem('nombre', nombres);
                    sessionStorage.setItem('iduniversidad', iduniversidad);
                });
                window.location.href = "/dashboard"; // Redirige a la página principal
            } else {
                // Las credenciales son incorrectas
                document.getElementById("error").textContent = "Credenciales incorrectas";
            }
        })
        .catch(function(error) {
            console.log(error);
        });
    }