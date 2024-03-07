import { Heading, Box, Text, Center } from '@chakra-ui/react'
import { ReactElement } from 'react'

export function NotFound(): ReactElement {
  return (
    <Box>
      <Heading mt={10} textAlign="center" margin={10}>
        Page not found
      </Heading>
      <Center>
        <Text>Sorry, the page you are looking for could not be found. </Text>
      </Center>
    </Box>
  )
}
