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
} from '@chakra-ui/react'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAppSelector } from '../../hooks'
import { isLoading } from '../../state/userSlice'
import useAuth from '../../hooks/useAuth'
import PasswordWithChecklist from './PasswordWithChecklist'
import { EMAIL_REGEX, PASSWORD_VALID_REGEX } from '../../lib/constatnts'

export function SignUp() {
  const navigate = useNavigate()
  const isPageLoading = useAppSelector(isLoading)
  const { signup, isLoggedIn } = useAuth()
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  useEffect(() => {
    if (isLoggedIn) navigate('/account')
  }, [navigate, isLoggedIn])
  const handleSubmit = async () => {
    await signup(email, password, name)
  }
  const isEmailValid = email.match(EMAIL_REGEX) !== null
  const isPasswordValid = password.match(PASSWORD_VALID_REGEX) !== null
  const isNameValid = name !== ''

  return (
    <Flex minH="84vh" textAlign="center" justify="center">
      <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
        <Stack textAlign="center">
          <Heading>Create your account</Heading>
        </Stack>
        <Box rounded="lg" bg="white" boxShadow="lg" p={8}>
          <Stack spacing={4}>
            <FormControl id="name" isRequired isInvalid={!isNameValid && name.length > 1}>
              <FormLabel>Name</FormLabel>
              <Input type="text" value={name} onChange={(e) => setName(e.target.value)} />
              <FormErrorMessage>Name is required.</FormErrorMessage>
            </FormControl>
            <FormControl id="email" isRequired isInvalid={!isEmailValid && email.length > 1}>
              <FormLabel>Email address</FormLabel>
              <Input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
              <FormErrorMessage>Email is required.</FormErrorMessage>
            </FormControl>
            <FormControl
              id="password"
              isRequired
              isInvalid={!isPasswordValid && password.length > 1}
            >
              <FormLabel>Password</FormLabel>
              <PasswordWithChecklist value={password} setValue={setPassword} />
            </FormControl>
            <HStack spacing={2} width="100%" justify="flex-end" wrap="wrap">
              <Button
                isLoading={isPageLoading}
                variant="outline"
                type="submit"
                isDisabled={!(isEmailValid && isPasswordValid && isNameValid)}
                onClick={handleSubmit}
              >
                Sign up
              </Button>
            </HStack>
          </Stack>
        </Box>
      </Stack>
    </Flex>
  )
}
