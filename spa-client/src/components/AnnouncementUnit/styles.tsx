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
  &:hover {
    background: ${({ theme }) => theme.cyanDark};
    transition: ease-in-out 0.2s;
  }
`;

export const SmallIcon = styled.img`
  height: 32px;
  width: 32px;
  cursor: pointer;
  margin-left: 10px;
`;

export const ReadMoreButton = styled.button`
  border: none;
  background: ${({ theme }) => theme.cyanDark};
  font-style: italic;
  margin-left: 10px;
  padding: 0px 8px;
  border-radius: 12px 4px 12px 4px;
  text-align: center;
  cursor: pointer;

  &:hover {
    background: ${({ theme }) => theme.cyanLight};
    transition: ease-in-out 0.2s;
  }
`;
