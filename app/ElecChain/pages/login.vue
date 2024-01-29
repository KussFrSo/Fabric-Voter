<template>
  <div
    class="w-full h-screen flex flex-col items-center justify-center bg-light-tint bg-zinc-700"
  >
    <h1 class="text-6xl text-yellow-500 text-lato">ElecChain</h1>
    <div class="flex flex-col mb-4 w-[350px] mt-4">
      <label for="email" class="mb-2 font-semibold text-zinc-300">Email</label>
      <input
        autocomplete="false"
        id="email"
        type="text"
        class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        placeholder="email@gmail.com"
        v-model="email"
      />
    </div>

    <div class="flex flex-col mb-4 w-[350px]">
      <label for="pass" class="mb-2 font-semibold text-zinc-300"
        >Contraseña</label
      >
      <input
        autocomplete="false"
        id="pass"
        type="password"
        class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        placeholder="********"
        v-model="password"
      />
    </div>
    <div class="w-[350px] -mt-4 ml-1">
      <NuxtLink
        to="/signin"
        class="text-xs text-yellow-500 hover:text-yellowe-600 hover:underline hover:cursor-pointer"
        >No tienes cuenta?</NuxtLink
      >
    </div>

    <button
      class="bg-yellow-500 rounded-lg p-2 text-white hover:bg-yellow-700 w-[350px] mt-4 text-center"
      @click="onClickLogin"
    >
      Login
    </button>
  </div>
</template>

<script lang="ts">
export default defineComponent({
  setup(props) {
    definePageMeta({
      title: "Login",
    });
    const router = useRouter();
    const auth = useAuthApi();
    const toast = useToast();

    const showLoginForm = ref<boolean>(false);
    const isLoading = ref<boolean>(false);

    const email = ref<string>();
    const password = ref<string>();

    const onClickShowLogin = () => {
      showLoginForm.value = true;
    };

    const onClickSignIn = () => {
      router.push("/signin");
    };

    const onClickLogin = async () => {
      try {
        isLoading.value = true;
        
        const {data} = await auth.login({
          email: email.value || "",
          password: password.value || "",
        });
        toast.actionSuccess("Login correcto!")
        localStorage.setItem('token', data.value.token);
        router.push("/");
      } catch (err) {
        toast.actionDanger("Usuario o contraseña incorrecta!")
      } finally {
        isLoading.value = false;
      }
    };

    return {
      onClickLogin,
      isLoading,
      onClickSignIn,
      showLoginForm,
      onClickShowLogin,
      email,
      password,
    };
  },
});
</script>
