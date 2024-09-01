import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import axios from "axios";
import router from "./routers";
import vuetify from "./plugins/vuetify";

axios.defaults.baseURL = "http://localhost:8000/api/"
createApp(App).use(vuetify).use(router).mount('#app')
