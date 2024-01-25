<template>
  <div
    class="w-full h-screen flex flex-col items-center justify-center bg-light-tint bg-zinc-700"
  >
    <h1 class="text-6xl text-yellow-500 text-lato">ElecChain</h1>
    <div class="flex flex-col mb-4 w-[350px] mt-4">
      <label for="email" class="mb-2 font-semibold text-zinc-300">Email</label>
      <input
        id="email"
        type="text"
        class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        placeholder="email@gmail.com"
      />
    </div>

    <div class="flex flex-col mb-4 w-[350px]">
      <label for="pass" class="mb-2 font-semibold text-zinc-300"
        >Contraseña</label
      >
      <input
        id="pass"
        type="password"
        class="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        placeholder="********"
      />
    </div>
    <div class="w-[350px] -mt-4 ml-1">
        <NuxtLink to="/signin" class="text-xs text-yellow-500 hover:text-yellowe-600 hover:underline hover:cursor-pointer">No tienes cuenta?</NuxtLink>

    </div>

      <NuxtLink to="/" class="bg-yellow-500 rounded-lg p-2 text-white hover:bg-yellow-700 w-[350px] mt-4 text-center"> Login </NuxtLink>
  </div>
</template>

<script lang="ts">
export default defineComponent({
  setup(props) {
    definePageMeta({
      title: "Login",
    });
    const router = useRouter();

    const showLoginForm = ref<boolean>(false);
    const isLoading = ref<boolean>(false);

    const onClickShowLogin = () => {
      showLoginForm.value = true;
    };

    const onClickSignIn = () => {
      router.push("/signin");
    };

    const onClickLogin = async (values: {
      email: string;
      password: string;
      passwordRemember: boolean;
    }) => {
      try {
        isLoading.value = true;
        //await auth.signIn(values.email, values.password, values.passwordRemember);
        router.push("/");
        //toast.actionSuccess();
      } catch (err) {
        //toast.exception(err);
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
    };
  },
});
</script>
