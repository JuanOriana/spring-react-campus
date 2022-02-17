import styled from "styled-components";
import { Link } from "react-router-dom";

export const AdminTitle = styled.h1`
  color: ${(props) => props.theme.cyanDarkest};
  margin: 40px 0;
`;

export const LinkRedirectionButton = styled(Link)`
  align-items: center;
  appearance: none;
  background-color: ${(props) => props.theme.cyanDarkest};
  border-radius: 8px;
  border-width: 0;
  box-shadow: rgba(45, 35, 66, 0.4) 0 2px 4px,rgba(45, 35, 66, 0.3) 0 7px 13px -3px,#D6D6E7 0 -3px 0 inset;
  box-sizing: border-box;
  color: white;
  cursor: pointer;
  display: inline-flex;
  height: 48px;
  justify-content: center;
  line-height: 1;
  list-style: none;
  overflow: hidden;
  padding: 24px 16px 24px 16px;
  position: center;
  text-align: left;
  text-decoration: none;
  transition: box-shadow .15s,transform .15s;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  white-space: nowrap;
  will-change: box-shadow,transform;
  font-size: 25px;

  :hover {
    box-shadow: rgba(45, 35, 66, 0.4) 0 4px 8px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
    transform: translateY(-2px);
  }

  :active {
    box-shadow: #D6D6E7 0 3px 7px inset;
    transform: translateY(2px);
  }

  :focus {
    box-shadow: #D6D6E7 0 0 0 1.5px inset, rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
  }
`;
