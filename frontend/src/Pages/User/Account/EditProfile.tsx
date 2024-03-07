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
import PasswordWithChecklist from '../PasswordWithChecklist'
import { EMAIL_REGEX, PASSWORD_VALID_REGEX } from '../../../lib/constatnts'
import { useAppDispatch, useAppSelector } from '../../../hooks'
import { isLoading, updateUserData } from '../../../state/userSlice'
import useAuth from '../../../hooks/useAuth'
import useCustomToast from '../../../hooks/useCustomToast'
import { ErrorPayloadData } from '../../../types'

export function EditProfile() {
  const dispatch = useAppDispatch()
  const { user } = useAuth()
  const isPageLoading = useAppSelector(isLoading)
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [dirty, setDirty] = useState({ name: false, email: false })
  const toast = useCustomToast()

  const isEmailValid = email.match(EMAIL_REGEX) !== null || !dirty.email
  const isPasswordValid = password.match(PASSWORD_VALID_REGEX) !== null || !password
  const isNameValid = name !== '' || !dirty.name

  useEffect(() => {
    if (!dirty.name && !name) {
      const userName = user ? user.name : ''
      setName(userName)
    }
    if (!dirty.email && !email) {
      const userEmail = user ? user.email : ''
      setEmail(userEmail)
    }
  }, [user])

  const handleSubmit = async () => {
    toast.closeAll()
    try {
      await dispatch(updateUserData({ email, password, name })).unwrap()
      toast({
        title: 'Your profile was updated.',
        status: 'success',
        position: 'top',
      })
    } catch (err) {
      const errorsData = err as ErrorPayloadData
      if (errorsData.data && errorsData.data.errors) {
        const { errors } = errorsData.data
        errors.map((errorText: string) => {
          toast({
            title: errorText,
            status: 'error',
            position: 'top',
          })
        })
      }
    }
  }
  return (
    <Flex minH="84vh" textAlign="center" justify="center">
      <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
        <Stack textAlign="center">
          <Heading>Edit your profile</Heading>
        </Stack>
        <Box rounded="lg" bg="white" boxShadow="lg" p={8}>
          <Stack spacing={4}>
            <FormControl id="name" isInvalid={!isNameValid}>
              <FormLabel>Name</FormLabel>
              <Input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                onBlur={() => setDirty((prevState) => ({ ...prevState, name: true }))}
              />
              <FormErrorMessage>Name is required.</FormErrorMessage>
            </FormControl>
            <FormControl id="email" isInvalid={!isEmailValid}>
              <FormLabel>Email address</FormLabel>
              <Input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                onBlur={() => setDirty((prevState) => ({ ...prevState, email: true }))}
              />
              <FormErrorMessage>Email is required.</FormErrorMessage>
            </FormControl>
            <FormControl id="password" isInvalid={!isPasswordValid}>
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
                Change
              </Button>
            </HStack>
          </Stack>
        </Box>
      </Stack>
    </Flex>
  )
}
