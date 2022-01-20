import styled from "styled-components";

export const BackImg = styled.img`
  width: 30px;
  transform: rotate(180deg);
  margin: 0 10px;
`;

export const UserContainer = styled.div`
  display: flex;
  width: calc(95% - 60px);
  margin: 30px 10px 10px 10px;
  border-radius: 12px;
  border: 2px solid ${(props) => props.theme.cyanDarkest};
  padding: 30px;
  align-self: center;
`;

export const UserColumn = styled.div`
  width: 50%;
  display: flex;
  flex-direction: column;
`;
