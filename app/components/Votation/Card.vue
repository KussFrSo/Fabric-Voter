<template>
	<div class="shadow-lg rounded-md p-4">
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
		return {};
	},
});
</script>

<style scoped></style>