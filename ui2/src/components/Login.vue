<template>
  <img class="logo" src="../assets/logo.svg">
  <h1>Crypto Tracker</h1>
  <div class="form">
     <form @submit.prevent="HandleSubmit">

      <input type="text" v-model="username" placeholder="Enter username" required>
      <input type="password" v-model="password" placeholder="Enter password" required>
      <p v-if="errorMessage" style="color: red">{{errorMessage}}</p>
      <button type="submit">Login</button>
     </form>
  </div>
</template>

<script>
import axios from "axios";
export default {

  name: 'LoginComponent',
  data() {
    return {
      username: "",
      password: "",
    }
  },
  methods: {
    async HandleSubmit() {
      try {
        let response = await axios.post(
            "user/login/",
            {
              username: this.username,
              password: this.password
            }
        );
        if (response.status==200){
          console.log(response)
          localStorage.setItem("bearer-token", response.data["token"]);
          this.$router.push({name: "Home"});
        }
      } catch (e) {
        console.log(e)
        this.errorMessage = e.response.data
        console.log(this.errorMessage)
        this.password = "";
      }
    },
  }
}

</script>
<style src="../assets/styles.css">
</style>