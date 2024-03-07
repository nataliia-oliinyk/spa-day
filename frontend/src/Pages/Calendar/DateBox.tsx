import { Box, Stack, Text } from '@chakra-ui/react'
import { Appointment } from '../../types'
import { AppointmentCard } from './AppointmentCard'

type DateBoxProps = {
  date: number
  gridColumn?: number
  appointments?: Appointment[]
}

export function DateBox({ date, gridColumn, appointments = [] }: DateBoxProps) {
  return (
    <Box w="100%" h={40} bg="olive.50" gridColumnStart={gridColumn} boxShadow="md" rounded="md">
      <Stack m={2} spacing={1}>
        <Text fontSize="xs" textAlign="right">
          {date}
        </Text>
        {appointments.map((appointment: Appointment) => (
          <AppointmentCard appointment={appointment} key={appointment.id} />
        ))}
      </Stack>
    </Box>
  )
}
