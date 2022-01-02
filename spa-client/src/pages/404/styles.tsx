import styled from "styled-components";

export const ErrorTitle = styled.h1`
  color: ${(props) => props.theme.cyanDarkest};
  align-self: center;
  margin-top: 30px;
`;

export const BackToPortalButton = styled.a`
  background-color: ${(props) => props.theme.cyanDarkest};
  border: none;
  border-radius: 8px;
  color: white;
  padding: 10px 16px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 20px 2px;
  cursor: pointer;
  :hover,
  :active {
    background-color: ${(props) => props.theme.cyanDark};
  }
`;
