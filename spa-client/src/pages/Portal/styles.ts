import styled from "styled-components";

export const CoursesContainer = styled.div`
  background: white;
  width: 560px;
  padding: 12px 20px 12px 20px;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: solid 2px ${(props) => props.theme.cyanDarkest};
`;

export const Course = styled.div<{ isOld: boolean }>`
  color: ${(props) => (props.isOld ? "gray" : "black")};
  width: 100%;
  display: flex;
  padding: 10px;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid gray;
`;

export const PortalAnnouncements = styled.div`
  display: flex;
  flex-direction: column;
  width: 70%;
  align-items: center;
  margin: 30px 0;
`;

export const CourseName = styled.p`
  font-weight: 700;
`;
