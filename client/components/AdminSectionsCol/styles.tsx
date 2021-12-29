import styled from "styled-components";

export const AdminSectionsColWrapper = styled.div<{ isSmall?: boolean }>`
  display: flex;
  flex-direction: column;
  height: 400px;
  background: ${(props) => props.theme.cyanDarkest};
  color: ${(props) => props.theme.cyanLight};
  padding: 20px;
  border-bottom-right-radius: 14px;
  border-top-right-radius: 12px;
  width: ${(props) => (props.isSmall ? "210px" : "300px")};
`;

export const AdminSectionsColTitle = styled.h3`
  margin-bottom: 10px;
  width: 100%;
  text-align: center;
  border-bottom: 2px solid var(--cyan-light);
  padding-bottom: 10px;
`;

export const AdminSectionsItem = styled.p<{ isActive?: boolean }>`
  color: ${(props) => (props.isActive ? "white" : "inherit")};
  font-size: 18px;
  list-style-type: none;
  margin-bottom: 9px;
  :hover {
    color: white;
    transition: all 0.2s ease-in-out;
  }
`;
