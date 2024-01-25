<template>
	<div class="shadow-lg bg-zinc-700/20 rounded-md p-4 h-52 w-80 hover:scale-110 transition-all cursor-pointer" @click="isProposalDetailOpen = true">
		<div v-if="isLoading" class="w-full h-full flex items-center justify-center">
			<AtomSpinner />
		</div>
		<slot v-else></slot>
	</div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
	props: {
		isLoading: {
			type: Boolean,
			default: false,
		},
	},
	setup() {
		const isProposalDetailOpen = useState('emailOpen', () => false);
		return {isProposalDetailOpen};
	},
});
</script>

<style scoped></style>