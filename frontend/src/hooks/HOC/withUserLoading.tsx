import useAuth from '../useAuth'
import { Loading } from '../../Pages/Loading'
import React from 'react'

const withUserLoading =
  <P extends object>(WrappedComponent: React.ComponentType<P>) =>
  (props: P) => {
    const { isUserLoading } = useAuth()
    return isUserLoading ? <Loading /> : <WrappedComponent {...props} />
  }

export default withUserLoading
