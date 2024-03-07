import { Text } from '@chakra-ui/react'
import { ReactElement } from 'react'

import type { Staff } from '../../types'
import { Card } from '../Card'

type StaffProps = {
  data: Staff
}

export function StaffCard({ data }: StaffProps): ReactElement {
  const cardContents = <Text>{data.treatments.join(', ')}</Text>
  const imagePath = `employees/getImage/${data.image}`
  return <Card itemName={data.name} image={imagePath} cardContents={cardContents} />
}
