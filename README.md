# Proyecto de Votación con Hyperledger Fabric

Este proyecto implementa un sistema de votación utilizando Hyperledger Fabric. A continuación, se describen los pasos necesarios para configurar el entorno, desplegar la red blockchain y ejecutar el chaincode.

## Pre-requisitos

Asegúrate de tener instalados los siguientes recursos en tu sistema:

- Curl
- Git
- Docker y Docker Compose
- Npm
- JQ
- Java (OpenJDK 11)
- Hyperledger Fabric Samples

## Instalación y Configuración

### Instalar Recursos

**Curl**:
```bash
sudo apt install curl
```

**Git**:
```bash
sudo apt install git
```

**Docker Compose**:
```bash
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

**Docker**:
```bash
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo groupadd docker
sudo usermod -aG docker $USER
```

**Npm**:
```bash
sudo apt install npm
```

**JQ**:
```bash
sudo apt-get install jq
```

**Java**:
```bash
sudo apt-get install openjdk-11-jdk
```

**Fabric samples**:
```bash
curl -sSL https://bit.ly/2ysbOFE | bash -s
```
### Configuración de la Red y Chaincode

**Configurar el Chaincode**:  

En la carpeta del chaincode, ejecuta los siguientes comandos:
```bash
chmod +x gradlew
./gradlew installDist
```

**Iniciar y Crear el Canal de Fabric**:  

En la carpeta fabric-samples/test-network, ejecuta los siguientes comandos:
```bash
./network.sh down
sudo ./network.sh up createChannel -ca -s couchdb
```

Para agregar una tercera organización, ejecuta los siguientes comandos:
```bash
cd addOrg3
sudo ./addOrg3.sh up -c channel1 -ca -s couchdb
```

**Establecer Variables de Entorno**:  

Establece las siguientes variables de entorno en la carpeta fabric-samples/test-network:
```bash
export PATH=${PWD}/../bin:${PWD}:$PATH
export FABRIC_CFG_PATH=$PWD/../config/
export ORDERER_CA=${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
export CORE_PEER_TLS_ROOTCERT_FILE_ORG1=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_TLS_ROOTCERT_FILE_ORG2=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
export CORE_PEER_TLS_ENABLED=true
export CORE_PEER_LOCALMSPID="Org1MSP"
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
export CORE_PEER_ADDRESS=localhost:7051
```

**Desplegar el Chaincode**:  

En la carpeta fabric-samples/test-network, ejecuta el siguiente comando:
```bash
./network.sh deployCC -ccn chaincode -ccp ./app/chaincode/ -ccl java
```

### Iniciar la Base de Datos y Monitoreo

**Iniciar la Base de Datos**:  

En la terminal, ejecuta los siguientes comandos:
```bash
sudo service docker start
make start-db
```

**Iniciar Prometheus y Grafana** (para monitoreo):  

En la terminal, ejecuta los siguientes comandos:
```bash
cd prometheus-grafana
sudo docker-compose up -d
```

Accede a Prometheus y Grafana en los siguientes puertos:
- Prometheus: http://34.175.29.63:9090
- Grafana: http://34.175.29.63:3000




















