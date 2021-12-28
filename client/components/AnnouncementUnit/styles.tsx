import styled from "styled-components";

export const AnnouncementWrapper = styled.div`
  align-items: center;
  background: white;
  width: 90%;
  border-radius: 12px;
  min-height: 200px;
  padding: 20px;
  border-bottom: 3px solid ${(props) => props.theme.cyanDarkest};
  margin-bottom: 5px;
  word-break: break-word;
`;

export const AnnouncementHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

export const AnnouncementTitle = styled.h4`
  font-size: 24px;
`;

export const AnnouncementDate = styled.div`
  font-size: 14px;
  margin: 0 0 6px 2px;
`;

export const SmallIcon = styled.img`
  height: 32px;
  width: 32px;
  cursor: pointer;
  margin-left: 10px;
`;
