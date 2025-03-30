<template>
    <div id="app">
      <h1>Busca de Operadoras</h1>
      <input 
        v-model="consultaBusca" 
        @input="buscar" 
        placeholder="Digite para buscar operadoras" 
        class="search-input"
      >
      <ul class="results-list">
        <li v-for="operadora in operadoras" :key="operadora.registro_ans" class="result-item">
          {{ operadora.razao_social }} ({{ operadora.nome_fantasia }})
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'App',
    data() {
      return {
        consultaBusca: '',
        operadoras: []
      }
    },
    methods: {
      async buscar() {
        try {
          const resposta = await axios.get('http://localhost:5000/busca', {
            params: { q: this.consultaBusca }
          });
          this.operadoras = resposta.data;
        } catch (error) {
          console.error('Erro ao buscar operadoras:', error);
        }
      }
    }
  }
  </script>
  
  <style>
  #app {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    font-family: Arial, sans-serif;
  }
  
  .search-input {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .results-list {
    list-style: none;
    padding: 0;
  }
  
  .result-item {
    padding: 10px;
    border-bottom: 1px solid #eee;
  }
  </style>