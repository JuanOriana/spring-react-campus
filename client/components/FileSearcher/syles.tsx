import styled from "styled-components";

export const FileQueryContainer = styled.form`
  display: flex;
  flex-direction: column;
  background: ${(props) => props.theme.cyanDarkest};
  padding: 12px;
  margin-bottom: 20px;
  border-radius: 12px;
`;

export const FileFilterContainer = styled.div`
  display: none;
  padding: 0 30px;
  flex-direction: column;
`;

export const FileSelectLabel = styled.label`
  font-size: 18px;
  font-weight: 700;
  color: ${(props) => props.theme.cyanLight};
`;

export const FileSelect = styled.select`
  font-size: 18px;
  border-radius: 12px;
  background: ${(props) => props.theme.cyanLight};
  padding: 5px;
  outline: none;
  border: none;
`;

export const FileCheckboxLabel = styled.label`
  color: ${(props) => props.theme.cyanLight};
`;

export const FileCheckbox = styled.input`
  border: 1px solid ${(props) => props.theme.cyanLight};
`;

export const PaginationArrow = styled.img`
  cursor: pointer;
  margin: 0 8px;
  height: 36px;
`;
