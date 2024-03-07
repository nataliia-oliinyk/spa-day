import { Box, Alert, AlertIcon, AlertDescription } from '@chakra-ui/react'

type ErrorProps = {
  message: string
}

export function ErrorMessage({ message }: ErrorProps) {
  return (
    <Box my={4}>
      <Alert status="error" borderRadius={4}>
        <AlertIcon />
        <AlertDescription>{message}</AlertDescription>
      </Alert>
    </Box>
  )
}
