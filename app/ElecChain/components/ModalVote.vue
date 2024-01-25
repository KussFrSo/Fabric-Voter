<template>
  <BaseModal name="vote" title="Votar" class="text-center">
    <h1>Estas seguro que quieres votar esta propuesta?</h1>
    <button
      class="bg-yellow-600 rounded-md w-full h-8 text-white hover:bg-yellow-700 mt-4">
      Votar
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
