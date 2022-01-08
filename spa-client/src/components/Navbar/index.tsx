import React from "react";
import PropTypes, { InferProps } from "prop-types";
import { Link, useLocation } from "react-router-dom";
import type Section from "../../../types/Section";

import {
  LogoutButton,
  NavContainer,
  NavSectionItem,
  NavSectionsContainer,
  NavTitle,
  UserWrapper,
} from "./styles";

Navbar.propTypes = {
  currentUser: PropTypes.shape({
    isAdmin: PropTypes.bool,
    image: PropTypes.object,
    name: PropTypes.string,
    userId: PropTypes.number,
  }),
};

function Navbar({ currentUser }: InferProps<typeof Navbar.propTypes>) {
  const location = useLocation();
  const pathname = location?.pathname;
  const sections: Section[] = [
    { path: "/portal", name: "Mis cursos" },
    { path: "/announcements", name: "Mis anuncios" },
    { path: "/files", name: "Mis archivos" },
    { path: "/timetable", name: "Mis horarios" },
  ];
  return (
    <NavContainer>
      <NavTitle>
        <Link to="/">CAMPUS</Link>
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
                  <Link to={section.path}>{section.name}</Link>
                </NavSectionItem>
              ))}
            </NavSectionsContainer>
          )}
          <UserWrapper>
            <Link to="/user">
              {!currentUser?.image && (
                <img src="/images/default-user-image.png" />
              )}
              {currentUser?.image && (
                <img src={`/user/profile-image/${currentUser.userId}`} />
              )}
              <h4>{currentUser?.name}</h4>
            </Link>
            <LogoutButton> Logout</LogoutButton>
          </UserWrapper>
        </>
      )}
      {!currentUser && <div style={{ width: "120 px" }}></div>}
    </NavContainer>
  );
}

export default Navbar;
