<template>
  <h1>ASSET</h1>
  <BasicComponent></BasicComponent>
  <br>
  <h1>JAMON</h1>
  <JamonComponent></JamonComponent>
</template>


  <script>
    import BasicComponent from './components/BasicComponent.vue';
    import JamonComponent from './components/JamonComponent.vue';
    export default {
      name: 'App',
      components: {
        BasicComponent,
        JamonComponent
      }
    }
  </script>

  <style>
#app {
  font-family: Arial, Helvetica, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

</style>

