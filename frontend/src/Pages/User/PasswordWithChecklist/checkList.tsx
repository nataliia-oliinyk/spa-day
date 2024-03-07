import { VStack, Box } from '@chakra-ui/react'
import styles from './checklist.module.css'
import {
  LENGTH_REGEX,
  NUMBER_REGEX,
  SPECIAL_CHARS_REGEX,
  UPPERCASE_REGEX,
} from '../../../lib/constatnts'

type Props = {
  value: string | undefined
  className?: string
}
const rules = [
  { label: 'One uppercase', pattern: UPPERCASE_REGEX },
  { label: 'One number', pattern: NUMBER_REGEX },
  { label: 'Min 8 characters', pattern: LENGTH_REGEX },
  { label: 'One special char', pattern: SPECIAL_CHARS_REGEX },
]
function CheckList({ value }: Props) {
  return (
    <VStack align="flex-start">
      {rules.map((rule, index) => {
        const cn = value && value.match(rule.pattern) ? styles.passed : ''
        return (
          <Box className={cn} key={index}>
            {rule.label}
          </Box>
        )
      })}
    </VStack>
  )
}
export default CheckList
