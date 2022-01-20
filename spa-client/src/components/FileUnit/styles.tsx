import styled from "styled-components";

export const FileName = styled.p`
  color: white;
  font-weight: 700;
  margin-left: 10px;
  font-size: 20px;
`;

export const FileUnitWrapper = styled.div`
  width: calc(100% - 40px);
  border-radius: 15px;
  margin: 5px;
  background: ${(props) => props.theme.cyanDark};
  justify-content: space-between;
  display: flex;
  padding: 20px 20px;
`;

export const FileImg = styled.img`
  width: 80px;
  margin-bottom: 12px;
`;

export const FileCategoryName = styled.p`
  background: ${(props) => props.theme.cyanDarkest};
  color: white;
  border-radius: 12px;
  padding: 6px;
  font-weight: 700;
  margin-left: 15px;
`;

export const MediumIcon = styled.img`
  height: 48px;
  width: 48px;
  cursor: pointer;
`;
