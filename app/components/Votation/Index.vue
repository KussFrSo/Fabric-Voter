<template>
	<div class="container">
		<Card v-for="item in mockData" :key="item.id" class="card">
			<h3>{{ item.title }}</h3>
			<p>{{ item.description }}</p>
		</Card>
	</div>
</template>


<script lang="ts">
import { defineComponent, ref } from 'vue';
import Card from './Card.vue';

export default defineComponent({
    setup() {
        const mockData = ref([
            { id: 1, title: 'Titulo 1', description: 'Descripción 1' },
            { id: 2, title: 'Titulo 2', description: 'Descripción 2' },
            { id: 3, title: 'Titulo 3', description: 'Descripción 3' },
            // ... más datos
        ]);
        return { mockData };
    },
    components: { Card }
});
</script>

<style scoped></style>