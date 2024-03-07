import { Box, Center, Heading, IconButton, Table, Tbody, Td, Text, Tr } from '@chakra-ui/react'
import { Link } from 'react-router-dom'
import dayjs from 'dayjs'
import { ImCancelCircle } from 'react-icons/im'
import { useEffect, useMemo } from 'react'
import { sortBy } from 'lodash'
import { Loading } from '../Loading'
import useAuth from '../../hooks/useAuth'
import { Appointment } from '../../types'
import {
  isLoading,
  loadUserAppointments,
  userAppointments,
  updateUserAppointments,
  cancelUserAppointment,
} from '../../state/appointmentsSlice'
import { useAppDispatch, useAppSelector } from '../../hooks'
import useCustomToast from '../../hooks/useCustomToast'

export function AppointmentList() {
  const { user, token } = useAuth()
  const dispatch = useAppDispatch()
  const isPageLoading = useAppSelector(isLoading)
  const appointments = useAppSelector(userAppointments)
  const toast = useCustomToast()
  useEffect(() => {
    if (user && token) {
      dispatch(loadUserAppointments({ user, token }))
    }
  }, [dispatch, user, token])
  const handleClick = async (cancelledAppointment: Appointment) => {
    if (token) {
      await dispatch(cancelUserAppointment({ appointment: cancelledAppointment, token }))
      dispatch(updateUserAppointments(cancelledAppointment.id))
      toast({
        title: 'You have cancelled the appointment',
        status: 'success',
      })
    }
  }
  const filteredAppointments = useMemo(
    () => sortBy(appointments, ['dateTime'], ['asc']),
    [appointments],
  )
  if (isPageLoading) return <Loading />
  return (
    <Box>
      <Heading mt={10} textAlign="center">
        Your Appointments
      </Heading>
      <Center>
        {filteredAppointments.length > 0 ? (
          <Table variant="simple" m={10} maxWidth="500px">
            <Tbody>
              {filteredAppointments.map((appointment) => (
                <Tr key={appointment.id}>
                  <Td>
                    <Text>{dayjs(appointment.dateTime).format('MMM D')}</Text>
                  </Td>
                  <Td>
                    <Text>{dayjs(appointment.dateTime).format('h a')}</Text>
                  </Td>
                  <Td>
                    <Text>{appointment.treatmentName}</Text>
                  </Td>
                  <Td>
                    <IconButton
                      aria-label="cancel appointment"
                      onClick={() => handleClick(appointment)}
                      icon={<ImCancelCircle />}
                    />
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        ) : (
          <Link to="/Calendar">Book an appointment</Link>
        )}
      </Center>
    </Box>
  )
}
