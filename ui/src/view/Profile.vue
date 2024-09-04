
<template>
  <NavBar></NavBar>
  <div class="component-with-nav-bar">
    <p
        class="portfolio-value"
    >{{ currentPortfolioValue }} USD</p>
    <v-text-field
      v-model="search"
      label="Search"
      prepend-inner-icon="mdi-magnify"
      variant="outlined"
      hide-details
      single-line
      class="search-field"
    ></v-text-field>
  <v-data-table
      checkbox-color="#DCDCDC"
      :hover="true"
      :headers="titles"
      :items="items"
      :loading="loading"
      :items-per-page="itemsPerPage"
      height="580"
      :page="1"
      :search="search"
      :server-items-length="totalItems"
      :items-per-page-options="[10, 25, 50, 100]"
      fixed-header
      @update:options="loadItems"
       @click:row="handleClick"
  >
  </v-data-table>
  <v-dialog
      v-model="dialog"
      width="auto"
    >
      <v-card
        class=""
        :title="row.item.cryptocurrency"
      >
        <v-form class="form">
         <v-text-field
             v-model="transactionAmount"
             type="number"
             label="Amount"
             variant="solo-filled"
             required

         ></v-text-field>

          <v-btn
             :disabled="!token"
             :loading="loading"
             class="buy-btn"
             @click="transaction('buy')"
          >
           Buy
         </v-btn>
          <v-btn
             :disabled="!token"
             :loading="loading"
             class="sell-btn"
             @click="transaction('sell')"
          >
           Sell
         </v-btn>
        </v-form>
      </v-card>
    </v-dialog>
    <v-overlay
      :model-value="successAlert || failAlert"
      class="alert"
      z-index="10"
      :scrim="false"
    >
    <v-alert
      v-if="successAlert"
      icon="$success"
      transition="fade-transition"
      text="Successfully updated portfolio!"
      title="Sucess"
      type="success"
    ></v-alert>
    <v-alert
      v-else-if="failAlert"
      icon="$error"
      transition="fade-transition"
      :text="failMessage"
      title="Failed"
      type="error"
    ></v-alert>
    </v-overlay>
  </div>


</template>

<script setup lang="ts">
import NavBar from "../components/NavBar.vue"
import {onMounted, ref} from "vue";
import apiClient from "../http-common.ts";
const items= ref<{name:string, value:string, market_cap:string}[]>([])
const currentPortfolioValue= ref<number>(0)
const loading = ref<boolean>(false)
const successAlert = ref<boolean>(false)
const failAlert = ref<boolean>(false)
const failMessage = ref<string>("")
const itemsPerPage = ref<number>(10)
const totalItems = ref<number>(0)
const transactionAmount = ref<number>()
const dialog = ref<boolean>(false)
const search = ref<string>("")
const row = ref<any>({})
const token = ref<string|null>(null)
const titles = ref<{key:string, title:string, align:string}[]>(
    [
      {
        key: "cryptocurrency",
        title: "Name",
        align: "start"
      },
      {
        key: "amount",
        title: "Amount",
        align: "end"
      },
  ]
)
onMounted(()=> {
  token.value = localStorage.getItem("bearer-token")
  if (!token.value){
    window.location.replace("/");
  }
})
async function loadItems(){
    loading.value = true
      await apiClient.get(
          "/portfolio/possession"

      ).then(response => {
            items.value = response.data.cryptocurrencies
            totalItems.value += items.value.length
            currentPortfolioValue.value = response.data.current_portfolio_value

          }
      )
  loading.value = false
}
function handleClick(_, rowTemp){
  dialog.value = true
  row.value = rowTemp
}

async function transaction(type: string){
    await apiClient.post(
      "/portfolio/transaction/", {
          "cryptocurrency": row.value.item.cryptocurrency,
          "amount": Number(transactionAmount.value),
          "transaction_type": type
    }
    ).then( response =>{
      dialog.value=false
      successAlert.value=true
      setTimeout(() => {
       successAlert.value=false
      }, 2000)
      loadItems()
    }).catch( error =>{
      console.log(error)
      dialog.value=false
      failAlert.value = true
      failMessage.value = error.response.data
      setTimeout(() => {
       failAlert.value=false
      }, 2000);
      }
    )
}

</script>

<style scoped>
.v-data-table{
  width: 80%;
  margin: auto;
}

</style>

