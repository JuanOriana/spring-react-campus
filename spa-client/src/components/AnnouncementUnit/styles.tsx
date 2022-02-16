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

export const AnnouncementSubject = styled.div`
  font-size: 14px;
  margin: 5px 0 6px 2px;
  background-color: #176961; /* Green */
  border: none;
  color: white;
  padding: 5px 16px;
  text-align: center;
  text-decoration: none;
  border-radius: 20px 10px 20px 8px;
  display: inline-block;
`;

export const SmallIcon = styled.img`
  height: 32px;
  width: 32px;
  cursor: pointer;
  margin-left: 10px;
`;

export const ReadMoreButton = styled.button`
  font-size: 14px;
  border: none;
  background: white;
  font-style: italic;
  color: black;
  padding: 5px 16px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
`;
