<template>
	<div class="bg-zinc-900/30 w-full h-full flex flex-col justify-between p-4 relative">
	
		<Icon name="mingcute:close-line" color="white" class=" absolute left-5 top-5 text-xl transition-all hover:scale-125"/>
		<div class="flex flex-col w-full h-full items-center">
			<h1>Proposal #1</h1>
			<h1>10:00AM, 10 Feb, 2024</h1>
		</div>

		<div>
			<div>
			<h2>Tiempo Restante:</h2>
			<h2>6d 23h 12m</h2>
		</div>
		<div>
			<h2>Estado:</h2>
			<h2>Votando</h2>
		</div>
		</div>

		<h4 class="text-zinc-300 font-bold text-sm mt-2">
                        Ayuda Humanitaria para la gente de la zona de ghaza
                    </h4>
                    <h5 class="text-zinc-500 font-bold text-xs mt-2">
                        A causa de la repentina guerra entre Israel y Palestina, mucha gente que estaba viviendo en ghaza a sido bombardeada y muchas familias estan sin comida, sin hogar...
                    </h5>
		

		<button class="bg-yellow-600 rounded-md w-full h-12 text-white">Votar</button>

	</div>
</template>


<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
	setup() {

		return {  };
	},
});
</script>

<style scoped></style>