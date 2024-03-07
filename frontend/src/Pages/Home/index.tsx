import { Icon, Text, Image, Stack } from '@chakra-ui/react'
import { GiFlowerPot } from 'react-icons/gi'
import splashImg from './assets/splash.jpg'

function Home() {
  return (
    <Stack textAlign="center" justify="center" height="84vh">
      <Image
        minHeight="100%"
        minWidth="1024px"
        width="100%"
        height="auto"
        position="fixed"
        top="0"
        left="0"
        zIndex="-1"
        src={splashImg}
        alt="peaceful orchids and stacked rocks"
      />
      <Text textAlign="center" fontFamily="Forum, sans-serif" fontSize="6em">
        <Icon m={4} verticalAlign="top" as={GiFlowerPot} />
        Lazy Days Spa
      </Text>
      <Text>Hours: limited</Text>
      <Text>Address: nearby</Text>
    </Stack>
  )
}

export default Home
