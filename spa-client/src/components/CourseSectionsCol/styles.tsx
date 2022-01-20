import styled from "styled-components";

export const CourseSectionsColWrapper = styled.div`
  background: ${(props) => props.theme.cyanDarkest};
  color: ${(props) => props.theme.cyanLight};
  display: flex;
  flex-direction: column;
  width: 220px;
  height: 400px;
  padding: 20px;
  border-bottom-right-radius: 14px;
`;

export const CourseSectionsColTitle = styled.h3`
  margin-bottom: 10px;
  width: 100%;
  text-align: center;
  border-bottom: 2px solid var(--cyan-light);
  padding-bottom: 10px;
`;

export const CourseSectionsItem = styled.p<{ isActive?: boolean }>`
  color: ${(props) => (props.isActive ? "white" : "inherit")};
  font-size: 18px;
  list-style-type: none;
  margin-bottom: 9px;
  :hover {
    color: white;
    transition: all 0.2s ease-in-out;
  }
`;
