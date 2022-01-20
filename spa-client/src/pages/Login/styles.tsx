import styled from "styled-components";

export const LoginWrapper = styled.form`
  background: white;
  width: 200px;
  padding: 20px 40px;
  border-radius: 12px;
  margin-bottom: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: solid 2px ${(props) => props.theme.cyanDarkest};
`;

export const LoginButton = styled.button`
  margin: 10px 0;
  background: ${(props) => props.theme.cyanDarkest};
  border-radius: 10px;
  color: white;
  border: 3px solid ${(props) => props.theme.cyanDark};
  padding: 10px 20px;
  font-size: 22px;
  cursor: pointer;
`;

export const LoginLabel = styled.label`
  color: ${(props) => props.theme.cyanDarkest};
  font-size: 18px;
  margin: 5px 0;
`;

export const LoginInput = styled.input`
  border-radius: 6px;
  border: 1px solid ${(props) => props.theme.cyanDarkest};
  padding: 6px;
  color: ${(props) => props.theme.cyanDarkest};
  margin-bottom: 4px;
  font-size: 18px;
`;
