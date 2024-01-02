import { createApp } from 'vue';
import App from './App.vue';
import router from "./routers";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import axios from "axios";

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

createVuetify({
  components,
  directives,
})

axios.defaults.baseURL = "http://127.0.0.1:8000/api/"
createApp(App).use(router).mount('#app')

import "bootstrap/dist/js/bootstrap.js"
