<template>
    <v-sheet class="form-sheet" rounded>

    <img alt="logo" class="logo" src="../assets/logo.svg">
  <h1>Crypto Tracker</h1>
      <v-card class="form-card" outlined color="transparent">
        <v-form class="form" v-model="form" @submit.prevent="HandleSubmit">
         <v-text-field
             v-model="username"
             type="text"
             label="Enter username"
             variant="solo-filled"
             required
         ></v-text-field>
         <v-text-field
             v-model="email"
             type="email"
             label="Enter email"
             variant="solo-filled"
             required
         ></v-text-field>
         <v-text-field
             v-model="password"
             type="password"
             label="Enter password"
             variant="solo-filled"
             required
         ></v-text-field>
         <v-text-field
             v-model="passwordConfirm"
             type="password"
             label="Confirm password"
             variant="solo-filled"
             required
         ></v-text-field>
         <p v-if="errorMessage" style="color: red">{{errorMessage}}</p>
         <v-btn
             :disabled="!form"
             :loading="loading"
             type="submit">
           Sign Up
         </v-btn>
        </v-form>
    </v-card>
  </v-sheet>
</template>

<script setup lang="ts">
import axios from "axios"
import { ref } from 'vue'

const username = ref<string>("")
const loading = ref<boolean>(false)
const form = ref<boolean>(false)
const email = ref<string>("")
const password = ref<string>("")
const passwordConfirm = ref<string>("")
const errorMessage = ref<string>("")
function validateForm() {
      if (password.value !== passwordConfirm.value) {
        errorMessage.value = "Passwords do not match";
        passwordConfirm.value = "";
        password.value = "";
        return false;
      }
      errorMessage.value = "";
      return true
    }
async function HandleSubmit(){
      let isValid = validateForm()
      if (!isValid){
        return false
      }
      loading.value = true
      try {
        let response = await axios.post(
            "user/register/",
            {
              email: email.value,
              username: username.value,
              password: password.value
            }
        );
        if (response.status==200){
          localStorage.setItem("bearer-token", response.data["token"])
          //TODO change with general link
          window.location.replace("http://localhost:5173");
        }
      } catch (e) {
        errorMessage.value = e.response.data
        loading.value = false
      }
    }
</script>