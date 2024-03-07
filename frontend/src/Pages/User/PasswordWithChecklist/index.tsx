import { FormEvent } from 'react'
import CheckList from './checkList'
import PasswordInput from './passwordInput'

type Props = {
  value: string | undefined
  setValue: (value: string) => void
}
function PasswordWithChecklist({ value, setValue }: Props) {
  const handleChange = (e: FormEvent<HTMLInputElement>) => {
    const target = e.target as HTMLInputElement
    setValue(target.value)
  }
  return (
    <>
      <PasswordInput value={value} onChange={handleChange} />
      <CheckList value={value} />
    </>
  )
}

export default PasswordWithChecklist
