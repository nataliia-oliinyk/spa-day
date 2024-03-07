import { useCallback } from 'react'
import Cookies, { CookieAttributes } from 'js-cookie'

const useCookie = (key: string) => {
  const getCookieValue = useCallback(() => {
    const cookie = Cookies.get(key)
    if (!cookie || cookie === 'null') return null
    return cookie
  }, [key])
  const updateCookie = useCallback(
    async (newValue: string, options?: CookieAttributes) => {
      Cookies.set(key, newValue, options)
    },
    [key],
  )
  const deleteCookie = useCallback(async () => {
    Cookies.remove(key)
  }, [key])
  return { getCookieValue, updateCookie, deleteCookie }
}
export default useCookie
