<template>
    <div>
    <div>
        <form @submit.prevent="createAsset">
            <div>
                <label for="appraisedValue">Appraised Value</label>
                <input type="number" id="appraisedValue" v-model="formData.appraisedValue">
            </div>
            <div>
                <label for="assetID">Asset ID</label>
                <input type="number" id="assetID" v-model="formData.assetID">
            </div>
            <div>
                <label for="color">Color</label>
                <input type="text" id="color" v-model="formData.color">
            </div>
            <div>
                <label for="owner">Owner</label>
                <input type="text" id="owner" v-model="formData.owner">
            </div>
            <div>
                <label for="size">Size</label>
                <input type="number" id="size" v-model="formData.size">
            </div>
            <button>Create Asset</button>
        </form>
            <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ creationResponse.code }}</h3>
                <p> {{ creationResponse.data }}</p>
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="assetExists">
            <div>
                <label for="assetId">Asset Id</label>
                <input type="number" id="assetId" v-model="assetId">
            </div>
            <button>Asset Exist</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ existResponse.code }}</h3>
                <p> {{ existResponse.data }}</p>
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="getAsset">
            <div>
                <label for="asset_id">Asset Id</label>
                <input type="number" id="asset_id" v-model="asset_id">
            </div>
            <button>Get Asset</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ getResponse.code }}</h3>
                <p> {{ getResponse.data }}</p>
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="getAllAssets">
            <button>Get All Assets</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ getAllResponse.code }}</h3>
                <ul>
                    <li v-for="asset in getData" :key="asset">
                        {{ asset.assetID }} - {{ asset.appraisedValue }} - {{ asset.color }} - {{ asset.owner }} - {{ asset.size }}
                    </li>
                </ul>
                
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="deleteAsset">
            <div>
                <label for="deleteId">Asset Id</label>
                <input type="number" id="deleteId" v-model="deleteId">
            </div>
            <button>Delete Asset</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ deleteResponse.code }}</h3>
                <p> {{ deleteResponse.data }}</p>
                <hr />
            </div>
    </div>

    <div>
        <form @submit.prevent="transferAsset">
            <div>
                <label for="transferAssetId">Asset ID</label>
                <input type="number" id="transferAssetId" v-model="transferData.assetID">
            </div>
            <div>
                <label for="newOwner">New Owner</label>
                <input type="text" id="newOwner" v-model="transferData.newOwner">
            </div>

            <button>Transfer Asset</button>
        </form>
        <h3 v-if="errorMsg">{{ errorMsg }}</h3>
            <div v-else>
                <h3>{{ transferResponse.code }}</h3>
                <p> {{ transferResponse.data }}</p>
                <hr />
            </div>
    </div>
    <div>
        <form @submit.prevent="updateAsset">
            <div>
                <label for="appraisedValueUpdated">Appraised Value</label>
                <input type="number" id="appraisedValueUpdated" v-model="updateData.appraisedValue">
            </div>
            <div>
                <label for="assetIDUpdated">Asset ID</label>
                <input type="number" id="assetIDUpdated" v-model="updateData.assetID">
            </div>
            <div>
                <label for="colorUpdated">Color</label>
                <input type="text" id="colorUpdated" v-model="updateData.color">
            </div>
            <div>
                <label for="ownerUpdated">Owner</label>
                <input type="text" id="ownerUpdated" v-model="updateData.owner">
            </div>
            <div>
                <label for="sizeUpdated">Size</label>
                <input type="number" id="sizeUpdated" v-model="updateData.size">
            </div>
            <button>Update Asset</button>
        </form>
                    <h3 v-if="errorMsg">{{ errorMsg }}</h3>
                    <div v-else>
                <h3>{{ updateResponse.code }}</h3>
                <p> {{ updateResponse.data }}</p>
                <hr />
            </div>
    </div>
        

    </div>
</template>

<script>
import axios from 'axios'
    export default {
        name: 'BasicComponent',
        data() {
            return {
                posts: [],
                creationResponse: [],
                getAllResponse: [],
                getData: [],
                existResponse: [],
                getResponse: [],
                deleteResponse: [],
                transferResponse: [],
                updateResponse: [],
                errorMsg: '',
                formData :{
                    appraisedValue: 0,
                    assetID: 0,
                    color: '',
                    owner: '',
                    size: 0
                },
                updateData :{
                    appraisedValue: 0,
                    assetID: 0,
                    color: '',
                    owner: '',
                    size: 0
                },
                assetId:0,
                asset_id:0,
                deleteId:0,
                transferData :{
                    assetID: 0,
                    newOwner: ""
                }
            }
        },
        methods: {
            assetExists() {
                axios.get(`http://localhost:8080/basic/assetExists?assetId=${assetId.value}` )
                    .then((response) => {
                        console.log(response.data)
                        this.existResponse = response.data;
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            getAsset() {
                axios.get(`http://localhost:8080/basic/readAsset?assetId=${asset_id.value}` )
                    .then((response) => {
                        console.log(response.data)
                        this.getResponse=response.data
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            getAllAssets() {
                axios.get(`http://localhost:8080/basic/getAllAssets` )
                    .then((response) => {
                        console.log('Raw response:', response.data);  // Imprimir la respuesta original
                        
                        this.getAllResponse = response.data;

                        this.getData = JSON.parse(response.data.data);
                        console.log(this.getData);
                        
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            deleteAsset() {
                axios.delete(`http://localhost:8080/basic/deleteAsset?assetId=${deleteId.value}` )
                    .then((response) => {
                        console.log(response.data)
                        this.deleteResponse = response.data
                    })
                    .catch((error) => {
                        console.log(error)
                        this.errorMsg = 'Error retreiving data'
                    })

            },
            createAsset() {
                const config = {
                    headers: {
                    'Content-Type': 'application/json',
                    }
                };

                axios.post('http://localhost:8080/basic/createAsset', this.formData, config)
                    .then(response => {
                     console.log(response.data);
                     this.creationResponse=response.data;
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            transferAsset() {
                const config = {
                    headers: {
                    'Content-Type': 'application/json',
                    }
                };

                axios.put('http://localhost:8080/basic/transferAsset', this.transferData, config)
                    .then(response => {
                        this.transferResponse = response.data
                     console.log(response.data);
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            updateAsset() {
                const config = {
                    headers: {
                    'Content-Type': 'application/json',
                    }
                };

                axios.put('http://localhost:8080/basic/updateAsset', this.updateData, config)
                    .then(response => {
                     console.log(response.data);
                     this.updateResponse = response.data
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
