import {
    crearTorneo,
    agregarNombre,
    registrarYGenerarSorteo,
    abrirModal,
    cerrarModal,
    agregarSetUI,
    guardarResultado
} from './torneo.js';

document.addEventListener('DOMContentLoaded', () => {
    // Navigation logic
    const navBtns = document.querySelectorAll('.nav-btn');
    const windows = document.querySelectorAll('.window');

    function navigateTo(windowId) {
        navBtns.forEach(btn => btn.classList.remove('active'));
        windows.forEach(win => win.classList.remove('active'));
        
        document.querySelector(`.nav-btn[data-target="${windowId}"]`).classList.add('active');
        document.getElementById(windowId).classList.add('active');
    }

    navBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            navigateTo(btn.dataset.target);
        });
    });

    // Event Listeners for Ventana 1
    document.getElementById('formConfig').addEventListener('submit', (e) => crearTorneo(e, navigateTo));
    document.getElementById('formatoTorneo').addEventListener('change', (e) => {
        const configGrupos = document.getElementById('configGrupos');
        if (e.target.value === 'Fase de Grupos') {
            configGrupos.style.display = 'block';
        } else {
            configGrupos.style.display = 'none';
        }
    });

    // Event Listeners for Ventana 2
    document.getElementById('btnAgregarNombre').addEventListener('click', agregarNombre);
    document.getElementById('btnSorteo').addEventListener('click', () => registrarYGenerarSorteo(navigateTo));

    // Event Listeners for Modal
    document.getElementById('btnCerrarModal').addEventListener('click', cerrarModal);
    document.getElementById('btnAddSet').addEventListener('click', agregarSetUI);
    document.getElementById('formResultado').addEventListener('submit', guardarResultado);
});
