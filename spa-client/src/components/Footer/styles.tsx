import styled from "styled-components";

export const FooterContainer = styled.footer`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: ${(props) => props.theme.cyanDark};
  color: white;
  font-size: 16px;
  padding: 6px;
`;
