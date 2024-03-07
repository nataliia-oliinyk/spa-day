export type Id = {
  id: string
}

export type NewUser = {
  email: string
  name: string
  password?: string
  address?: string
  phone?: string
  token?: string
}

export type User = Id & NewUser

export type Appointment = {
  dateTime: Date
  treatmentName: string
  userId?: string
} & Id

export type Treatment = {
  name: string
  durationInMinutes: number
  image: string
  description: string
} & Id

export type Staff = {
  name: string
  treatments: string[]
  image: string
} & Id

export type ErrorPayloadData = {
  status: number | null
  data: { errors: [] } | null
}
