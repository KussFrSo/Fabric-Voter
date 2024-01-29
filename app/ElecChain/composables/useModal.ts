export enum Modals {
	createVotation = 'createVotation',
	vote ='vote',
}

const curModal = ref<Modals>();
export default function () {
	const open = (name: Modals) => {
		curModal.value = name;
	};

	const close = () => {
		curModal.value = undefined;
	};

	return {
		open,
		close,
		curModal,
	};
}
