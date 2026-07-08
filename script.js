// ===============================
// VARIABLES GENERALES
// ===============================

let usuarioActivo = JSON.parse(localStorage.getItem("usuarioActivo")) || null;

let movimientos = [];
let metasFinancieras = [];
let presupuestos = [];


// ===============================
// ELEMENTOS LOGIN
// ===============================

const login = document.getElementById("login");
const formLogin = document.getElementById("formLogin");
const inputNombre = document.getElementById("nombre");
const inputCorreo = document.getElementById("correoelectronico");
const inputContrasena = document.getElementById("contrasena");

const sidebar = document.getElementById("sidebar");
const contenidoPrincipal = document.querySelector(".contenido-principal");
const saludoUsuario = document.getElementById("saludoUsuario");
const datosUsuarioConfig = document.getElementById("datosUsuarioConfig");
const btnCerrarSesion = document.getElementById("btnCerrarSesion");
const btnCerrarSesionConfig = document.getElementById("btnCerrarSesionConfig");


// ===============================
// ELEMENTOS MOVIMIENTOS
// ===============================

const formularioMovimiento = document.getElementById("formMovimiento");
const inputMonto = document.getElementById("monto");
const selectTipo = document.getElementById("tipo");
const selectCategoria = document.getElementById("categoria");
const inputDescripcion = document.getElementById("descripcion");
const inputFechaMovimiento = document.getElementById("fechaMovimiento");
const tablaMovimientos = document.getElementById("tablamovimientos");
const btnLimpiarMovimientos = document.getElementById("btnLimpiarMovimientos");


// ===============================
// TARJETAS DASHBOARD
// ===============================

const tarjetaSaldo = document.querySelector("#uno h3");
const tarjetaIngresos = document.querySelector("#dos h3");
const tarjetaGastos = document.querySelector("#tres h3");
const tarjetaAhorro = document.querySelector("#cuatro h3");
const textoAhorro = document.querySelector("#cuatro p");
const mensajeFinanciero = document.getElementById("mensajeFinanciero");


// ===============================
// ELEMENTOS METAS
// ===============================

const formularioMetas = document.getElementById("formulario-metas");
const inputNombreMeta = document.getElementById("nombremeta");
const inputValorMeta = document.getElementById("valormeta");
const inputValorAhorrado = document.getElementById("valorahorrado");
const listaMetas = document.getElementById("listametas");


// ===============================
// ELEMENTOS PRESUPUESTO
// ===============================

const formPresupuesto = document.getElementById("formPresupuesto");
const categoriaPresupuesto = document.getElementById("categoriaPresupuesto");
const montoPresupuesto = document.getElementById("montoPresupuesto");
const mesPresupuesto = document.getElementById("mesPresupuesto");
const listaPresupuestos = document.getElementById("listaPresupuestos");


// ===============================
// ELEMENTOS REPORTES
// ===============================

const contenedorReportes = document.getElementById("contenedorReportes");


// ===============================
// FUNCIONES DE APOYO
// ===============================

function formatearCOP(valor) {
  return valor.toLocaleString("es-CO", {
    style: "currency",
    currency: "COP",
    minimumFractionDigits: 0
  });
}

function obtenerFechaHoy() {
  return new Date().toISOString().slice(0, 10);
}

function obtenerMesActual() {
  return new Date().toISOString().slice(0, 7);
}

function formatearFecha(fecha) {
  if (!fecha) return "";
  const partes = fecha.split("-");
  return `${partes[2]}/${partes[1]}/${partes[0]}`;
}

