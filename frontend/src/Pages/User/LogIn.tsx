import {
  Box,
  Button,
  Flex,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Heading,
  HStack,
  Input,
  Stack,
  Link as ChakraLink,
} from '@chakra-ui/react'
import { Link as ReactRouterLink, useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { useAppSelector } from '../../hooks'
import { isLoading } from '../../state/userSlice'
import { EMAIL_REGEX } from '../../lib/constatnts'
import useAuth from '../../hooks/useAuth'

export function LogIn() {
  const navigate = useNavigate()
  const isPageLoading = useAppSelector(isLoading)
  const { isLoggedIn, login } = useAuth()
  const [email, setEmail] = useState('test@example.com')
  const [password, setPassword] = useState('Password1!')
  const [dirty, setDirty] = useState({ email: false, password: false })

  useEffect(() => {
    if (isLoggedIn) navigate('/account')
  }, [navigate, isLoggedIn])
  const handleSubmit = async () => {
    await login(email, password)
  }
  const isEmailValid = email.match(EMAIL_REGEX) !== null
  return (
    <Flex minH="84vh" textAlign="center" justify="center">
      <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
        <Stack textAlign="center">
          <Heading>Sign in to your account</Heading>
        </Stack>
        <Box rounded="lg" bg="white" boxShadow="lg" p={8}>
          <Stack spacing={4}>
            <FormControl id="email" isRequired isInvalid={!isEmailValid && dirty.email}>
              <FormLabel>Email address</FormLabel>
              <Input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                onBlur={() => setDirty((prevState) => ({ ...prevState, email: true }))}
              />
              <FormErrorMessage>Email is required.</FormErrorMessage>
            </FormControl>
            <FormControl id="password" isRequired isInvalid={!password && dirty.password}>
              <FormLabel>Password</FormLabel>
              <Input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onBlur={() => setDirty((prevState) => ({ ...prevState, password: true }))}
              />
              <FormErrorMessage>Password is required.</FormErrorMessage>
            </FormControl>
            <HStack spacing={2} width="100%" justify="flex-end" wrap="wrap">
              <ChakraLink color="teal.500" as={ReactRouterLink} to="/signup">
                create an account
              </ChakraLink>
              <Button
                isLoading={isPageLoading}
                variant="outline"
                type="submit"
                isDisabled={!isEmailValid || !password}
                onClick={handleSubmit}
              >
                Sign in
              </Button>
            </HStack>
          </Stack>
        </Box>
      </Stack>
    </Flex>
  )
}
