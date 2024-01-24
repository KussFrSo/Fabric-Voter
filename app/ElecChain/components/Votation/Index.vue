<template>
	<div class="h-full w-full flex items-center flex-col p-8 relative">
        <div class="absolute top-5 right-5 flex gap-4">
            <button class="bg-orange-500 rounded-lg p-2 w-32 text-white hover:bg-orange-700">Crear Votacion</button>
            <button class="bg-yellow-500 rounded-lg p-2 w-32 text-white hover:bg-yellow-700">Login</button>
        </div>

        <h1 class="text-white text-4xl">Votaciones</h1>
        <div class="flex gap-20 my-4">
            <button class="text-2xl text-white border-b-2 border-yellow-500">Activas</button>
            <button class="text-2xl text-zinc-400">Finalizadas</button>
        </div>
        <div class="flex gap-12">
            <Card v-for="item in mockData" :key="item.id" class="card">
                <div class="flex flex-col">
                    <div class="flex justify-between text-zinc-300 font-bold">
                        <h4 class="text-sm">Porpuesta #1</h4>
                        <h4 class="text-sm">6d 23h 12m</h4>
                    </div>
                    <h4 class="text-zinc-300 font-bold text-sm mt-2">
                        Ayuda Humanitaria para la gente de la zona de ghaza
                    </h4>
                    <h5 class="text-zinc-500 font-bold text-xs mt-2">
                        A causa de la repentina guerra entre Israel y Palestina, mucha gente que estaba viviendo en ghaza a sido bombardeada y muchas familias estan sin comida, sin hogar...
                    </h5>


                </div>
		    </Card>
        </div>
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
        ]);
        return { mockData };
    },
    components: { Card }
});
</script>

<style scoped></style>