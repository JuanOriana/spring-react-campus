import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import type Section from '../../types/Section'

import {
  LogoutButton,
  NavContainer,
  NavSectionItem,
  NavSectionsContainer,
  NavTitle,
  UserWrapper,
} from './styles'
import { useAuth } from '../../contexts/AuthContext'
import { handleService } from '../../scripts/handleService'
import { userService } from '../../services'
import { UserSectionImg } from '../../pages/User/styles'

// i18next imports
import { useTranslation } from 'react-i18next'
import '../../common/i18n/index'
//

Navbar.propTypes = {
  currentUser: PropTypes.shape({
    isAdmin: PropTypes.bool,
    image: PropTypes.object,
    name: PropTypes.string,
    userId: PropTypes.number,
  }),
}

function Navbar() {
  const { t } = useTranslation()
  let navigate = useNavigate()
  let location = useLocation()
  let { user, signout } = useAuth()
  const [userImg, setUserImg] = useState<string | undefined>(undefined)

  const pathname = location?.pathname
  const sections: Section[] = [
    { path: '/portal', name: 'Mis cursos' },
    { path: '/announcements', name: 'Mis anuncios' },
    { path: '/files', name: 'Mis archivos' },
    { path: '/timetable', name: 'Mis horarios' },
  ]

  useEffect(() => {
    if (user) {
      handleService(
        userService.getUserProfileImage(user?.userId),
        navigate,
        (userImg) => {
          setUserImg(
            userImg.size > 0 ? URL.createObjectURL(userImg) : undefined
          )
        },
        () => {
          return
        }
      )
    }
  }, [user])
  return (
    <NavContainer>
      <NavTitle>
        <Link to={user?.admin ? '/admin' : '/portal'}>CAMPUS</Link>
      </NavTitle>
      {user && (
        <>
          {!user.admin && (
            <NavSectionsContainer>
              {sections.map((section) => (
                <NavSectionItem
                  active={pathname === section.path}
                  key={section.path}
                >
                  <Link to={section.path}>
                    {t('Navbar.sections.' + section.path)}
                  </Link>
                </NavSectionItem>
              ))}
            </NavSectionsContainer>
          )}
          <UserWrapper>
            <Link to='/user' style={{ display: 'flex' }}>
              <UserSectionImg
                alt={user.username}
                src={userImg ? userImg : './images/default-user-image.png'}
                style={{ width: 32, height: 32, marginRight: 8 }}
              />
              <h4>
                {user?.name} {user?.surname}
              </h4>
            </Link>
            <LogoutButton
              onClick={() => {
                if (!window.confirm(t('Navbar.alert.confirmLogout'))) return
                signout(() => navigate('/'))
              }}
            >
              {' '}
              {t('Navbar.logout')}
            </LogoutButton>
          </UserWrapper>
        </>
      )}
      {!user && <div style={{ width: '120 px' }}></div>}
    </NavContainer>
  )
}

export default Navbar
