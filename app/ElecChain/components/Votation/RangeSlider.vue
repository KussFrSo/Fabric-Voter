<template>
    <div class="flex flex-col w-full gap-2">
      <slot></slot>
  <div
    class="relative w-full h-2 bg-transparent/20 rounded-full overflow-hidden"
  >
    <div
      :style="{ width: progressBarWidth }"
      class="h-2 rounded-full"
      :class="color"
    ></div>
  </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, computed } from "vue";

export default defineComponent({
  props: {
    value: {
      type: Number,
      required: true
    },
    max: {
      type: Number,
      required: true
    },
    color: {
      type: String,
      default: "bg-blue-500"
    }
  },
  setup(props) {
    const color = props.color
    const progressBarWidth = computed(() => {
      const percentage = (props.value / props.max) * 100;
      return `${percentage}%`;
    });

    return { progressBarWidth, color };
  }
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
