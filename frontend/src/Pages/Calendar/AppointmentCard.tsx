import { Box, HStack, Text } from '@chakra-ui/react'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import { Appointment } from '../../types'
import { useAppDispatch } from '../../hooks'
import { updateUserAppointment, updateAppointments } from '../../state/appointmentsSlice'
import useAuth from '../../hooks/useAuth'
import { dateInPast } from '../../lib/monthYear'
import useCustomToast from '../../hooks/useCustomToast'

type AppointmentProps = {
  appointment: Appointment
}

function getStyles(canBeResolved: boolean, resolvedByUser: boolean) {
  let bgColor = '#e2e2e2'
  let hoverCss = {}
  if (canBeResolved) {
    bgColor = 'white'
    hoverCss = {
      transform: 'translateY(-1px)',
      boxShadow: 'md',
      cursor: 'pointer',
    }
  } else if (resolvedByUser) {
    bgColor = '#ffe0e0'
  }
  return { bgColor, hoverCss }
}

export function AppointmentCard({ appointment }: AppointmentProps) {
  const dispatch = useAppDispatch()
  const { user, token } = useAuth()
  const toast = useCustomToast()
  const [clickable, setClickable] = useState(false)
  useEffect(() => {
    if (!dateInPast(appointment.dateTime) && !appointment.userId) {
      setClickable(true)
    } else {
      setClickable(false)
    }
  }, [user, appointment, setClickable])

  const handleClick = async () => {
    if (user && token && clickable) {
      const updatedAppointment = { ...appointment, userId: user.id }
      await dispatch(
        updateUserAppointment({
          appointment: updatedAppointment,
          token,
        }),
      )
      dispatch(updateAppointments(updatedAppointment))
      toast({
        title: 'You have reserved the appointment',
        status: 'success',
      })
    } else {
      toast({
        title: 'You need to be logged in to reserve the appointment',
        status: 'error',
      })
    }
  }

  const { bgColor, hoverCss } = getStyles(
    !appointment.userId && !dateInPast(appointment.dateTime),
    !!user && appointment.userId === user.id,
  )
  return (
    <Box
      borderRadius="lg"
      px={2}
      bgColor={bgColor}
      color="black"
      as="button"
      onClick={handleClick}
      _hover={hoverCss}
      disabled={!clickable}
    >
      <HStack justify="space-between">
        <Text as="span" fontSize="xs">
          {dayjs(appointment.dateTime).format('h a')}
        </Text>
        <Text as="span" fontSize="xs">
          {appointment.treatmentName}
        </Text>
      </HStack>
    </Box>
  )
}
