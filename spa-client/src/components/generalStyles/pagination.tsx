import styled from "styled-components";

export const PaginationWrapper = styled.div`
  display: flex;
  align-items: center;
  font-size: 27px;
  font-weight: 700;
  margin: 20px 0;
`;

export const PaginationArrow = styled.img<{ xRotated?: boolean }>`
  transform: ${(props) => (props.xRotated ? "rotate(180deg);" : "")};
  cursor: pointer;
  margin: 0 8px;
  height: 36px;
`;
