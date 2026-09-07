// componentw encargado de mostrar el formulario de inicio de sesion.
import { useState } from "react"
function Login({onLogin}) {
    const [nombre, setNombre]= useState('')
    const [correo, setCorreo] = useState('')
    const [contrasena, setContrasena] = useState('')

function manejarInicioSesion(e){   
        e.preventDefault()
        console. log('Nombre:', nombre)
        console.log('Correo:', correo)
        console.log('Contraseña:', contrasena)
        if (nombre !==''&& correo !==''&& contrasena !==''){onLogin() 

         }   
       }    
     return (
        //contenido del formulario
    
     < div className="login-container">
     <div className="login-tarjeta">
        <h1>Iniciar sesion</h1>
        <p>
            Ingresar tus datos para ver tu informe financiero.
        </p>
        <form onSubmit={manejarInicioSesion}>
        <div className="campo-formulario">
            <label htmlFor="nombre">
                Nombre de usuario
                </label>
                <input
                id="nombre"
                type="text"
                placeholder="Escribe tu nombre"
                value={nombre}
                onChange={(e) => setNombre (e.target.value)}
                />
                </div>
                <div className= "campo-formulario">
                    <label htmlFor="correo">
                        correo electronico
                    </label>
                    </div>
                    <input
                    id="correo"
                    type="email"
                    placeholder="correo@email.com"
                    value={correo}
                    onChange={(e) =>setCorreo(e.target.value)}
                    />
                    <div className="campo-formulario">
                    <label htmlFor="contrasena">
                    contrasena
                    </label>
                    <input
                    id="contrasena"
                    type="password"
                    placeholder="Escribe tu contrasena"
                    value={contrasena}
                    onChange={(e) =>setContrasena(e.target.value)}
                    />
                    </div>
                    <button type="submit">
                        Iniciar sesion
                    </button>
                </form>
            </div>
       </div>
     )
     }
 export default Login
