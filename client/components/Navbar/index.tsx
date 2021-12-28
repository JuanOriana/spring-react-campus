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
  router: PropTypes.shape({ pathname: PropTypes.string }),
  currentUser: PropTypes.shape({
    isAdmin: PropTypes.bool,
    image: PropTypes.object,
    name: PropTypes.string,
  }),
};

interface Section {
  path: string;
  name: string;
}

function Navbar({
  currentTab = 0,
  currentUser,
  router,
}: InferProps<typeof Navbar.propTypes>) {
  const pathname = router?.pathname;
  const sections: Section[] = [
    { path: "/portal", name: "Mis cursos" },
    { path: "/announcements", name: "Mis anuncios" },
    { path: "/files", name: "Mis archivos" },
    { path: "/timetable", name: "Mis horarios" },
  ];
  return (
    <NavContainer>
      <NavTitle>
        <Link href="/">Campus</Link>
      </NavTitle>
      {currentUser && (
        <>
          {!currentUser.isAdmin && (
            <NavSectionsContainer>
              {sections.map((section) => (
                <NavSectionItem
                  active={pathname === section.path}
                  key={section.path}
                >
                  <Link href="/portal">{section.name}</Link>
                </NavSectionItem>
              ))}
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
