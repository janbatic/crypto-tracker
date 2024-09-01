<template>
  <v-app-bar :elevation="5" class="app-bar-custom">
    <img alt="logo" class="logo-nav-bar" src="../assets/logo.svg">
    <v-app-bar-title>Crypto tracker</v-app-bar-title>
             <v-btn
          class="elevation-2 "
          @click="$router.push('/')"
        >
          <v-icon>mdi-home</v-icon>

        </v-btn>
         <v-btn
            v-if="token"
            class="elevation-2 "
            @click="$router.push('/profile')"
        >
          <v-icon>mdi-chart-arc</v-icon>

        </v-btn>
    <v-menu>
       <template v-slot:activator="{ props }">

        <v-btn
            v-if="token"
          v-bind="props"
          class="account-btn elevation-2 "
        >
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item>
          <v-btn
            variant="text"
            @click="logOut"
          >
            Log Out
          </v-btn>
        </v-list-item>
      </v-list>
    </v-menu>
    <v-btn
        v-if="!token"
        class="sign-in-btn elevation-2 "
        @click="$router.push('/login')"
        >
      Sign in
    </v-btn>
  </v-app-bar>

</template>
<script setup lang="ts">
import {onMounted, ref} from "vue";
const token = ref<string|null>(null)

onMounted(() => {
  token.value = localStorage.getItem("bearer-token")
})

async function logOut(){
  localStorage.removeItem("bearer-token")
  window.location.replace("/");
}

</script>

<style>
.v-app-bar-title{
  text-align: left;
}


</style>