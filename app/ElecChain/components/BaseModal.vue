<template>
	<Transition name="page">
		<div v-if="isOpen" class="fixed top-0 bg-black/30 left-0 right-0 bottom-0 z-50">
			<div class="flex flex-row items-center justify-center w-full h-full px-4" @click.self="closeModal">
				<div class="bg-white xl:min-w-[500px] xl:min-h-[250px] max-w-[700px] rounded-xl flex flex-col gap-6 p-8">
					<!-- header -->
					<div class="flex flex-row justify-between items-center text-xl border-b border-primary-default pb-2">
						<h3 class="font-bold font-lg text-muted-40">{{title }}</h3>
						<div class="cursor-pointer flex" @click="closeModal">
							<Icon name="solar:close-circle-linear" />
						</div>
					</div>
					<div class="h-full">
						<slot></slot>
					</div>
				</div>
			</div>
		</div>
	</Transition>
</template>

<script lang="ts">
import { Modals } from '~/composables/useModal';

export default defineComponent({
	props: {
		name: {
			type: String as PropType<Modals>,
			required: true,
		},
		title: {
			type: String,
			required: false,
			default: '',
		},
		text: {
			type: String,
			required: false,
			default: '',
		},
		sendText: {
			type: String,
			required: false,
			default: '',
		},
		isLoading: {
			type: Boolean,
			required: false,
			default: false,
		},
		send: {
			type: Function,
			required: false,
			default: () => {},
		},
		alternativeText: {
			type: String,
			required: false,
			default: '',
		},
		alternativeSend: {
			type: Function,
			required: false,
			default: () => {},
		},
	},
	emits: ['closed'],
	setup(props, ctx) {
		const modal = useModal();

		const isOpen = computed(() => {
			return modal.curModal.value === props.name;
		});

		const closeModal = () => {
			modal.close();
			ctx.emit('closed');
		};

		return {
			isOpen,
			modal,
			closeModal,
		};
	},
});
</script>

<style scoped></style>
