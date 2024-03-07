import {
  Box,
  Button,
  Flex,
  HStack,
  Icon,
  Link,
  MenuButton,
  Menu,
  MenuList,
  MenuItem,
} from '@chakra-ui/react'
import { ReactElement, ReactNode } from 'react'
import { GiFlowerPot } from 'react-icons/gi'
import { Link as RouterLink, useNavigate } from 'react-router-dom'
import { BiChevronDown } from 'react-icons/bi'
import useAuth from '../hooks/useAuth'

const Links = ['treatments', 'staff', 'calendar']

function NavLink({ to, children }: { to: string; children: ReactNode }) {
  return (
    <Link
      as={RouterLink}
      px={2}
      py={1}
      rounded="md"
      color="olive.200"
      _hover={{
        textDecoration: 'none',
        color: 'olive.500',
      }}
      to={to}
    >
      {children}
    </Link>
  )
}

export function Navbar(): ReactElement {
  const { user, isLoggedIn, logout } = useAuth()
  const navigate = useNavigate()
  return (
    <Box bg="gray.900" px={4}>
      <Flex h={16} alignItems="center" justify="space-between">
        <HStack spacing={8} alignItems="center">
          <NavLink to="/">
            <Icon w={8} h={8} as={GiFlowerPot} />
          </NavLink>
          <HStack as="nav" spacing={4}>
            {Links.map((link) => (
              <NavLink key={link} to={`/${link}`}>
                {link.toUpperCase()}
              </NavLink>
            ))}
          </HStack>
        </HStack>
        <HStack>
          {isLoggedIn && user ? (
            <>
              <Menu>
                <MenuButton as={Button} rightIcon={<BiChevronDown />}>
                  {user.name}
                </MenuButton>
                <MenuList>
                  <Link as={RouterLink} to="/account" color="olive.900">
                    <MenuItem>Profile</MenuItem>
                  </Link>
                  <Link as={RouterLink} to="/account/appointments" color="olive.900">
                    <MenuItem>Reserved Appointments</MenuItem>
                  </Link>
                </MenuList>
              </Menu>
              <Button onClick={() => logout()}>Sign out</Button>
            </>
          ) : (
            <Button onClick={() => navigate('login')}>Sign in</Button>
          )}
        </HStack>
      </Flex>
    </Box>
  )
}
