export interface delegarVotoDTO {
	idVotacion: number;
	idVotante: number;
	to: number;
}

export interface votoDTO {
	idPropuesta: number;
	idVotacion: number;
	idVotante: number;
}

export interface IdDTO {
	id: string;
}

export default function () {
	const getVotacion = (body: IdDTO) => {
		return useApi(`/votacion/getVotacion`, {
			body,
			method: 'GET',
		});
	};

    const getVotacionesActivas = (body: IdDTO) => {
		return useApi(`/votacion/getVotacionesActivas`, {
			body,
			method: 'GET',
		});
	};

    const getPropuestasDeVotacion = (body: IdDTO) => {
		return useApi(`/votacion/getPropuestasDeVotacion`, {
			body,
			method: 'GET',
		});
	};

    const registrarVotacion = (body: IdDTO) => {
		return useApi(`/votacion/registrarVotacion`, {
			body,
			method: 'POST',
		});
	};

    const delegarVoto = (body: delegarVotoDTO) => {
		return useApi(`/votacion/delegarVoto`, {
			body,
			method: 'POST',
		});
	};

    const finalizarVotacion = (body: IdDTO) => {
		return useApi(`/votacion/finalizarVotacion`, {
			body,
			method: 'POST',
		});
	};

    const iniciarVotacion = (body: IdDTO) => {
		return useApi(`/votacion/iniciarVotacion`, {
			body,
			method: 'POST',
		});
	};

    const votar = (body: votoDTO) => {
		return useApi(`/votacion/registrarVotacion`, {
			body,
			method: 'POST',
		});
	};

	return {
		votar,
        iniciarVotacion,
        finalizarVotacion,
        delegarVoto,
        registrarVotacion,
        getPropuestasDeVotacion,
        getVotacionesActivas,
        getVotacion
	};
}