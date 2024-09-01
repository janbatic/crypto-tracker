import axios, { AxiosInstance } from "axios";

const apiClient: AxiosInstance = axios.create({
  baseURL: "http://localhost:8000/api",
  headers: {
    "Content-type": "application/json",
    "Authorization": localStorage.getItem("bearer-token")
  },

});

export default apiClient;