import styled from "styled-components";
import {Link} from "react-router-dom";

export const DeliveredLink = styled(Link)<{isDelivered?:boolean}>`
  pointer-events: ${(props) => props.isDelivered? "none":"auto"}; 
  display: flex; 
  align-items: center ;
  margin-left: 10px;
`

export const PaginationArrow = styled.img`
  cursor: pointer;
  margin: 0 8px;
  height: 36px;
`
export const MediumIcon = styled.img`
  height: 48px;
  width: 48px;
  cursor: pointer;
`

export const ExamComment = styled.p`
  color: lightyellow;
  font-weight: 700;
  margin-left: 40px;
  border-top: 2px dotted white;
  font-size: 18px;
  padding-top: 10px;
`