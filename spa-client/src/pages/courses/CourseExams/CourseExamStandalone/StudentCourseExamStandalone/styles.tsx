import styled from "styled-components";
import { Link } from "react-router-dom";

export const LinkButton = styled(Link)`
  border-radius: 12px;
  width: 200px;
  padding: 5px;
  font-size: 22px;
  font-weight: 700;
  background: ${(props) => props.theme.cyanDark};
  color: ${(props) => props.theme.cyanLight};
  border: 2px solid ${(props) => props.theme.cyanLight};
  cursor: pointer;
  align-self: center;
  margin-top: 20px;
  resize: none;
  margin-right: 15px;
  background: #a80011;
  text-align: center;
`;

export const CommentTitle = styled.h3`
  margin-top: 20px;
  font-size: 22px;
  font-weight: 700;
  color: ${(props) => props.theme.cyanDark};
  margin-left: 10px;
`;
