import styled from "styled-components";

export const NavContainer = styled.nav`
  width: 100vw;
  max-width: 100vw;
  background: ${(props) => props.theme.cyanDark};
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
`;

export const NavTitle = styled.h1`
  font-family: "Righteous", cursive;
  font-size: 38px;
  margin: 0 0 0 14px;
`;

export const NavSectionsContainer = styled.ul`
  display: flex;
  height: 70px;
  align-items: center;
  margin-left: 48px;
`;

export const NavSectionItem = styled.li<{ active?: boolean }>`
  font-weight: ${(props) => (props.active ? 700 : 500)};
  list-style-type: none;
  margin-right: 40px;
  font-size: 16px;
`;

export const NavSectionItemActive = styled.li`
  font-weight: 700;
`;

export const UserWrapper = styled.div`
  margin-right: 40px;
  font-weight: 700;
  font-size: 20px;
  display: flex;
  align-items: center;
  @media (max-width: 990px) {
    align-items: flex-end;
    flex-direction: column;
  }
`;

export const LogoutButton = styled.button`
  color: saddlebrown;
  background: none;
  border: 2px solid saddlebrown;
  cursor: pointer;
  border-radius: 12px;
  margin-left: 10px;
  :hover {
    background: lightcoral;
    transition: ease-in-out 0.2s;
  }
  @media (max-width: 990px) {
    margin-left: 0;
  }
`;

export const SuccessBox = styled.div`
  background: ${(props) => props.theme.successBack};
  color: ${(props) => props.theme.successFront};
  border-bottom: 2px solid ${(props) => props.theme.successFront};
  padding: 3px;
  padding-left: 20px;
  font-weight: 700;
`;
