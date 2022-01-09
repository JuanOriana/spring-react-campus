import styled from "styled-components";

export const Separator = styled.div<{ reduced: boolean }>`
  width: ${(props) => (props.reduced ? "90%" : "auto")};
  height: 3px;
  border-top: 3px solid ${(props) => props.theme.cyanDarkest};
  border-bottom: 3px solid ${(props) => props.theme.cyanDarkest};
  margin: 20px 0;
  color: ${(props) => props.theme.cyanDarkest};
  align-self: center;
`;

export const BigWrapper = styled.div`
  display: flex;
  flex-direction: column;
  background: white;
  width: 95%;
  border-radius: 12px;
  min-height: 400px;
  padding: 20px;
  border-bottom: 3px solid ${(props) => props.theme.cyanDarkest};
  margin-bottom: 30px;
`;

export const SectionHeading = styled.h3`
  color: ${(props) => props.theme.cyanDarkest};
  font-size: 24px;
  margin-bottom: 20px;
`;

export const GeneralTitle = styled.h2`
  font-size: 24px;
`;
