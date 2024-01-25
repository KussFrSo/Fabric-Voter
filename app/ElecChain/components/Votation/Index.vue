<template>
  <div class="h-full w-full flex items-center flex-col py-8 px-12 relative">
    <div class="absolute top-5 right-5 flex gap-4">
      <button
        class="bg-orange-500 rounded-lg p-2 w-32 text-white hover:bg-orange-700"
        @click="openModal"
      >
        Crear Votacion
      </button>
      <NuxtLink to="/login" class="bg-yellow-500 text-center rounded-lg p-2 w-32 text-white hover:bg-yellow-700">
          Login
        </NuxtLink>
    </div>

    <h1 class="text-white text-4xl">Votaciones</h1>
    <div class="flex h-8 gap-20 my-4">
      <button
        :class="{
          'text-2xl text-white border-b-2 border-yellow-500':
            proposalTypes === 'active',
          'hover:scale-110 transition-all hover:text-white text-zinc-400':
            proposalTypes !== 'active',
        }"
        @click="proposalTypes = 'active'"
      >
        Activas
      </button>

      <button
        :class="{
          'text-2xl text-white border-b-2 border-yellow-500':
            proposalTypes === 'ended',
          'hover:scale-110 transition-all hover:text-white text-zinc-400':
            proposalTypes !== 'ended',
        }"
        @click="proposalTypes = 'ended'"
      >
        Finalizadas
      </button>
    </div>
    <div class="flex gap-20 mt-4">
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
            A causa de la repentina guerra entre Israel y Palestina, mucha gente
            que estaba viviendo en ghaza a sido bombardeada y muchas familias
            estan sin comida, sin hogar...
          </h5>

          <div class="flex gap-4 mt-2">
            <VotationRangeSlider :value="40" :max="100" color="bg-lime-500">
              <p class="text-xs text-zinc-500 ml-1 -mb-1">Si</p>
            </VotationRangeSlider>

            <VotationRangeSlider :value="20" :max="100" color="bg-red-500">
              <p class="text-xs text-zinc-500 ml-1 -mb-1">No</p>
            </VotationRangeSlider>

            <VotationRangeSlider :value="80" :max="100" color="bg-yellow-600">
              <p class="text-xs text-zinc-500 ml-1 -mb-1">Abstención</p>
            </VotationRangeSlider>
          </div>
        </div>
      </Card>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from "vue";
import Card from "./Card.vue";

export default defineComponent({
  setup() {
    const mockData = ref([
      { id: 1, title: "Titulo 1", description: "Descripción 1" },
      { id: 2, title: "Titulo 2", description: "Descripción 2" },
      { id: 3, title: "Titulo 3", description: "Descripción 3" },
    ]);

    const modal = useModal();

    const proposalTypes = ref<"active" | "ended">("active");
    const value = ref(40); 
    const maxValue = ref(100);

    // Calcular el ancho de la barra de progreso
    const progressBarWidth = computed(() => {
      const percentage = (value.value / maxValue.value) * 100;
      return `${percentage}%`;
    });

    const openModal = () => {
      modal.open(Modals.createVotation)
    }

    return { mockData, proposalTypes, value, openModal };
  },
  components: { Card },
});
</script>

<style scoped>
.range-input {
  -webkit-appearance: none;
  -moz-appearance: none;
}

.range-input::-webkit-slider-thumb {
  -webkit-appearance: none;
}

.range-input::-moz-range-thumb {
  -moz-appearance: none;
}

.range-input::-ms-thumb {
  -ms-appearance: none;
}

.range-input:disabled {
  pointer-events: none;
}
</style>
