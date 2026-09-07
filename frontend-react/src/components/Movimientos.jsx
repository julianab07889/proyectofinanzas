import { useState } from 'react'

// Componente encargado de registrar y mostrar movimientos financieros.
function Movimientos() {

  // Estados del formulario.
  const [tipo, setTipo] = useState('ingreso')
  const [descripcion, setDescripcion] = useState('')
  const [valor, setValor] = useState('')

  // Estado que almacena la lista de movimientos registrados.
  const [movimientos, setMovimientos] = useState([])

  // Función que se ejecuta al enviar el formulario.
  function registrarMovimiento(e) {
    e.preventDefault()

    // Validación para evitar registros incompletos.
    if (descripcion === '' || valor === '') {
      return
    }

    // Se crea un nuevo objeto con los datos del movimiento.
    const nuevoMovimiento = {
      id: Date.now(),
      tipo: tipo,
      descripcion: descripcion,
      valor: Number(valor)
    }

    // Se conserva la lista anterior y se agrega el nuevo movimiento.
    setMovimientos([...movimientos, nuevoMovimiento])

    // Se limpian los campos después del registro.
    setDescripcion('')
    setValor('')
  }

  return (
    <div className="seccion-panel">

      <h1>Movimientos</h1>

      <p>
        Registra y consulta tus ingresos y gastos.
      </p>


      {/* Formulario para registrar movimientos */}
      <form
        className="form-movimiento"
        onSubmit={registrarMovimiento}
      >

        <select
          value={tipo}
          onChange={(e) => setTipo(e.target.value)}
        >
          <option value="ingreso">
            Ingreso
          </option>

          <option value="gasto">
            Gasto
          </option>
        </select>


        <input
          type="text"
          placeholder="Descripción"
          value={descripcion}
          onChange={(e) => setDescripcion(e.target.value)}
        />


        <input
          type="number"
          placeholder="Valor"
          value={valor}
          onChange={(e) => setValor(e.target.value)}
        />


        <button type="submit">
          Registrar movimiento
        </button>

      </form>


      {/* Lista de movimientos registrados */}
      <div className="lista-movimientos">

        <h2>Movimientos registrados</h2>

        {movimientos.length === 0 && (
          <p>No hay movimientos registrados.</p>
        )}


        {movimientos.map((movimiento) => (

          <div
            className="movimiento-item"
            key={movimiento.id}
          >

            <span>
              {movimiento.descripcion}
            </span>

            <strong>
              {movimiento.tipo === 'ingreso' ? '+' : '-'}
              {' $ '}
              {movimiento.valor.toLocaleString('es-CO')}
            </strong>

          </div>

        ))}

      </div>

    </div>
  )
}

export default Movimientos