import styled from "styled-components";
import { Link } from "react-router-dom";

export const AdminTitle = styled.h1`
  color: ${(props) => props.theme.cyanDarkest};
  margin: 40px 0;
`;

export const LinkRedirectionButton = styled(Link)`
  font-size: 30px;
  padding: 30px;
  background: ${(props) => props.theme.cyanDarkest};
  border-radius: 10px;
  color: white;
  border: 3px solid ${(props) => props.theme.cyanDark};
  cursor: pointer;
`;
