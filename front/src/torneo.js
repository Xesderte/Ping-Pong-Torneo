import { api } from './api.js';

export const torneoState = {
    torneoGlobalId: null,
    formatoGlobal: null,
    idFaseGlobal: null,
    nombresArray: [],
    setsCounter: 0
};

export async function crearTorneo(e, navigateTo) {
    e.preventDefault();
    const payload = {
        nombre: document.getElementById('nombreTorneo').value,
        formato: document.getElementById('formatoTorneo').value,
        cantidadSets: parseInt(document.getElementById('cantSets').value),
        limitePuntos: parseInt(document.getElementById('limitePuntos').value),
        requiereVentaja: document.getElementById('ventaja').checked
    };
    torneoState.formatoGlobal = payload.formato;
    
    try {
        const data = await api.crearTorneo(payload);
        torneoState.torneoGlobalId = data.idTorneo;
        
        if (torneoState.formatoGlobal === 'Fase de Grupos') {
            document.getElementById('configGrupos').style.display = 'block';
        }
        navigateTo('ventana2');
    } catch (error) {
        console.error(error);
        alert("Asegúrate de que el backend Spring Boot esté corriendo en el puerto 8080");
    }
}

export function agregarNombre() {
    const inp = document.getElementById('nombreInput');
    const val = inp.value.trim();
    if (val) {
        torneoState.nombresArray.push(val);
        renderLista();
        inp.value = '';
    }
}

function renderLista() {
    const ul = document.getElementById('listaNombres');
    ul.innerHTML = torneoState.nombresArray.map(n => `<li>${n}</li>`).join('');
}

export async function registrarYGenerarSorteo(navigateTo) {
    if (torneoState.nombresArray.length < 2) return alert("Carga al menos 2 nombres/equipos");
    const modalidad = document.getElementById('modalidadEquipos').value;
    
    try {
        const payloadRegistrar = { jugadores: torneoState.nombresArray.map(n => ({nombre: n})) };
        
        if (modalidad === 'individual') {
            await api.registrarEquipos(torneoState.torneoGlobalId, payloadRegistrar);
        } else {
            await api.registrarParejas(torneoState.torneoGlobalId, payloadRegistrar);
        }

        let payloadSorteo = null;
        if (torneoState.formatoGlobal === 'Fase de Grupos') {
            payloadSorteo = { maxEquiposPorGrupo: parseInt(document.getElementById('maxEquiposGrupo').value) };
        }
        
        await api.generarSorteo(torneoState.torneoGlobalId, payloadSorteo);
        await iniciarVentana3(navigateTo);
    } catch (error) {
        console.error(error);
        alert("Error en el sorteo");
    }
}

export async function iniciarVentana3(navigateTo) {
    navigateTo('ventana3');
    try {
        const torneoInfo = await api.obtenerTorneo(torneoState.torneoGlobalId);
        document.getElementById('tituloTorneo').innerText = "Torneo: " + torneoInfo.nombre;
        torneoState.idFaseGlobal = torneoInfo.fases[0].idFase;

        await cargarFixture();
        await cargarTabla();
    } catch (error) {
        console.error(error);
    }
}

export async function cargarFixture() {
    try {
        const partidos = await api.obtenerFixture(torneoState.torneoGlobalId);
        
        const fechasMap = {};
        partidos.forEach(p => {
            const f = p.fecha || 1;
            if(!fechasMap[f]) fechasMap[f] = [];
            fechasMap[f].push(p);
        });

        let fechaActiva = 1;
        const sortedFechas = Object.keys(fechasMap).sort((a,b)=>a-b);
        for (let f of sortedFechas) {
            const todosJugados = fechasMap[f].every(p => p.estado === 'JUGADO');
            if (!todosJugados) {
                fechaActiva = parseInt(f);
                break;
            }
        }

        const container = document.getElementById('fixtureContainer');
        container.innerHTML = '';

        sortedFechas.forEach(f => {
            const block = document.createElement('div');
            block.className = 'fecha-block';
            if (parseInt(f) > fechaActiva) block.style.opacity = '0.5';
            
            block.innerHTML = `<div class="fecha-header">Fecha ${f} ${parseInt(f) > fechaActiva ? ' (Bloqueada)' : ''}</div>`;
            
            fechasMap[f].forEach(p => {
                const isClickable = (parseInt(f) === fechaActiva && p.estado !== 'JUGADO');
                const btnHtml = isClickable 
                    ? `<button class="btn btn-anotar" data-id="${p.idPartido}" data-local="${p.equipoLocal.nombre}" data-visita="${p.equipoVisitante.nombre}">Anotar</button>` 
                    : `<span style="font-weight:bold; color:var(--primary)">${p.estado}</span>`;
                
                block.innerHTML += `
                    <div class="partido">
                        <div>${p.equipoLocal.nombre} vs ${p.equipoVisitante.nombre}</div>
                        <div>${btnHtml}</div>
                    </div>
                `;
            });
            container.appendChild(block);
        });

        document.querySelectorAll('.btn-anotar').forEach(btn => {
            btn.addEventListener('click', (e) => {
                abrirModal(e.target.dataset.id, e.target.dataset.local, e.target.dataset.visita);
            });
        });
    } catch (error) {
        console.error(error);
    }
}

export async function cargarTabla() {
    try {
        const equipos = await api.obtenerPosiciones(torneoState.idFaseGlobal);
        const tbody = document.getElementById('tablaPosiciones');
        tbody.innerHTML = equipos.map(e => `
            <tr>
                <td>${e.nombre || 'Equipo'}</td>
                <td><strong>${e.puntosTabla || '?'}</strong></td>
                <td>${e.partidosJugados || 0}</td>
                <td>${e.partidosGanados || 0}</td>
                <td>${e.partidosPerdidos || 0}</td>
            </tr>
        `).join('');
    } catch (error) {
        console.error(error);
    }
}

export function abrirModal(id, lName, vName) {
    document.getElementById('partidoActivoId').value = id;
    document.getElementById('partidoLabel').innerText = `${lName} vs ${vName}`;
    document.getElementById('setsContainer').innerHTML = '';
    torneoState.setsCounter = 0;
    agregarSetUI();
    document.getElementById('modalResultado').classList.add('active');
}

export function cerrarModal() { 
    document.getElementById('modalResultado').classList.remove('active'); 
}

export function agregarSetUI() {
    torneoState.setsCounter++;
    const div = document.createElement('div');
    div.style.marginBottom = '10px';
    div.innerHTML = `
        <strong>Set ${torneoState.setsCounter}</strong>
        <div style="display:flex; gap:10px">
            <input type="number" id="l_${torneoState.setsCounter}" placeholder="Pts Local" required>
            <input type="number" id="v_${torneoState.setsCounter}" placeholder="Pts Visita" required>
        </div>
    `;
    document.getElementById('setsContainer').appendChild(div);
}

export async function guardarResultado(e) {
    e.preventDefault();
    const idPartido = document.getElementById('partidoActivoId').value;
    const sets = [];
    for(let i=1; i<=torneoState.setsCounter; i++) {
        sets.push({
            puntosLocal: parseInt(document.getElementById(`l_${i}`).value),
            puntosVisitante: parseInt(document.getElementById(`v_${i}`).value)
        });
    }

    try {
        await api.guardarResultado(idPartido, { sets, partidoFinalizado: true });
        cerrarModal();
        await cargarFixture();
        await cargarTabla();
    } catch(error) {
        alert("Error al cargar resultado: " + error);
    }
}
