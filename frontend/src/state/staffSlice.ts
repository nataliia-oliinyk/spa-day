import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { LOADING_STATUS } from '../lib/constatnts'
import { Staff } from '../types'
import { RootState } from './store'
import { axiosInstance } from '../lib/axiosInstance'

type StaffState = {
  data: Staff[]
  loadingStatus: string
}

const initialState: StaffState = {
  data: [],
  loadingStatus: 'idle',
}

export const loadStaff = createAsyncThunk('staff/load', async () => {
  const resp = await axiosInstance.get(`employees/`)
  return resp.data
})

export const isLoading = (state: RootState) =>
  state.staff.loadingStatus !== LOADING_STATUS.SUCCEEDED
export const allStaff = (state: RootState) => state.staff.data

const staffSlice = createSlice({
  name: 'staff',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(loadStaff.pending, (state) => {
      state.loadingStatus = LOADING_STATUS.LOADING
    })
    builder.addCase(loadStaff.fulfilled, (state, action) => {
      state.data = action.payload
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })
  },
})

export const loadActions = staffSlice.actions
export default staffSlice.reducer
