import axios, { AxiosRequestConfig } from 'axios'
import { BASE_URL } from './constatnts'

const config: AxiosRequestConfig = {
  baseURL: BASE_URL,
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Content-type': 'application/json; charset=UTF-8',
  },
}
export const axiosInstance = axios.create(config)

export function getJWTHeader(token: string): Record<string, string> {
  return { Authorization: `Bearer ${token}` }
}
