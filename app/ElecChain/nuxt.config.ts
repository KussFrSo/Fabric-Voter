// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  css: ['~/assets/css/variables.css', '~/assets/css/animations.css', '~/assets/css/toast.css'],
  modules: ['@nuxtjs/tailwindcss', 'nuxt-icon'],
})
