
<template>
  <NavBar></NavBar>
  <div class="component-with-nav-bar">
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
        :title="row.item.name"
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
const page = ref<number>(1)
const loading = ref<boolean>(false)
const successAlert = ref<boolean>(false)
const failAlert = ref<boolean>(false)
const failMessage = ref<string>("")
const itemsPerPage = ref<number>(10)
const totalItems = ref<number>(0)
const transactionAmount = ref<number>()
const numPages = ref<number>(-1)
const dialog = ref<boolean>(false)
const search = ref<string>("")
const row = ref<any>({})
const token = ref<string|null>(null)
const titles = ref<{key:string, title:string, align:string}[]>(
    [
      {
        key: "name",
        title: "Name",
        align: "start"
      },
      {
        key: "value",
        title: "Price",
        align: "end"
      },
      {
        key: "market_cap",
        title: "Market capital",
        align: "end"
      },
  ]
)
onMounted(()=> {
  token.value = localStorage.getItem("bearer-token")
})
async function loadItems(){

    if (numPages.value===page.value - 1 || loading.value){
      return
    }
    loading.value = true
    while(numPages.value!==page.value-1){
      await apiClient.get(
          "/crypto-info/", {withCredentials: false,params: {
              "page": page.value
          }
          }
      ).then(response => {
            items.value = items.value.concat(response.data.crypto_info)
            page.value = response.data.page
            totalItems.value += response.data.items_count
            numPages.value = response.data.num_pages
            page.value++
          }
      )
      if(numPages.value <= 1) {break}
  }
  loading.value = false
}
function handleClick(_, rowTemp){
  dialog.value = true
  row.value = rowTemp
}

async function transaction(type: string){
    await apiClient.post(
      "/portfolio/transaction/", {
          "cryptocurrency": row.value.item.name,
          "amount": Number(transactionAmount.value),
          "transaction_type": type
    }
    ).then( response =>{
      dialog.value=false
      successAlert.value=true
      setTimeout(() => {
       successAlert.value=false
      }, 2000)
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

