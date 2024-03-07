import { useEffect } from 'react'
import { Box, Heading, HStack } from '@chakra-ui/react'
import { Loading } from '../Loading'
import { getAllTreatments, isLoading, loadTreatments } from '../../state/treatmentsSlice'
import { useAppDispatch, useAppSelector } from '../../hooks'
import { TreatmentCard } from './TreatmentCard'

function Treatments() {
  const dispatch = useAppDispatch()
  const isPageLoading = useAppSelector(isLoading)
  const treatments = useAppSelector(getAllTreatments)
  useEffect(() => {
    dispatch(loadTreatments())
  }, [dispatch])

  if (isPageLoading) return <Loading />
  return (
    <Box>
      <Heading mt={10} textAlign="center">
        Available Treatments
      </Heading>
      <HStack m={10} spacing={8} justify="center">
        {treatments.map((item) => (
          <TreatmentCard data={item} key={item.id} />
        ))}
      </HStack>
    </Box>
  )
}

export default Treatments
