<template>
  <div class="h-full w-full flex items-center flex-col py-8 px-12 relative">
    <div class="absolute top-5 right-5 flex gap-4 items-center">
      <button
        class="bg-orange-500 rounded-lg p-2 w-32 text-white hover:bg-orange-700"
        @click="openModal"
      >
        Crear Votacion
      </button>
      <NuxtLink
        v-if="!userLogged"
        to="/login"
        class="bg-yellow-500 text-center rounded-lg p-2 w-32 text-white hover:bg-yellow-700"
      >
        Login
      </NuxtLink>
      <div v-else>
        <span
          class="text-white text-base hover:underline underline-offset-2 hover:cursor-pointer"
          @click="logout"
          >{{ userEmail }}</span
        >
      </div>
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
      <Card v-for="item in propuestas" :key="item.id" class="card">
        <div class="flex flex-col">
          <div class="flex justify-between text-zinc-300 font-bold">
            <h4 class="text-sm">Porpuesta #{{ item.idPropuesta }}</h4>
            <h4 class="text-sm">60d</h4>
          </div>
          <h4 class="text-zinc-300 font-bold text-sm mt-2">
            {{ item.nombre }}
          </h4>
          <h5 class="text-zinc-500 font-bold text-xs mt-2">
            {{ item.detallePropuesta }}.
          </h5>

          <div class="flex gap-4 mt-2">
            <VotationRangeSlider :value="item.votos" :max="100" color="bg-lime-500">
              <div class="flex justify-between">
                <p class="text-xs text-zinc-500 ml-1 -mb-2">Votos</p>
                <p class="text-xs text-white mr-1 -mb-1">{{ item.votos }} votos</p>
              </div>
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
import * as jwtDecode from "jwt-decode";
import mock from "./../../assets/mockdata.json";

export default defineComponent({
  async setup() {
    const mockData = ref([
      { id: 1, title: "Titulo 1", description: "Descripción 1" },
      { id: 2, title: "Titulo 2", description: "Descripción 2" },
      { id: 3, title: "Titulo 3", description: "Descripción 3" },
    ]);

    const modal = useModal();
    const votation = useVotacionesApi();

    const userLogged = useState("userLogged", () => false);
    const proposalTypes = ref<"active" | "ended">("active");
    const value = ref(40);
    const maxValue = ref(100);
    const userEmail = ref("");
    const propuestas = ref<any>(mock);

    onMounted(async () => {
      if (process.client) {
        const token = localStorage.getItem("token");
        if (token) {
          try {
            const decodedToken = jwtDecode.jwtDecode(token);
            userEmail.value = decodedToken.sub || "";
            userLogged.value = true;
          } catch (error) {
            console.error("Error al decodificar el token:", error);
          }
        }
      }
    });

    // const getPropuestas = async () => {
    //   try {
    //     const { data } = await votation.getPropuestasDeVotacion({ id: "1" });
    //     propuestas.value = data.value;
    //   } catch (error) {
    //     console.log("Test");
    //     propuestas.value = mock;
    //     console.log(propuestas.value);
    //   }
    // };
    // getPropuestas();

    const calcularTotalDeVotos = () => {
      return propuestas.value.map((votacion: any) => {
        const totalVotosPorVotacion = votacion.propuestas.reduce((suma, propuesta) => {
          return suma + propuesta.votos;
        }, 0);
        console.log(totalVotosPorVotacion)
        return totalVotosPorVotacion
      });
    }
    // Calcular el ancho de la barra de progreso
    const progressBarWidth = computed(() => {
      const percentage = (value.value / maxValue.value) * 100;
      return `${percentage}%`;
    });

    const openModal = () => {
      modal.open(Modals.createVotation);
    };

    const logout = () => {
      localStorage.removeItem("token");
      userEmail.value = "";
      userLogged.value = false;
    };

    return {
      mockData,
      proposalTypes,
      value,
      openModal,
      userEmail,
      logout,
      userLogged,
      propuestas,
      calcularTotalDeVotos,
    };
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
