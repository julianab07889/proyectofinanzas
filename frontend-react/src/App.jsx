import {useState} from'react'
import Login from './components/Login'
import Dashboard from './components/Dashboard'
import './App.css'

function App() {

  const [sesionIniciada, setSesionIniciada] = useState(false)

  function iniciarSesion() {
    setSesionIniciada(true)
  }

  return (
    <>
      {sesionIniciada
        ? <Dashboard />
        : <Login onLogin={iniciarSesion} />
      }
    </>
  )
}

export default App