import styled from "styled-components";

export const CourseTableForm = styled.form`
  background: white;
  padding: 10px;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
`;

export const FormText = styled.p`
  margin-top: 20px;
  font-size: 22px;
  font-weight: 700;
  color: ${(props) => props.theme.cyanDark};
  margin-left: 10px;
`;

export const CourseTable = styled.table`
  background: #efefef;
  border-collapse: collapse;
  margin: 50px 0 12px 0;
  th,
  td {
    padding: 1em;
    width: 8em;
    border: 1px solid black;
  }
`;

export const CourseTableHeader = styled.tr`
  color: #efefef;
  background: var(--cyan-darkest);
  text-transform: uppercase;
  font-size: 1em;
  text-align: center;
`;
