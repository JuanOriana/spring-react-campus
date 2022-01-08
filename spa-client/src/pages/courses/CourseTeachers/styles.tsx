import styled from "styled-components";

export const TeacherUnit = styled.div`
  display: flex;
  align-items: center;
`;

export const TeacherIcon = styled.img`
  height: 32px;
  width: 32px;
  border-radius: 50%;
  border: 2px solid ${(props) => props.theme.cyanDark};
  margin-right: 10px;
  overflow: hidden;
  object-fit: cover;
`;

export const MailIcon = styled.img`
  height: 52px;
  width: 52px;
  border-radius: 50%;
  margin-left: 10px;
  cursor: pointer;
`;
