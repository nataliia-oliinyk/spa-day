import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { LOADING_STATUS } from '../lib/constatnts'
import { Treatment } from '../types'
import { RootState } from './store'
import { axiosInstance } from '../lib/axiosInstance'

type TreatmentsState = {
  data: Treatment[]
  loadingStatus: string
}

const initialState = {
  data: [],
  loadingStatus: 'idle',
} as TreatmentsState

export const loadTreatments = createAsyncThunk('treatments/load', async () => {
  const resp = await axiosInstance.get(`treatments/`)
  return resp.data
})

export const getAllTreatments = (state: RootState) => state.treatments.data
export const isLoading = (state: RootState) =>
  state.treatments.loadingStatus === LOADING_STATUS.LOADING

const treatmentsSlice = createSlice({
  name: 'treatments',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(loadTreatments.pending, (state) => {
      state.loadingStatus = LOADING_STATUS.LOADING
    })
    builder.addCase(loadTreatments.fulfilled, (state, action) => {
      state.data = action.payload
      state.loadingStatus = LOADING_STATUS.SUCCEEDED
    })
  },
})

export const treatmentsActions = treatmentsSlice.actions
export default treatmentsSlice.reducer
