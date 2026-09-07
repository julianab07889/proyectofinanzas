import { useState } from 'react'

// Componente encargado de registrar y mostrar presupuestos.
function Presupuesto() {

  const [categoria, setCategoria] = useState('')
  const [monto, setMonto] = useState('')
  const [presupuestos, setPresupuestos] = useState([])

  // Registra un nuevo presupuesto.
  function registrarPresupuesto(e) {
    e.preventDefault()

    // Evita guardar información incompleta.
    if (categoria === '' || monto === '') {
      return
    }

    const nuevoPresupuesto = {
      id: Date.now(),
      categoria: categoria,
      monto: Number(monto)
    }

    // Conserva los presupuestos anteriores y agrega el nuevo.
    setPresupuestos([...presupuestos, nuevoPresupuesto])

    // Limpia el formulario.
    setCategoria('')
    setMonto('')
  }

  return (
    <div className="seccion-panel">

      <h1>Presupuesto</h1>

      <p>
        Organiza cuánto dinero deseas destinar a cada categoría.
      </p>

      <form
        className="form-presupuesto"
        onSubmit={registrarPresupuesto}
      >

        <input
          type="text"
          placeholder="Categoría"
          value={categoria}
          onChange={(e) => setCategoria(e.target.value)}
        />

        <input
          type="number"
          placeholder="Monto disponible"
          value={monto}
          onChange={(e) => setMonto(e.target.value)}
        />

        <button type="submit">
          Crear presupuesto
        </button>

      </form>


      <div className="lista-presupuestos">

        <h2>Presupuestos registrados</h2>

        {presupuestos.length === 0 && (
          <p>No hay presupuestos registrados.</p>
        )}

        {presupuestos.map((presupuesto) => (

          <div
            className="presupuesto-item"
            key={presupuesto.id}
          >

            <div>
              <h3>{presupuesto.categoria}</h3>
              <p>Monto disponible</p>
            </div>

            <strong>
              $ {presupuesto.monto.toLocaleString('es-CO')}
            </strong>

          </div>

        ))}

      </div>

    </div>
  )
}

export default Presupuesto