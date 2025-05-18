import axios from 'axios'

const mockBaseURL = "/api"
const devBaseURL = "http://localhost:8080/api"
const prodBaseURL = "http://localhost:8081/api"

const request = axios.create({
  baseURL: devBaseURL,
  timeout: 10000,
  withCredentials: true
})

export default request