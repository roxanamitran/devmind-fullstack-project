import axios from "axios";
const apiClient = axios.create({
  baseURL: "http://localhost:8080",
  "Content-Type": "application/json",
  timeout: 10000
});

export default apiClient;
