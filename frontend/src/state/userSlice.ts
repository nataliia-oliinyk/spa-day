import { createAsyncThunk, createSlice, isRejected } from '@reduxjs/toolkit'
import axios from 'axios'
import { AUTH_COOKIE_NAME, LOADING_STATUS } from '../lib/constatnts'
import { RootState } from './store'
import { axiosInstance } from '../lib/axiosInstance'
import { ErrorPayloadData, NewUser, User } from '../types'
import Cookies from 'js-cookie'

type UserState = {
  user: User | null
  token: string | null
  loadingStatus: string
}

const initialState = {
  user: null,
  token: null,
  loadingStatus: 'idle',
} as UserState

export const payloadError = (err: unknown): ErrorPayloadData => {
  const responseCode =
    axios.isAxiosError(err) && err?.response?.status ? err?.response?.status : null
  const responseData = axios.isAxiosError(err) && err?.response?.data ? err?.response?.data : null
  return { status: responseCode, data: responseData }
}

export const loginUser = createAsyncThunk<
  { token: string },
  {
    email: string
    password: string
  }
>('user/login', async (params, { rejectWithValue }) => {
  try {
    const resp = await axiosInstance.post(`user/auth/authenticate`, params)
    return resp.data
  } catch (err) {
    return rejectWithValue(payloadError(err))
  }
})

export const signUpUser = createAsyncThunk<{ token: string }, NewUser>(
  'user/signup',
  async (params, { rejectWithValue }) => {
    try {
      const resp = await axiosInstance.post(`user/auth/register`, params)
      return resp.data
    } catch (err) {
      return rejectWithValue(payloadError(err))
    }
  },
)

export const loadUserData = createAsyncThunk<User>(
  'user/loadUser',
  async (_, { getState, rejectWithValue }) => {
    try {
      const { user } = getState() as { user: UserState }
      const config = {
        headers: { Authorization: `Bearer ${user.token}` },
      }
      const resp = await axiosInstance.get(`user/account/`, config)
      return resp.data
    } catch (err) {
      return rejectWithValue(payloadError(err))
    }
  },
)

export const updateUserData = createAsyncThunk<{ token: string }, NewUser>(
  'user/updateData',
  async (params, { getState, rejectWithValue }) => {
    try {
      const { user } = getState() as { user: UserState }
      const config = {
        headers: { Authorization: `Bearer ${user.token}` },
      }
      const resp = await axiosInstance.post(`user/account/`, params, config)
      return resp.data
    } catch (err) {
      return rejectWithValue(payloadError(err))
    }
  },
)

export const isLoading = (state: RootState) => state.user.loadingStatus === LOADING_STATUS.LOADING
export const isUserLoggedIn = (state: RootState) => !!state.user.token
export const loggedInUser = (state: RootState) => state.user.user
export const authToken = (state: RootState) => state.user.token

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    updateToken(state, action) {
      state.token = action.payload
    },
    updateUser(state, action) {
      state.user = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.loadingStatus = LOADING_STATUS.LOADING
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loadingStatus = LOADING_STATUS.SUCCEEDED
        state.token = action.payload.token
      })

    builder
      .addCase(signUpUser.pending, (state) => {
        state.loadingStatus = LOADING_STATUS.LOADING
      })
      .addCase(signUpUser.fulfilled, (state, action) => {
        state.loadingStatus = LOADING_STATUS.SUCCEEDED
        state.token = action.payload.token
      })

    builder
      .addCase(updateUserData.pending, (state) => {
        state.loadingStatus = LOADING_STATUS.LOADING
      })
      .addCase(updateUserData.fulfilled, (state, action) => {
        state.loadingStatus = LOADING_STATUS.SUCCEEDED
        state.token = action.payload.token
      })

    builder.addCase(loadUserData.fulfilled, (state, action) => {
      state.user = action.payload
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })

    builder.addMatcher(isRejected, (state, error) => {
      state.loadingStatus = LOADING_STATUS.FAILED
      const errorData = error.payload as ErrorPayloadData
      if (errorData.status === 401) {
        Cookies.remove(AUTH_COOKIE_NAME)
        state.token = null
        state.user = null
      }
    })
  },
})

export const { updateToken, updateUser } = userSlice.actions
export default userSlice.reducer
