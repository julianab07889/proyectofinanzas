import { useState } from 'react'

// Componente encargado de registrar y mostrar metas financieras.
function MetasFinancieras() {

  const [nombreMeta, setNombreMeta] = useState('')
  const [valorMeta, setValorMeta] = useState('')
  const [valorAhorrado, setValorAhorrado] = useState('')
  const [metas, setMetas] = useState([])

  // Registra una nueva meta financiera.
  function registrarMeta(e) {
    e.preventDefault()

    if (
      nombreMeta === '' ||
      valorMeta === '' ||
      valorAhorrado === ''
    ) {
      return
    }

    const nuevaMeta = {
      id: Date.now(),
      nombre: nombreMeta,
      valorObjetivo: Number(valorMeta),
      ahorrado: Number(valorAhorrado)
    }

    setMetas([...metas, nuevaMeta])

    setNombreMeta('')
    setValorMeta('')
    setValorAhorrado('')
  }

  return (
    <div className="seccion-panel">

      <h1>Metas Financieras</h1>

      <p>
        Registra y controla tus objetivos de ahorro.
      </p>

      <form
        className="form-meta"
        onSubmit={registrarMeta}
      >

        <input
          type="text"
          placeholder="Nombre de la meta"
          value={nombreMeta}
          onChange={(e) => setNombreMeta(e.target.value)}
        />

        <input
          type="number"
          placeholder="Valor objetivo"
          value={valorMeta}
          onChange={(e) => setValorMeta(e.target.value)}
        />

        <input
          type="number"
          placeholder="Valor ahorrado"
          value={valorAhorrado}
          onChange={(e) => setValorAhorrado(e.target.value)}
        />

        <button type="submit">
          Crear meta
        </button>

      </form>


      <div className="lista-metas">

        <h2>Mis metas</h2>

        {metas.length === 0 && (
          <p>No hay metas registradas.</p>
        )}

        {metas.map((meta) => {

          const porcentaje =
            (meta.ahorrado / meta.valorObjetivo) * 100

          return (
            <div
              className="meta-item"
              key={meta.id}
            >

              <h3>{meta.nombre}</h3>

              <p>
                Objetivo: $
                {meta.valorObjetivo.toLocaleString('es-CO')}
              </p>

              <p>
                Ahorrado: $
                {meta.ahorrado.toLocaleString('es-CO')}
              </p>

              <strong>
                Progreso: {porcentaje.toFixed(0)}%
              </strong>

            </div>
          )
        })}

      </div>

    </div>
  )
}

export default MetasFinancieras