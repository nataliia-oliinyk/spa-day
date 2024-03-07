import { configureStore } from '@reduxjs/toolkit'
import treatmentsReducer from './treatmentsSlice'
import staffReducer from './staffSlice'
import appointmentsReducer from './appointmentsSlice'
import userReducer from './userSlice'

export const store = configureStore({
  reducer: {
    treatments: treatmentsReducer,
    staff: staffReducer,
    appointments: appointmentsReducer,
    user: userReducer,
  },
})
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
