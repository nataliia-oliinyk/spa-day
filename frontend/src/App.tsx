import { ChakraProvider } from '@chakra-ui/react'
import { Outlet } from 'react-router-dom'
import { theme } from './theme'
import { Navbar } from './Pages/Navbar'

function App() {
  return (
    <ChakraProvider theme={theme}>
      <Navbar />
      <Outlet />
    </ChakraProvider>
  )
}

export default App
