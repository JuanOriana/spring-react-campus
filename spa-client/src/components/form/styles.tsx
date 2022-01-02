import styled from "styled-components";

export const FormWrapper = styled.form`
  align-self: center;
  background: white;
  display: flex;
  flex-direction: column;
  border: 10px solid ${(props) => props.theme.cyanDark};
  width: 95%;
  border-radius: 12px;
  min-height: 200px;
  padding: 20px;
  border-bottom: 10px solid ${(props) => props.theme.cyanDarkest};
  margin-bottom: 5px;
`;

export const FormButton = styled.button`
  border-radius: 12px;
  width: 200px;
  padding: 5px;
  font-size: 22px;
  font-weight: 700;
  background: ${(props) => props.theme.cyanDark};
  color: ${(props) => props.theme.cyanLight};
  border: 2px solid ${(props) => props.theme.cyanLight};
  cursor: pointer;
  align-self: center;
  margin-top: 20px;
  resize: none;
`;

export const FormLabel = styled.label`
  margin-top: 20px;
  font-size: 22px;
  font-weight: 700;
  color: ${(props) => props.theme.cyanDark};
  margin-left: 10px;
`;

export const FormInput = styled.input`
  font-size: 18px;
  margin: 0 10px;
  border-radius: 12px;
  border: 2px solid ${(props) => props.theme.cyanDark};
  padding: 5px;
  outline: none;
`;

export const ErrorMessage = styled.p`
  color: red;
  margin-left: 10px;
`;
