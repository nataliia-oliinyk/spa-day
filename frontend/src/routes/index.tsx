import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import HomePage from '../Pages/Home'
import Treatments from '../Pages/Treatments'
import { Staff } from '../Pages/Staff'
import { Calendar } from '../Pages/Calendar'
import { LogIn } from '../Pages/User/LogIn'
import { AccountDashboard } from '../Pages/User/AccountDashboard'
import { NotFound } from '../Pages/NotFound'
import ProtectedRoute from '../hooks/HOC/ProtectedRoute'
import { Loading } from '../Pages/Loading'
import { AppointmentList } from '../Pages/User/AppointmentList'
import App from '../App'
import { SignUp } from '../Pages/User/SignUp'
import withUserLoading from '../hooks/HOC/withUserLoading'

function Routes() {
  const LoginWithUserLoading = withUserLoading(LogIn)
  const SignUpWithUserLoading = withUserLoading(SignUp)
  const AccountWithUserLoading = withUserLoading(AccountDashboard)
  const AccountAppointmentsWithUserLoading = withUserLoading(AppointmentList)

  const routesForAuthenticatedOnly = [
    {
      path: '/account',
      element: (
        <ProtectedRoute>
          <App />
        </ProtectedRoute>
      ),
      children: [
        {
          path: '',
          element: <AccountWithUserLoading />,
        },
        {
          path: 'appointments',
          element: <AccountAppointmentsWithUserLoading />,
        },
      ],
    },
  ]

  const routesForPublic = [
    {
      path: '/',
      element: <App />,
      children: [
        {
          path: '/',
          element: <HomePage />,
        },
        {
          path: 'treatments',
          element: <Treatments />,
        },
        {
          path: 'staff',
          element: <Staff />,
        },
        {
          path: 'calendar',
          element: <Calendar />,
        },
        {
          path: 'login',
          element: <LoginWithUserLoading />,
        },
        {
          path: 'signup',
          element: <SignUpWithUserLoading />,
        },
        {
          path: '*',
          element: <NotFound />,
        },
      ],
    },
  ]

  const router = createBrowserRouter([...routesForPublic, ...routesForAuthenticatedOnly])
  return <RouterProvider router={router} fallbackElement={<Loading />} />
}

export default Routes
