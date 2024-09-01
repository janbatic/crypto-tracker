<template>
    <v-sheet class="form-sheet" rounded>

    <img alt="logo" class="logo" src="../assets/logo.svg">
  <h1>Crypto Tracker</h1>
      <v-card class="form-card elevation-3" outlined color="transparent">
        <v-form class="form" v-model="form" @submit.prevent="HandleSubmit">
         <v-text-field
             v-model="username"
             type="text"
             label="Enter username"
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
         <p v-if="errorMessage" style="color: red">{{errorMessage}}</p>
         <v-btn
             :disabled="!form"
             :loading="loading"
             type="submit">
           Sign in
         </v-btn>
        </v-form>
        <p class="or-p">
          ----------------------- or -----------------------
        </p>
        <v-btn
           class="sign-up-button"
           :loading="loading"
           @click="signUp">
         Sign Up
        </v-btn>
    </v-card>
  </v-sheet>
</template>

<script setup lang="ts">
import axios from "axios"
import { ref } from 'vue'

const username = ref<string>("")
const loading = ref<boolean>(false)
const form = ref<boolean>(false)
const password = ref<string>("")
const errorMessage = ref<string>("")

async function HandleSubmit(){

      loading.value = true
      try {
        let response = await axios.post(
            "user/login/",
            {
              username: username.value,
              password: password.value
            }
        );
        console.log(response)
        if (response.status==200){
          localStorage.setItem("bearer-token", response.data["token"])
          window.location.replace("/");
        }
      } catch (e) {
        errorMessage.value = e.response.data
        loading.value = false
        console.log(errorMessage.value)
      }
    }
async function signUp(){
  window.location.replace("/sign-up");
}

</script>

<style>

</style>
