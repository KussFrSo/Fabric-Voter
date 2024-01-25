<template>
  <BaseModal name="vote" title="Votar" class="text-center">
    <h1>Estas seguro que quieres votar esta propuesta?</h1>
    <button
      class="bg-yellow-600 rounded-md w-full h-8 text-white hover:bg-yellow-700 mt-4"
      @click="votar">
      Votar
    </button>
  </BaseModal>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const propuestas = ref([{ nombre: "", descripcion: "" }, { nombre: "", descripcion: "" }]);
    const votation = useVotacionesApi()

    const addPropuesta = () => {
      propuestas.value.push({ nombre: "", descripcion: "" });
    };

    const votar = async() => {
      await votation.votar({
        idPropuesta: 1,
        idVotacion: 1,
        idVotante: 1,
      })
    }
    return { propuestas, addPropuesta, votar };
  },
});
</script>

<style scoped></style>
