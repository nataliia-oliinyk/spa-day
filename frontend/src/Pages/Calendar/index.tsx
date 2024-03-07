import { Box, HStack, IconButton, Heading, Checkbox, Grid } from '@chakra-ui/react'
import { TiArrowLeftThick, TiArrowRightThick } from 'react-icons/ti'
import dayjs from 'dayjs'
import { useEffect, useMemo, useState } from 'react'
import { getNewMonthYear, getMonthYearDetails } from '../../lib/monthYear'
import { DateBox } from './DateBox'
import { useAppDispatch, useAppSelector } from '../../hooks'
import { monthAppointments, isLoading, loadAppointments } from '../../state/appointmentsSlice'
import { Loading } from '../Loading'
import { Appointment } from '../../types'
import { DATE_FORMAT } from '../../lib/constatnts'

type AppointmentObject = {
  [key: string]: Appointment[]
}

export function Calendar() {
  const currentDate = dayjs()
  const [monthYear, setMonthYear] = useState(getMonthYearDetails(currentDate))
  const [onlyAvailable, setOnlyAvailable] = useState(false)
  const dispatch = useAppDispatch()
  const isPageLoading = useAppSelector(isLoading)
  const appointments = useAppSelector(monthAppointments)

  useEffect(() => {
    dispatch(loadAppointments({ year: monthYear.year, month: monthYear.month }))
  }, [dispatch, monthYear])

  const formattedAppointments = useMemo(() => {
    const appointmentsByDays: AppointmentObject = {}
    ;[...Array(monthYear.lastMonthDate)].map((_, i) => {
      const day = i + 1
      const formattedCurrentDate = dayjs(`${monthYear.year}-${monthYear.month}-${day}`).format(
        DATE_FORMAT,
      )
      const appointmentsPerDay = (appointments as Appointment[]).filter(
        (appointment: Appointment) =>
          dayjs(appointment.dateTime).format(DATE_FORMAT) === formattedCurrentDate,
      )
      appointmentsByDays[`${day}`] = appointmentsPerDay
    })
    return appointmentsByDays
  }, [appointments, monthYear])

  const filteredAppointments = useMemo(() => {
    if (!onlyAvailable) return formattedAppointments
    const filteredData: { [key: string]: Appointment[] } = {}
    Object.entries(formattedAppointments).map(([key, value]) => {
      filteredData[key] = (value as Appointment[]).filter(
        (item) => !item.userId && dayjs(item.dateTime) >= dayjs(),
      )
    })
    return filteredData
  }, [onlyAvailable, formattedAppointments])

  function updateMonthYear(increment: number) {
    setMonthYear((prevData) => getNewMonthYear(prevData, increment))
  }

  if (isPageLoading) return <Loading />
  return (
    <Box>
      <HStack mt={10} spacing={8} justify="center">
        <IconButton
          aria-label="previous month"
          onClick={() => updateMonthYear(-1)}
          icon={<TiArrowLeftThick />}
          isDisabled={monthYear.startMonthDate < currentDate}
        />
        <Heading minW="40%" textAlign="center">
          {monthYear.monthName} {monthYear.year}
        </Heading>
        <IconButton
          aria-label="next month"
          onClick={() => updateMonthYear(1)}
          icon={<TiArrowRightThick />}
        />
        <Checkbox
          variant="flushed"
          width="48"
          position="absolute"
          right="10px"
          checked={onlyAvailable}
          onChange={() => setOnlyAvailable(!onlyAvailable)}
        >
          Only show available
        </Checkbox>
      </HStack>
      <Grid templateColumns="repeat(7, 1fr)" gap={4} my={5} mx={10}>
        {[...Array(monthYear.lastMonthDate)].map((_, i) => (
          <DateBox
            key={i + 1}
            date={i + 1}
            appointments={filteredAppointments[i + 1]}
            gridColumn={i <= 0 ? monthYear.firstDayOfWeek + 1 : undefined}
          />
        ))}
      </Grid>
    </Box>
  )
}