function limpiarTexto(texto) {
  return texto
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

function obtenerUsuarios() {
  return JSON.parse(localStorage.getItem("usuariosFinanzas")) || [];
}

function guardarUsuarios(usuarios) {
  localStorage.setItem("usuariosFinanzas", JSON.stringify(usuarios));
}

function crearClaveUsuario(tipoDato) {
  return `${tipoDato}_${usuarioActivo.correo}`;
}


// ===============================
// LOGIN
// ===============================

formLogin.addEventListener("submit", function(evento) {
  evento.preventDefault();

  const nombre = inputNombre.value.trim();
  const correo = inputCorreo.value.trim().toLowerCase();
  const contrasena = inputContrasena.value.trim();

  if (nombre === "" || correo === "" || contrasena === "") {
    alert("Por favor completa todos los campos.");
    return;
  }

  let usuarios = obtenerUsuarios();

  const usuarioEncontrado = usuarios.find(function(usuario) {
    return usuario.correo === correo;
  });

  if (usuarioEncontrado) {
    if (usuarioEncontrado.contrasena !== contrasena) {
      alert("La contraseña no coincide con este correo.");
      return;
    }

    usuarioActivo = {
      nombre: usuarioEncontrado.nombre,
      correo: usuarioEncontrado.correo
    };
  } else {
    const nuevoUsuario = {
      nombre: nombre,
      correo: correo,
      contrasena: contrasena
    };

    usuarios.push(nuevoUsuario);
    guardarUsuarios(usuarios);

    usuarioActivo = {
      nombre: nombre,
      correo: correo
    };
  }

  localStorage.setItem("usuarioActivo", JSON.stringify(usuarioActivo));

  formLogin.reset();
  mostrarAplicacion();
});

function mostrarLogin() {
  login.classList.remove("oculto");
  sidebar.classList.add("oculto");
  contenidoPrincipal.classList.add("oculto");
}

function mostrarAplicacion() {
  login.classList.add("oculto");
  sidebar.classList.remove("oculto");
  contenidoPrincipal.classList.remove("oculto");

  saludoUsuario.textContent = `¡Hola, ${usuarioActivo.nombre}! 👋`;

  datosUsuarioConfig.textContent = `Sesión activa: ${usuarioActivo.nombre} - ${usuarioActivo.correo}`;

  cargarDatosUsuario();
  pintarTodo();

  setTimeout(function() {
    grafica.resize();
  }, 100);
}

function cerrarSesion() {
  localStorage.removeItem("usuarioActivo");
  usuarioActivo = null;

  movimientos = [];
  metasFinancieras = [];
  presupuestos = [];

  mostrarLogin();
}

btnCerrarSesion.addEventListener("click", cerrarSesion);
btnCerrarSesionConfig.addEventListener("click", cerrarSesion);


// ===============================
// CARGAR Y GUARDAR DATOS POR USUARIO
// ===============================

function cargarDatosUsuario() {
  movimientos = JSON.parse(localStorage.getItem(crearClaveUsuario("movimientos"))) || [];
  metasFinancieras = JSON.parse(localStorage.getItem(crearClaveUsuario("metas"))) || [];
  presupuestos = JSON.parse(localStorage.getItem(crearClaveUsuario("presupuestos"))) || [];
}

function guardarMovimientos() {
  localStorage.setItem(crearClaveUsuario("movimientos"), JSON.stringify(movimientos));
}

function guardarMetas() {
  localStorage.setItem(crearClaveUsuario("metas"), JSON.stringify(metasFinancieras));
}

function guardarPresupuestos() {
  localStorage.setItem(crearClaveUsuario("presupuestos"), JSON.stringify(presupuestos));
}


// ===============================
// MOVIMIENTOS
// ===============================

formularioMovimiento.addEventListener("submit", function(evento) {
  evento.preventDefault();

  const monto = Number(inputMonto.value);
  const tipo = selectTipo.value;
  const categoria = selectCategoria.value;
  const descripcion = inputDescripcion.value.trim();
  const fecha = inputFechaMovimiento.value;

  if (monto <= 0 || categoria === "" || descripcion === "" || fecha === "") {
    alert("Por favor completa correctamente los datos del movimiento.");
    return;
  }

  const nuevoMovimiento = {
    fecha: fecha,
    tipo: tipo,
    categoria: categoria,
    descripcion: descripcion,
    monto: monto
  };

  movimientos.push(nuevoMovimiento);

  guardarMovimientos();
  pintarTodo();

  formularioMovimiento.reset();
  inputFechaMovimiento.value = obtenerFechaHoy();
});

function pintarTablaMovimientos() {
  tablaMovimientos.innerHTML = "";

  if (movimientos.length === 0) {
    tablaMovimientos.innerHTML = `
      <tr>
        <td colspan="6">No hay movimientos registrados.</td>
      </tr>
    `;
    return;
  }

  movimientos.forEach(function(movimiento, index) {
    const fila = document.createElement("tr");

    const signo = movimiento.tipo === "Ingreso" ? "+" : "-";
    const claseMonto = movimiento.tipo === "Ingreso" ? "monto-ingreso" : "monto-gasto";

    fila.innerHTML = `
      <td>${formatearFecha(movimiento.fecha)}</td>
      <td>${movimiento.tipo}</td>
      <td>${movimiento.categoria}</td>
      <td>${limpiarTexto(movimiento.descripcion)}</td>
      <td class="${claseMonto}">${signo}${formatearCOP(movimiento.monto)}</td>
      <td>
        <button class="btn-eliminar" onclick="eliminarMovimiento(${index})">
          Eliminar
        </button>
      </td>
    `;

    tablaMovimientos.appendChild(fila);
  });
}

function eliminarMovimiento(index) {
  movimientos.splice(index, 1);
  guardarMovimientos();
  pintarTodo();
}

btnLimpiarMovimientos.addEventListener("click", function() {
  if (movimientos.length === 0) {
    alert("No tienes movimientos para limpiar.");
    return;
  }

  const confirmar = confirm("¿Seguro que deseas eliminar todos tus movimientos?");

  if (confirmar) {
    movimientos = [];
    guardarMovimientos();
    pintarTodo();
  }
});


// ===============================
// DASHBOARD
// ===============================

function actualizarTarjetas() {
  const mesActual = obtenerMesActual();

  let ingresosMes = 0;
  let gastosMes = 0;
  let ingresosTotales = 0;
  let gastosTotales = 0;

  movimientos.forEach(function(movimiento) {
    if (movimiento.tipo === "Ingreso") {
      ingresosTotales += movimiento.monto;

      if (movimiento.fecha.startsWith(mesActual)) {
        ingresosMes += movimiento.monto;
      }
    } else {
      gastosTotales += movimiento.monto;

      if (movimiento.fecha.startsWith(mesActual)) {
        gastosMes += movimiento.monto;
      }
    }
  });

  const saldoActual = ingresosTotales - gastosTotales;
  const ahorroMes = ingresosMes - gastosMes;

  let porcentajeAhorro = 0;

  if (ingresosMes > 0) {
    porcentajeAhorro = Math.round((ahorroMes / ingresosMes) * 100);
  }

  tarjetaSaldo.textContent = formatearCOP(saldoActual);
  tarjetaIngresos.textContent = formatearCOP(ingresosMes);
  tarjetaGastos.textContent = formatearCOP(gastosMes);
  tarjetaAhorro.textContent = formatearCOP(ahorroMes);
  textoAhorro.textContent = `${porcentajeAhorro}% de tus ingresos`;

  if (movimientos.length === 0) {
    mensajeFinanciero.innerHTML = `
      <i class="fa-solid fa-chart-line"></i>
      <p>Agrega movimientos para ver tu diagnóstico financiero.</p>
    `;
  } else if (ahorroMes > 0) {
    mensajeFinanciero.innerHTML = `
      <i class="fa-solid fa-chart-line"></i>
      <p>Vas bien. Este mes tienes un ahorro positivo de ${formatearCOP(ahorroMes)}.</p>
    `;
  } else if (ahorroMes === 0) {
    mensajeFinanciero.innerHTML = `
      <i class="fa-solid fa-scale-balanced"></i>
      <p>Este mes tus ingresos y gastos están equilibrados.</p>
    `;
  } else {
    mensajeFinanciero.innerHTML = `
      <i class="fa-solid fa-triangle-exclamation"></i>
      <p>Atención. Este mes tus gastos superan tus ingresos por ${formatearCOP(Math.abs(ahorroMes))}.</p>
    `;
  }
}


// ===============================
// GRÁFICA
// ===============================

const ctx = document.getElementById("micanvas").getContext("2d");

let grafica = new Chart(ctx, {
  type: "line",
  data: {
    labels: ["Sin datos"],
    datasets: [
      {
        label: "Ingresos",
        data: [0],
        borderColor: "green",
        backgroundColor: "green",
        tension: 0.3
      },
      {
        label: "Gastos",
        data: [0],
        borderColor: "red",
        backgroundColor: "red",
        tension: 0.3
      }
    ]
  },
  options: {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});

function actualizarGrafica() {
  if (movimientos.length === 0) {
    grafica.data.labels = ["Sin datos"];
    grafica.data.datasets[0].data = [0];
    grafica.data.datasets[1].data = [0];
    grafica.update();
    return;
  }

  const resumenPorFecha = {};

  movimientos.forEach(function(movimiento) {
    if (!resumenPorFecha[movimiento.fecha]) {
      resumenPorFecha[movimiento.fecha] = {
        ingresos: 0,
        gastos: 0
      };
    }

    if (movimiento.tipo === "Ingreso") {
      resumenPorFecha[movimiento.fecha].ingresos += movimiento.monto;
    } else {
      resumenPorFecha[movimiento.fecha].gastos += movimiento.monto;
    }
  });

  const fechas = Object.keys(resumenPorFecha).sort();

  grafica.data.labels = fechas.map(function(fecha) {
    return formatearFecha(fecha);
  });

  grafica.data.datasets[0].data = fechas.map(function(fecha) {
    return resumenPorFecha[fecha].ingresos;
  });

  grafica.data.datasets[1].data = fechas.map(function(fecha) {
    return resumenPorFecha[fecha].gastos;
  });

  grafica.update();
}


// ===============================
// METAS FINANCIERAS
// ===============================

formularioMetas.addEventListener("submit", function(evento) {
  evento.preventDefault();

  const nombremeta = inputNombreMeta.value.trim();
  const valormeta = Number(inputValorMeta.value);
  const valorahorrado = Number(inputValorAhorrado.value);

  if (nombremeta === "" || valormeta <= 0 || valorahorrado < 0) {
    alert("Por favor completa correctamente los datos de la meta.");
    return;
  }

  if (valorahorrado > valormeta) {
    alert("El valor ahorrado no puede ser mayor que el valor total de la meta.");
    return;
  }

  const nuevaMeta = {
    nombremeta: nombremeta,
    valormeta: valormeta,
    valorahorrado: valorahorrado
  };

  metasFinancieras.push(nuevaMeta);

  guardarMetas();
  pintarMetas();

  formularioMetas.reset();
});

function pintarMetas() {
  listaMetas.innerHTML = "";

  if (metasFinancieras.length === 0) {
    listaMetas.innerHTML = "<p>No tienes metas financieras registradas.</p>";
    return;
  }

  metasFinancieras.forEach(function(meta, index) {
    const porcentaje = Math.round((meta.valorahorrado / meta.valormeta) * 100);
    const porcentajeFinal = Math.min(porcentaje, 100);

    const nuevaMeta = document.createElement("div");
    nuevaMeta.classList.add("meta-item");

    nuevaMeta.innerHTML = `
      <div class="circulo-meta" style="background: conic-gradient(#8c36e8 ${porcentajeFinal}%, #eeeeee 0%);">
        <div class="circulo-centro">
          <strong>${porcentajeFinal}%</strong>
        </div>
      </div>

      <div class="info-meta">
        <h4>${limpiarTexto(meta.nombremeta)}</h4>
        <p>${formatearCOP(meta.valorahorrado)} / ${formatearCOP(meta.valormeta)}</p>

        <div class="acciones-meta">
          <button type="button" class="btn-abonar-meta" onclick="abonarMeta(${index})">
            Abonar
          </button>

          <button type="button" class="btn-eliminar-meta" onclick="eliminarMeta(${index})">
            Eliminar
          </button>
        </div>
      </div>
    `;

    listaMetas.appendChild(nuevaMeta);
  });
}

function abonarMeta(index) {
  const abono = Number(prompt("¿Cuánto quieres abonar a esta meta?"));

  if (abono <= 0) {
    alert("El abono debe ser mayor a cero.");
    return;
  }

  const meta = metasFinancieras[index];

  if (meta.valorahorrado + abono > meta.valormeta) {
    alert("El abono supera el valor total de la meta.");
    return;
  }

  meta.valorahorrado += abono;

  guardarMetas();
  pintarMetas();
}

function eliminarMeta(index) {
  metasFinancieras.splice(index, 1);
  guardarMetas();
  pintarMetas();
}


// ===============================
// PRESUPUESTOS
// ===============================

formPresupuesto.addEventListener("submit", function(evento) {
  evento.preventDefault();

  const categoria = categoriaPresupuesto.value;
  const monto = Number(montoPresupuesto.value);
  const mes = mesPresupuesto.value;

  if (categoria === "" || monto <= 0 || mes === "") {
    alert("Por favor completa correctamente los datos del presupuesto.");
    return;
  }

  const presupuestoExistente = presupuestos.find(function(presupuesto) {
    return presupuesto.categoria === categoria && presupuesto.mes === mes;
  });

  if (presupuestoExistente) {
    alert("Ya tienes un presupuesto para esta categoría en ese mes.");
    return;
  }

  const nuevoPresupuesto = {
    categoria: categoria,
    monto: monto,
    mes: mes
  };

  presupuestos.push(nuevoPresupuesto);

  guardarPresupuestos();
  pintarPresupuestos();

  formPresupuesto.reset();
  mesPresupuesto.value = obtenerMesActual();
});

function calcularGastoCategoriaMes(categoria, mes) {
  let total = 0;

  movimientos.forEach(function(movimiento) {
    if (
      movimiento.tipo === "Gasto" &&
      movimiento.categoria === categoria &&
      movimiento.fecha.startsWith(mes)
    ) {
      total += movimiento.monto;
    }
  });

  return total;
}

function pintarPresupuestos() {
  listaPresupuestos.innerHTML = "";

  if (presupuestos.length === 0) {
    listaPresupuestos.innerHTML = "<p>No tienes presupuestos registrados.</p>";
    return;
  }

  presupuestos.forEach(function(presupuesto, index) {
    const gastado = calcularGastoCategoriaMes(presupuesto.categoria, presupuesto.mes);
    const disponible = presupuesto.monto - gastado;

    let porcentaje = Math.round((gastado / presupuesto.monto) * 100);
    let porcentajeVisual = Math.min(porcentaje, 100);

    let claseEstado = "presupuesto-ok";
    let textoEstado = "Vas bien";

    if (porcentaje >= 80 && porcentaje <= 100) {
      claseEstado = "presupuesto-alerta";
      textoEstado = "Cuidado, estás cerca del límite";
    }

    if (porcentaje > 100) {
      claseEstado = "presupuesto-excedido";
      textoEstado = `Excedido por ${formatearCOP(Math.abs(disponible))}`;
    }

    const item = document.createElement("div");
    item.classList.add("presupuesto-item");

    item.innerHTML = `
      <div class="presupuesto-header">
        <div>
          <h4>${presupuesto.categoria}</h4>
          <span>Mes: ${presupuesto.mes}</span>
        </div>

        <button class="btn-eliminar-presupuesto" onclick="eliminarPresupuesto(${index})">
          Eliminar
        </button>
      </div>

      <div class="presupuesto-info">
        <p>Presupuesto: <strong>${formatearCOP(presupuesto.monto)}</strong></p>
        <p>Gastado: <strong>${formatearCOP(gastado)}</strong></p>
        <p>Disponible: <strong>${formatearCOP(disponible)}</strong></p>
      </div>

      <div class="barra-presupuesto">
        <div class="barra-presupuesto-progreso ${claseEstado}" style="width: ${porcentajeVisual}%;"></div>
      </div>

      <p class="estado-presupuesto">${porcentaje}% usado - ${textoEstado}</p>
    `;

    listaPresupuestos.appendChild(item);
  });
}

function eliminarPresupuesto(index) {
  presupuestos.splice(index, 1);
  guardarPresupuestos();
  pintarPresupuestos();
}


// ===============================
// REPORTES
// ===============================

function pintarReportes() {
  contenedorReportes.innerHTML = "";

  if (movimientos.length === 0) {
    contenedorReportes.innerHTML = "<p>No hay datos para generar reportes.</p>";
    return;
  }

  const resumen = {};

  movimientos.forEach(function(movimiento) {
    if (!resumen[movimiento.categoria]) {
      resumen[movimiento.categoria] = {
        ingresos: 0,
        gastos: 0
      };
    }

    if (movimiento.tipo === "Ingreso") {
      resumen[movimiento.categoria].ingresos += movimiento.monto;
    } else {
      resumen[movimiento.categoria].gastos += movimiento.monto;
    }
  });

  Object.keys(resumen).forEach(function(categoria) {
    const card = document.createElement("div");
    card.classList.add("reporte-card");

    card.innerHTML = `
      <h4>${categoria}</h4>
      <p>Ingresos: <strong>${formatearCOP(resumen[categoria].ingresos)}</strong></p>
      <p>Gastos: <strong>${formatearCOP(resumen[categoria].gastos)}</strong></p>
      <p>Balance: <strong>${formatearCOP(resumen[categoria].ingresos - resumen[categoria].gastos)}</strong></p>
    `;

    contenedorReportes.appendChild(card);
  });
}


// ===============================
// NAVEGACIÓN SIDEBAR
// ===============================

const itemsMenu = document.querySelectorAll(".item-menu");
const secciones = document.querySelectorAll(".seccion");

itemsMenu.forEach(function(item) {
  item.addEventListener("click", function() {
    const seccionMostrar = item.getAttribute("data-seccion");

    secciones.forEach(function(seccion) {
      seccion.classList.remove("activa");
    });

    document.getElementById(seccionMostrar).classList.add("activa");

    itemsMenu.forEach(function(opcion) {
      opcion.classList.remove("activo");
    });

    item.classList.add("activo");
  });
});


// ===============================
// PINTAR TODO
// ===============================

function pintarTodo() {
  pintarTablaMovimientos();
  actualizarTarjetas();
  actualizarGrafica();
  pintarMetas();
  pintarPresupuestos();
  pintarReportes();
}


// ===============================
// CARGA INICIAL
// ===============================

inputFechaMovimiento.value = obtenerFechaHoy();
mesPresupuesto.value = obtenerMesActual();

if (usuarioActivo) {
  mostrarAplicacion();
} else {
  mostrarLogin();
}