import { FormEvent, memo, useState } from 'react'
import { Button, Input, InputGroup, InputRightElement } from '@chakra-ui/react'

type PasswordInputProps = {
  value: string | undefined
  onChange?: (event: FormEvent<HTMLInputElement>) => void
}

const PasswordInput = memo(({ onChange, value }: PasswordInputProps) => {
  const [isPlainText, setIsPlainText] = useState(false)
  return (
    <InputGroup size="md">
      <Input
        pr="4.5rem"
        type={isPlainText ? 'text' : 'password'}
        value={value}
        onChange={onChange}
      />
      <InputRightElement width="4.5rem">
        <Button h="1.75rem" size="sm" onClick={() => setIsPlainText(!isPlainText)}>
          {isPlainText ? 'Hide' : 'Show'}
        </Button>
      </InputRightElement>
    </InputGroup>
  )
})
PasswordInput.displayName = 'PasswordInput'
export default PasswordInput
