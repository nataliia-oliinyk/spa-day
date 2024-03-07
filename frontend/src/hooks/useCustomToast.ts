import { useToast } from '@chakra-ui/react'

const useCustomToast = () =>
  useToast({
    isClosable: true,
    variant: 'subtle',
    position: 'bottom',
    duration: 9000,
  })
export default useCustomToast
