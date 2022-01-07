import React from "react";
import PropTypes, { InferProps } from "prop-types";
import Link from "next/link";
import { Section } from "../../types";

import {
  LogoutButton,
  NavContainer,
  NavSectionItem,
  NavSectionsContainer,
  NavTitle,
  UserWrapper,
} from "./styles";

Navbar.propTypes = {
  router: PropTypes.shape({ pathname: PropTypes.string }),
  currentUser: PropTypes.shape({
    isAdmin: PropTypes.bool,
    image: PropTypes.object,
    name: PropTypes.string,
  }),
};

function Navbar({ currentUser, router }: InferProps<typeof Navbar.propTypes>) {
  const pathname = router?.pathname;
  const sections: Section[] = [
    { path: "/Portal", name: "Mis cursos" },
    { path: "/announcements", name: "Mis anuncios" },
    { path: "/files", name: "Mis archivos" },
    { path: "/Timetable", name: "Mis horarios" },
  ];
  return (
    <NavContainer>
      <NavTitle>
        <Link href="/">CAMPUS</Link>
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
                  <Link href={section.path}>{section.name}</Link>
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
