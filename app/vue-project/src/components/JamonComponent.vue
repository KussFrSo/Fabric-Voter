<template>
    <div>
    <div>
        <form @submit.prevent="registrarJamon">
            <div>
                <label for="id">Jamon ID</label>
                <input type="number" id="id" v-model="formData.jamonId">
            </div>
            <div>
                <label for="raza">Raza</label>
                <input type="text" id="raza" v-model="formData.raza">
            </div>
            <div>
                <label for="alimentacion">Alimentacion</label>
                <input type="text" id="alimentacion" v-model="formData.alimentacion">
            </div>
            <div>
                <label for="denominacionOrigen">Denominacion de Origen</label>
                <input type="text" id="denominacionOrigen" v-model="formData.denomOrig">
            </div>
            <div>
                <label for="owner">Propietario</label>
                <input type="text" id="owner" v-model="formData.owner">
            </div>
            <div>
                <label for="valor">Valor</label>
                <input type="number" id="valor" v-model="formData.valor">
            </div>
            <button>Registrar Jamon</button>
        </form>
            <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ creationResponse.code }}</h3>
                <p> {{ creationResponse.data }}</p>
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="imprimirJamon">
            <div>
                <label for="jamon_id">Jamon Id</label>
                <input type="number" id="jamon_id" v-model="jamon_id">
            </div>
            <button>Imprimir Jamon</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ printResponse.code }}</h3>
                <p> {{ printResponse.data }}</p>
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="listarJamones">
            <button>Listar todos los Jamones</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ getAllResponse.code }}</h3>
                <ul>
                    <li v-for="jamon in getData" :key="jamon">
                        {{ jamon.id }} - {{ jamon.raza }} - {{ jamon.alimentacion }} - {{ jamon.denominacionOrigen }} - {{ jamon.propietario }} - {{ jamon.valor }} - {{ jamon.intermediarios }}
                    </li>
                </ul>
                
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="borrarJamon">
            <div>
                <label for="deleteId">Jamon Id</label>
                <input type="number" id="deleteId" v-model="deleteId">
            </div>
            <button>Borrar Jamon</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ deleteResponse.code }}</h3>
                <p> {{ deleteResponse.data }}</p>
                <hr />
            </div>
    </div>

    <div>
        <form @submit.prevent="transferenciaJamon">
            <div>
                <label for="jamonId">Jamon ID</label>
                <input type="number" id="jamonId" v-model="transferData.jamonId">
            </div>
            <div>
                <label for="newOwner">Nuevo Propietario</label>
                <input type="text" id="newOwner" v-model="transferData.newOwner">
            </div>
            <div>
                <label for="newValue">Nuevo Valor</label>
                <input type="number" id="newValue" v-model="transferData.newValue">
            </div>

            <button>Transferir Jamon</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ transferResponse.code }}</h3>
                <p> {{ transferResponse.data }}</p>
                <hr />
            </div>
    </div>
    </div>
</template>

<script>
import axios from 'axios'
    export default {
        name: 'JamonComponent',
        data() {
            return {
                posts: [],
                creationResponse: [],
                getAllResponse: [],
                getData: [],
                printResponse: [],
                getResponse: [],
                deleteResponse: [],
                transferResponse: [],
                errorMsg: '',
                formData :{
                    jamonId: 0,
                    raza: '',
                    alimentacion: '',
                    denomOrig: '',
                    owner: '',
                    valor: 0,
                    intermediarios:[]
                },
                jamon_id:0,
                deleteId:0,
                transferData :{
                    jamonId: 0,
                    newOwner: "",
                    newValue: 0
                }
            }
        },
        methods: {
            imprimirJamon() {
                axios.get(`http://localhost:8080/jamon/cargarJamon?jamonId=${jamon_id.value}` )
                    .then((response) => {
                        console.log(response.data)
                        this.printResponse = response.data;
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            listarJamones() {
                axios.get(`http://localhost:8080/jamon/listarJamones` )
                    .then((response) => {
                        console.log('Raw response:', response.data);
                        
                        this.getAllResponse = response.data;

                        this.getData = JSON.parse(response.data.data);
                        console.log(this.getData);
                        
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            borrarJamon() {
                axios.delete(`http://localhost:8080/jamon/borrarJamon?jamonId=${this.deleteId}` )
                    .then((response) => {
                        console.log(response.data)
                        console.log(this.deleteId.value)
                        this.deleteResponse = response.data
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            registrarJamon() {
                const config = {
                    headers: {
                    'Content-Type': 'application/json',
                    }
                };

                axios.post('http://localhost:8080/jamon/registrarJamon', this.formData, config)
                    .then(response => {
                     console.log(response.data);
                     this.creationResponse=response.data;
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            transferenciaJamon() {
                const config = {
                    headers: {
                    'Content-Type': 'application/json',
                    }
                };

                axios.put('http://localhost:8080/jamon/transferenciaJamon', this.transferData, config)
                    .then(response => {
                        this.transferResponse = response.data
                        console.log(response.data);
                    })
                    .catch(error => {
                        console.error(error);
                    });
            }
        }
    }
</script>
<style scoped>
  div {
    margin-bottom: 20px;
  }

  form {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
  }

  label {
    margin-bottom: 10px;
    font-weight: bold;
    display: block;
  }

  input {
    padding: 8px;
    margin-bottom: 10px;
  }

  button {
    padding: 10px;
    background-color: #4caf50;
    color: white;
    border: none;
    cursor: pointer;
  }

  h3 {
    color: #333;
    margin-bottom: 5px;
  }

  p {
    margin-bottom: 10px;
  }

  hr {
    border: 1px solid #ccc;
    margin-bottom: 20px;
  }
</style>
