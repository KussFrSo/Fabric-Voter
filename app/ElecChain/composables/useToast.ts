import { useToast } from 'vue-toastification';

export default function () {
	const _toast = useToast();

	const actionSuccess = (message?: string) => {
		const messageToPrint = message ?? 'actions.success';
		return _toast.success(messageToPrint);
	};

	const actionDanger = (message?: string) => {
		const messageToPrint = message ?? 'actions.error';
		return _toast.error(messageToPrint);
	};

	return {
		actionSuccess,
		actionDanger,
	};
}
