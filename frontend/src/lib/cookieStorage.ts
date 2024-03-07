import { AUTH_COOKIE_MAX_AGE } from './constatnts'

const expiredDate = (hours: number) => {
  const date = new Date()
  date.setTime(date.getTime() + hours * 1000)
  return date.toString()
}

export const setCookie = (key: string, value: string) => {
  document.cookie = `${key}=${value || ''}; expires=${expiredDate(AUTH_COOKIE_MAX_AGE)}; path=/`
}
export const getCookie = (key: string) => {
  const nameEQ = `${key}=`
  const ca = document.cookie.split(';')
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i]
    while (c.charAt(0) == ' ') c = c.substring(1, c.length)
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length)
  }
  return null
}
export const deleteCookie = (key: string) => {
  console.log(key)
  setCookie(key, null)
}
