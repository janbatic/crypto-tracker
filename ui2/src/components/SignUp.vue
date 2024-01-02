<template>
  <img class="logo" src="../assets/logo.svg">
  <h1>Crypto Tracker</h1>
  <div class="signup">
     <form @submit.prevent="HandleSubmit">

      <input type="text" v-model="username" placeholder="Enter username" required>
      <input type="text" v-model="email" placeholder="Enter email" required>
      <input type="password" v-model="password" placeholder="Enter password" required>
      <input type="password" v-model="passwordConfirm"  placeholder="Confirm password" required>
      <p v-if="errorMessage" style="color: red">{{errorMessage}}</p>
      <button type="submit">Sign Up</button>
     </form>
  </div>
</template>

<script>
import axios from "axios";
export default {

  name: 'SignUp',
  data() {
    return {
      username: "",
      email: "",
      password: "",
      passwordConfirm: "",
    }
  },
  methods: {
    validateForm() {
      if (this.password !== this.passwordConfirm) {
        this.errorMessage = "Passwords do not match";
        this.passwordConfirm = "";
        return false;
      }
      this.errorMessage = "";
      return true
    },
    async HandleSubmit() {
      let isValid = this.validateForm()
      if (!isValid){
        return false
      }
      try {
        let response = await axios.post(
            "user/register/",
            {
              email: this.email,
              username: this.username,
              password: this.password
            }
        );
        if (response.status==200){
          localStorage.setItem("bearer-token", response.data["token"])
        }
      } catch (e) {
        this.errorMessage = e.response.data
        console.log(this.errorMessage)
        this.passwordConfirm = "";
      }
    },
  }
}

</script>
<style src="../assets/styles.css">
</style>