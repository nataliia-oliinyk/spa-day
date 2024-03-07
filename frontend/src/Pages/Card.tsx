import { Box, Center, Heading, Image, Stack } from '@chakra-ui/react'
import { ReactElement, ReactNode } from 'react'

import { BASE_URL_IMAGE } from '../lib/constatnts'

type CardProps = {
  itemName: string
  image: string
  cardContents: ReactNode
}

export function Card({ itemName, image, cardContents }: CardProps): ReactElement {
  return (
    <Center py={12}>
      <Box
        p={6}
        maxW="330px"
        w="full"
        bg="white"
        boxShadow="2xl"
        rounded="lg"
        pos="relative"
        zIndex={1}
      >
        <Box rounded="lg" mt={-12} pos="relative" height="230px">
          <Image
            rounded="lg"
            height={230}
            width={282}
            objectFit="cover"
            src={`${BASE_URL_IMAGE}${image}`}
            alt={itemName}
          />
        </Box>
        <Stack pt={10}>
          <Heading textAlign="center" fontSize="2xl" textTransform="capitalize">
            {itemName}
          </Heading>
          {cardContents}
        </Stack>
      </Box>
    </Center>
  )
}
