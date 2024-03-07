import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { findIndex } from 'lodash'
import { LOADING_STATUS } from '../lib/constatnts'
import { Appointment, User } from '../types'
import { RootState } from './store'
import { axiosInstance, getJWTHeader } from '../lib/axiosInstance'

type AppointmentsState = {
  data: Appointment[]
  loadingStatus: string
  userData: Appointment[]
}

const initialState = {
  data: [],
  loadingStatus: 'idle',
  userData: [],
} as AppointmentsState

export const loadAppointments = createAsyncThunk<
  Appointment[],
  {
    year: string
    month: string
  }
>('appointments/load', async ({ year, month }, { rejectWithValue }) => {
  try {
    const resp = await axiosInstance.post(`appointments/`, { year, month })
    return resp.data
  } catch (e) {
    return rejectWithValue(e)
  }
})

export const loadUserAppointments = createAsyncThunk<Appointment[], { user: User; token: string }>(
  'appointments/loadByUser',
  async ({ user, token }, { rejectWithValue }) => {
    try {
      const resp = await axiosInstance.get(`/user/account/${user.id}/appointments`, {
        headers: getJWTHeader(token),
      })
      return resp.data
    } catch (e) {
      return rejectWithValue(e)
    }
  },
)
export const updateUserAppointment = createAsyncThunk<
  Appointment,
  { appointment: Appointment; token: string }
>('appointments/reserve', async ({ appointment, token }, { rejectWithValue }) => {
  try {
    const resp = await axiosInstance.patch(
      `/user/account/appointments/${appointment.id}`,
      {
        userId: appointment.userId || null,
      },
      {
        headers: getJWTHeader(token),
      },
    )
    return resp.data
  } catch (e) {
    return rejectWithValue(e)
  }
})
export const cancelUserAppointment = createAsyncThunk<
  void,
  { appointment: Appointment; token: string }
>('appointments/cancel', async ({ appointment, token }, { rejectWithValue }) => {
  try {
    const resp = await axiosInstance.get(`/user/account/appointments/${appointment.id}/cancel`, {
      headers: getJWTHeader(token),
    })
    return resp.data
  } catch (e) {
    return rejectWithValue(e)
  }
})

export const isLoading = (state: RootState) =>
  state.appointments.loadingStatus === LOADING_STATUS.LOADING
export const userAppointments = (state: RootState) => state.appointments.userData
export const monthAppointments = (state: RootState) => state.appointments.data

const appointmentsSlice = createSlice({
  name: 'appointments',
  initialState,
  reducers: {
    updateUserAppointments(state, action) {
      state.userData = [...state.userData].filter((item: Appointment) => item.id !== action.payload)
    },
    updateAppointments(state, action) {
      const updatedAppointment = action.payload
      const index = findIndex(state.data, { id: updatedAppointment.id })
      state.data[index] = updatedAppointment
    },
  },
  extraReducers: (builder) => {
    builder.addCase(loadAppointments.pending, (state) => {
      state.loadingStatus = LOADING_STATUS.LOADING
    })
    builder.addCase(loadAppointments.fulfilled, (state, action) => {
      state.data = action.payload
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })
    // load by user
    builder.addCase(loadUserAppointments.pending, (state) => {
      state.loadingStatus = LOADING_STATUS.LOADING
    })
    builder.addCase(loadUserAppointments.fulfilled, (state, action) => {
      state.userData = action.payload
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })
    // update
    builder.addCase(updateUserAppointment.pending, (state) => {
      state.loadingStatus = LOADING_STATUS.LOADING
    })
    builder.addCase(updateUserAppointment.fulfilled, (state) => {
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })
    // cancel
    builder.addCase(cancelUserAppointment.pending, (state) => {
      state.loadingStatus = LOADING_STATUS.LOADING
    })
    builder.addCase(cancelUserAppointment.fulfilled, (state) => {
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })
  },
})

export const { updateAppointments, updateUserAppointments } = appointmentsSlice.actions
export default appointmentsSlice.reducer
