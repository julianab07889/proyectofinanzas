import { useState } from 'react'

import Movimientos from './Movimientos'
import MetasFinancieras from './MetasFinancieras'
import Presupuesto from './Presupuesto'
import Reportes from './Reportes'
import Configuracion from './Configuracion'

// Componente principal que administra la navegación del sistema.
function Dashboard() {

  const [seccionActiva, setSeccionActiva] = useState('dashboard')

  return (
    <div className="dashboard">

      <aside className="sidebar">

        <h2>Mis Finanzas</h2>

        <nav>

          <button onClick={() => setSeccionActiva('dashboard')}>
            Dashboard
          </button>

          <button onClick={() => setSeccionActiva('movimientos')}>
            Movimientos
          </button>

          <button onClick={() => setSeccionActiva('metas')}>
            Metas Financieras
          </button>

          <button onClick={() => setSeccionActiva('presupuesto')}>
            Presupuesto
          </button>

          <button onClick={() => setSeccionActiva('reportes')}>
            Reportes
          </button>

          <button onClick={() => setSeccionActiva('configuracion')}>
            Configuración
          </button>

        </nav>

      </aside>


      <main className="dashboard-contenido">

        {seccionActiva === 'dashboard' && (
          <>
            <header className="dashboard-header">
              <h1>¡Hola! 👋</h1>
              <p>Aquí tienes un resumen de tus finanzas.</p>
            </header>


            <section className="tarjetas-financieras">

              <div className="tarjeta-financiera">
                <h3>Saldo actual</h3>
                <strong>$ 4.500.000</strong>
                <p>Disponible</p>
              </div>

              <div className="tarjeta-financiera">
                <h3>Ingresos</h3>
                <strong>$ 4.500.000</strong>
                <p>Este mes</p>
              </div>

              <div className="tarjeta-financiera">
                <h3>Gastos</h3>
                <strong>$ 0</strong>
                <p>Este mes</p>
              </div>

              <div className="tarjeta-financiera">
                <h3>Ahorro del mes</h3>
                <strong>$ 4.500.000</strong>
                <p>100% de tus ingresos</p>
              </div>

            </section>


            <section className="resumen-financiero">

              <div className="grafica-placeholder">
                <h2>Ingresos vs Gastos</h2>
                <p>Aquí ubicaremos nuestra gráfica financiera.</p>
              </div>

              <div className="mensaje-financiero">
                <p>Vas bien. Este mes tienes un ahorro positivo.</p>
              </div>

            </section>
          </>
        )}


        {seccionActiva === 'movimientos' && <Movimientos />}

        {seccionActiva === 'metas' && <MetasFinancieras />}

        {seccionActiva === 'presupuesto' && <Presupuesto />}

        {seccionActiva === 'reportes' && <Reportes />}

        {seccionActiva === 'configuracion' && <Configuracion />}

      </main>

    </div>
  )
}

export default Dashboard