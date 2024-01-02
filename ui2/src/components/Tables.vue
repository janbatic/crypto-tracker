
<script setup>

import {useVueTable, FlexRender, getCoreRowModel} from "@tanstack/vue-table"
import axios from "axios";
import {ref} from "vue";


const columnHeaders = ref([
  {
    accessKey: "name",
    header: "Name",
  },
  {
    accessKey: "value",
    header: "Price",
  },
  {
    accessKey: "market_cap",
    header: "Market capital",
  },
])
let data = await axios.get(
    "http://127.0.0.1:8000/api/crypto-info/"
)
data = ref(data.data["crypto_info"])
console.log(data.value)
// const data = ref(this.coins)
const tanstackTable = useVueTable({
  data: data.value,
  columns: columnHeaders,
  getCoreRowModel: getCoreRowModel()
})
export default {
  name: "TableComponent",
  computed: {
    table() {
      return tanstackTable
    }
  },
  components: {FlexRender},
}

</script>

<template>
  <div>
    <table>
    <!-- Table headers -->
      <thead>
        <tr v-for="headerGroup in table.getHeaderGroups()" :key="headerGroup.id">
          <th v-for="header in headerGroup.headers" :key="header.id">
            <FlexRender :render="header.column.columnDef.header" :props="header.getContext()"/>
          </th>
        </tr>
      </thead>

      <!-- Table rows -->
      <tbody>
        <tr v-for="row in table.getRowModel().rows" :key="row.id">
          <td v-for="cell in row.getVisibleCells()" :key="cell.id">
            <FlexRender
                :render="cell.column.columnDef.cell"
                :props="cell.getContext()"
            />
          </td>

        </tr>
      </tbody>
    </table>

  </div>

</template>