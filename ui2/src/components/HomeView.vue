<template>
  <h1>HJOME VIEWWW</h1>
  <template>
    <v-data-table-server
      v-model:items-per-page="itemsPerPage"
      :titles="titles"
      :items-length="50"
      :items="serverItems"
      :loading="loading"
      :search="search"
      item-value="name"
      @update:options="loadItems"
    ></v-data-table-server>
  </template>
</template>

<script lang="ts">
import {ref} from "vue";
const itemsPerPage = ref(10)
const items = ref([])
const totalItems = ref(0)
const loading = ref(false)
const titles = ref(
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


</script>