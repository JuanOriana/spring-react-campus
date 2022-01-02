import styled from "styled-components";

export const PageOrganizer = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
`;

export const PageContainer = styled.div`
  background: ${(props) => props.theme.cyanLight};
  width: 100%;
  flex: 1;
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
