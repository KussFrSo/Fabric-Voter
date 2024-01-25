<template>
    <div class="bg-zinc-800 w-screen h-screen flex">
        <Votation class="w-full grow"></Votation>
        <VotationDetail v-if="isProposalDetailOpen" class="w-[500px]"></VotationDetail>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
    setup() {
        const isProposalDetailOpen = useState('emailOpen', () => false);
        return {
            isProposalDetailOpen,
        };
    },
})
</script>

<style scoped>

</style>