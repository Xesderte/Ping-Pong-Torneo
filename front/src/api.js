const API_URL = 'http://localhost:8080/api';

export const api = {
    async crearTorneo(payload) {
        const res = await fetch(`${API_URL}/torneos`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
        if (!res.ok) throw new Error('Error al crear torneo');
        return await res.json();
    },

    async registrarEquipos(torneoId, payload) {
        const res = await fetch(`${API_URL}/torneos/${torneoId}/equipos`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
        if (!res.ok) throw new Error('Error al registrar equipos');
        return await res.json();
    },

    async registrarParejas(torneoId, payload) {
        const res = await fetch(`${API_URL}/torneos/${torneoId}/sorteo-parejas`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
        if (!res.ok) throw new Error('Error al armar parejas');
        return await res.json();
    },

    async generarSorteo(torneoId, payload) {
        const res = await fetch(`${API_URL}/torneos/${torneoId}/sorteo`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: payload ? JSON.stringify(payload) : null
        });
        if (!res.ok) throw new Error('Error al generar sorteo');
        return await res.json();
    },

    async obtenerTorneo(torneoId) {
        const res = await fetch(`${API_URL}/torneos/${torneoId}`);
        if (!res.ok) throw new Error('Error al obtener el torneo');
        return await res.json();
    },

    async obtenerFixture(torneoId) {
        const res = await fetch(`${API_URL}/partidos/torneo/${torneoId}/fixture`);
        if (!res.ok) throw new Error('Error al cargar fixture');
        return await res.json();
    },

    async obtenerPosiciones(idFase) {
        const res = await fetch(`${API_URL}/torneos/fase/${idFase}/posiciones`);
        if (!res.ok) throw new Error('Error al cargar posiciones');
        return await res.json();
    },

    async guardarResultado(idPartido, payload) {
        const res = await fetch(`${API_URL}/partidos/${idPartido}/resultado`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
        if (!res.ok) throw new Error('Error al cargar resultado');
        return true;
    }
};
