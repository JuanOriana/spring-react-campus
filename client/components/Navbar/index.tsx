import React from "react";
import PropTypes, { InferProps } from "prop-types";
import Link from "next/link";
import {
  LogoutButton,
  NavContainer,
  NavSectionItem,
  NavSectionsContainer,
  NavTitle,
  UserWrapper,
} from "./styles";
Navbar.propTypes = {
  currentTab: PropTypes.number,
  currentUser: PropTypes.shape({
    isAdmin: PropTypes.bool,
    image: PropTypes.object,
    name: PropTypes.string,
  }),
};

function Navbar({
  currentTab = 0,
  currentUser,
}: InferProps<typeof Navbar.propTypes>) {
  return (
    <NavContainer>
      <NavTitle>
        <Link href="/">Campus</Link>
      </NavTitle>
      {currentUser && (
        <>
          {!currentUser.isAdmin && (
            <NavSectionsContainer>
              <NavSectionItem>
                <Link href="/portal">Mis cursos</Link>
              </NavSectionItem>
              <NavSectionItem>
                <Link href="/announcements">Mis anuncios</Link>
              </NavSectionItem>
              <NavSectionItem>
                <Link href="/files">Mis archivos</Link>
              </NavSectionItem>
              <NavSectionItem>
                <Link href="/timetable">Mis hoarios</Link>
              </NavSectionItem>
            </NavSectionsContainer>
          )}
          <UserWrapper>
            <a href="/user">
              {!currentUser?.image && (
                <img src="/resources/images/default-user-image.png" />
              )}
              {currentUser?.image && (
                <img src="/user/profile-image/${currentUser.userId}" />
              )}
              <h4>{currentUser?.name}</h4>
            </a>
            <LogoutButton> Logout</LogoutButton>
          </UserWrapper>
        </>
      )}
      {!currentUser && <div style={{ width: "120 px" }}></div>}
    </NavContainer>
  );
}

export default Navbar;
