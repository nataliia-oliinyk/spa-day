import { useNavigate } from 'react-router-dom'
import { ReactNode, useEffect } from 'react'
import useAuth from '../useAuth'

function ProtectedRoute({ children }: { children: ReactNode }) {
  const { isLoggedIn, isUserLoading } = useAuth()
  const navigate = useNavigate()

  useEffect(() => {
    if (!isLoggedIn && !isUserLoading) {
      navigate('/login', { replace: true })
    }
  }, [navigate, isLoggedIn, isUserLoading])
  return <>{children}</>
}

export default ProtectedRoute
