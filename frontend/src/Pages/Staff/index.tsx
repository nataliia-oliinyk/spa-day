import { Box, Heading, HStack, Radio, RadioGroup } from '@chakra-ui/react'
import { useEffect, useMemo, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../hooks'
import { allStaff, isLoading, loadStaff } from '../../state/staffSlice'
import { Loading } from '../Loading'
import { StaffCard } from './StaffCard'
import { getAllTreatments, loadTreatments } from '../../state/treatmentsSlice'

export function Staff() {
  const dispatch = useAppDispatch()
  const isPageLoading = useAppSelector(isLoading)
  const [filter, setFilter] = useState('all')
  const staff = useAppSelector(allStaff)
  const treatments = useAppSelector(getAllTreatments)
  useEffect(() => {
    dispatch(loadStaff())
    dispatch(loadTreatments())
  }, [dispatch])
  const filteredStaff = useMemo(() => {
    if (filter === 'all') return staff
    return staff.filter((person) => person.treatments.indexOf(filter.toLowerCase()) !== -1)
  }, [filter, staff])
  if (isPageLoading) return <Loading />
  return (
    <Box>
      <Heading mt={10} textAlign="center">
        Our Staff
      </Heading>
      <HStack m={10} spacing={8} justify="center">
        {filteredStaff.map((item) => (
          <StaffCard data={item} key={item.id} />
        ))}
      </HStack>
      <RadioGroup onChange={setFilter} value={filter}>
        <HStack my={10} spacing={8} justify="center">
          <Heading size="md">Filter by treatment:</Heading>
          <Radio value="all">All</Radio>
          {treatments.map((item) => (
            <Radio value={item.name} key={item.id}>
              {item.name}
            </Radio>
          ))}
        </HStack>
      </RadioGroup>
    </Box>
  )
}
