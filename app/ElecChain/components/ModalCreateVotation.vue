<template>
  <BaseModal name="createVotation" title="Crear Votación">
    <div class="flex flex-col mb-4 w-full mt-4">
      <label for="votationName" class="mb-2 font-semibold text-black"
        >Nombre Votacion</label
      >
      <input
        id="votationName"
        type="text"
        class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        placeholder="Donde gastar la ultima inversion?"
      />
    </div>

    <div class="flex flex-col mb-4 w-full mt-4">
      <label for="email" class="mb-2 font-semibold text-black">Email</label>
      <input
        id="email"
        type="text"
        class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        placeholder="email@gmail.com"
      />
    </div>

    <div class="flex justify-between mb-4">
      <h1 class="text-xl font-semibold underline underline-offset-2">
        Propuestas de la votación:
      </h1>
      <button
        class="bg-black rounded-md px-2 hover:bg-white transition-all border border-black"
        @click="addPropuesta"
      >
        <Icon
          name="ph:plus-bold"
          class="w-full h-full hover:text-black transition-all text-white"
        />
      </button>
    </div>

<div class="overflow-auto h-[300px]">
    <div
      v-for="(propuesta, index) in propuestas"
      :key="index"
      class="mb-4 flex flex-col gap-2"
    >
      <div class="flex flex-col gpa-2">
        <label for="email" class="mb-2 font-semibold text-black"
          >Nombre propuesta #{{ index + 1 }}</label
        >
        <input
          v-model="propuesta.nombre"
          type="text"
          class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          placeholder="Nombre de la propuesta"
        />
      </div>
      <div class="flex flex-col gpa-2">
        <label for="email" class="mb-2 font-semibold text-black"
          >Descripcion propuesta #{{ index + 1 }}</label
        >
        <textarea
          v-model="propuesta.descripcion"
          class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none"
          placeholder="Descripción de la propuesta"
        ></textarea>
      </div>
    </div>
</div>

    <button
      to="/"
      class="bg-yellow-500 rounded-lg p-2 text-white hover:bg-yellow-700 w-full mt-4 text-center"
    >
      Crear
    </button>
  </BaseModal>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const propuestas = ref([{ nombre: "", descripcion: "" }, { nombre: "", descripcion: "" }]);

    const addPropuesta = () => {
      propuestas.value.push({ nombre: "", descripcion: "" });
    };
    return { propuestas, addPropuesta };
  },
});
</script>

<style scoped></style>
