<template>
  <div
    class="bg-zinc-900/30 w-full h-full flex flex-col justify-between p-4 relative border-l-2 border-zinc-700"
  >
    <Icon
      name="mingcute:close-line"
      color="white"
      class="absolute left-5 top-5 text-xl transition-all hover:scale-125"
      @click="isProposalDetailOpen = false"
    />
    <div class="flex flex-col w-full h-full items-center">
      <h1 class="text-2xl text-white">Proposal #1</h1>
      <h1 class="text-base text-zinc-500">10:00AM, 10 Feb, 2024</h1>

      <div
        class="flex gap-20 justify-center w-full my-8 p-4 bg-zinc-700/30 rounded-md"
      >
        <div>
          <h2 class="text-xs text-zinc-300">Tiempo Restante:</h2>
          <h2 class="text-xl text-white">6d 23h 12m</h2>
        </div>
        <div>
          <h2 class="text-xs text-zinc-300">Estado:</h2>
          <h2 class="text-xl text-white">Votando</h2>
        </div>
      </div>

      <h4 class="text-lg text-zinc-300 font-bold text-sm mt-2">
        Ayuda humanitaria para la gente de la zona de ghaza
      </h4>
      <h5 class="text-zinc-500 text-sm mt-2">
        A causa de la repentina guerra entre Israel y Palestina, mucha gente que
        estaba viviendo en ghaza a sido bombardeada y muchas familias estan sin
        comida, sin hogar A causa de la repentina guerra entre Israel y
        Palestina, mucha gente que estaba viviendo en ghaza a sido bombardeada y
        muchas familias estan sin comida, sin hogar...
      </h5>

      <div
        class="flex w-full justify-between border-b border-zinc-700 px-1 mt-8 pb-1"
      >
        <h1 class="text-lg text-white">Votos</h1>
        <Icon
          name="solar:info-circle-bold"
          color="white"
          class="text-lg hover:scale-110 transition-all mt-2"
        />
      </div>

      <div class="mt-4 w-full flex flex-col gap-6">
        <VotationRangeSlider :value="40" :max="100" color="bg-lime-500">
          <div class="flex justify-between">
            <p class="text-base text-zinc-500 ml-1 -mb-1">Si</p>
            <p class="text-base text-zinc-500 mr-1 -mb-1">40%</p>
          </div>
        </VotationRangeSlider>

        <VotationRangeSlider :value="20" :max="100" color="bg-red-500">
          <div class="flex justify-between">
            <p class="text-base text-zinc-500 ml-1 -mb-1">No</p>
            <p class="text-base text-zinc-500 mr-1 -mb-1">20%</p>
          </div>
        </VotationRangeSlider>

        <VotationRangeSlider :value="80" :max="100" color="bg-yellow-600">
          <div class="flex justify-between">
            <p class="text-base text-zinc-500 ml-1 -mb-1">Abstención</p>
            <p class="text-base text-zinc-500 mr-1 -mb-1">80%</p>
          </div>
        </VotationRangeSlider>
      </div>
    </div>

    <button
      :class="{
        'pointer-events-none hover:cursor-not-allowed bg-yellow-600/15 text-white/15':
          !userLogged,
        'cursor-pointer': userLogged,
      }"
      class="bg-yellow-600 rounded-md w-full h-12 text-white hover:bg-yellow-700"
      @click="openModal"
    >
      Votar
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  setup() {
    const modal = useModal();
    const isProposalDetailOpen = useState("propuestaOpen", () => false);
    const userLogged = useState("userLogged", () => false);

    const openModal = () => {
      modal.open(Modals.vote);
    };

    return { isProposalDetailOpen, openModal, userLogged };
  },
});
</script>

<style scoped></style>
