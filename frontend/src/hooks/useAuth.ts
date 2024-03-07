import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from './index'
import {
  loggedInUser,
  loginUser,
  isUserLoggedIn,
  updateToken,
  signUpUser,
  authToken,
  updateUser,
  loadUserData,
} from '../state/userSlice'
import { AUTH_COOKIE_MAX_AGE, AUTH_COOKIE_NAME } from '../lib/constatnts'
import useCustomToast from './useCustomToast'
import { ErrorPayloadData } from '../types'
import useCookie from './useCookie'

const useAuth = () => {
  const dispatch = useAppDispatch()
  const toast = useCustomToast()
  const { getCookieValue, updateCookie, deleteCookie } = useCookie(AUTH_COOKIE_NAME)

  const token = useAppSelector(authToken)
  const isLoggedIn = useAppSelector(isUserLoggedIn)
  const user = useAppSelector(loggedInUser)

  const [isUserLoading, setIsUserLoading] = useState(true)

  useEffect(() => {
    if (!!token && !user) {
      dispatch(loadUserData())
    }
    if (getCookieValue() && !token) {
      dispatch(updateToken(getCookieValue()))
    }
    setIsUserLoading(false)
  }, [user, token, setIsUserLoading])
  const login = async (email: string, password: string) => {
    try {
      const { token } = await dispatch(loginUser({ email, password })).unwrap()
      await updateCookie(token, { expires: AUTH_COOKIE_MAX_AGE, secure: true })
    } catch (err) {
      toast({
        title: 'Invalid username or password',
        status: 'error',
        position: 'top',
      })
    }
  }
  const logout = async () => {
    setIsUserLoading(true)
    await deleteCookie()
    dispatch(updateToken(null))
    dispatch(updateUser(null))
  }
  const signup = async (email: string, password: string, name: string) => {
    try {
      const { token } = await dispatch(signUpUser({ email, name, password })).unwrap()
      await updateCookie(token, { expires: AUTH_COOKIE_MAX_AGE, secure: true })
      toast({
        title: 'Your account was created.',
        status: 'success',
        position: 'top',
      })
    } catch (err) {
      const errorsData = err as ErrorPayloadData
      if (errorsData.data && errorsData.data.errors) {
        const { errors } = errorsData.data
        errors.map((errorText: string) => {
          toast({
            title: errorText,
            status: 'error',
            position: 'top',
          })
        })
      }
    }
  }

  return { user, isLoggedIn, login, logout, signup, token, isUserLoading }
}
export default useAuth
