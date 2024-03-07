import { Text } from '@chakra-ui/react'
import { ReactElement } from 'react'

import type { Treatment } from '../../types'
import { Card } from '../Card'

type TreatmentProps = {
  data: Treatment
}

export function TreatmentCard({ data }: TreatmentProps): ReactElement {
  const cardContents = <Text>{data.description}</Text>
  const imagePath = `treatments/getImage/${data.image}`
  return <Card itemName={data.name} image={imagePath} cardContents={cardContents} />
}
