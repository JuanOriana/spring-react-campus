import React from "react";
import {
  AnnouncementDate,
  AnnouncementHeader,
  AnnouncementTitle,
  AnnouncementWrapper,
  SmallIcon,
} from "./styles";
import { Link } from "react-router-dom";
import { AnnouncementModel } from "../../types";

interface AnnouncementUnitProps {
  isGlobal?: boolean;
  isTeacher?: boolean;
  announcement: AnnouncementModel;
}

function AnnouncementUnit({
  isGlobal,
  isTeacher,
  announcement,
}: AnnouncementUnitProps) {
  return (
    <AnnouncementWrapper>
      <AnnouncementHeader>
        <AnnouncementTitle>{announcement.title}</AnnouncementTitle>
        <div style={{ display: "flex" }}>
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              fontSize: "14px",
              textAlign: "right",
            }}
          >
            <p>
              {announcement.author?.name} {announcement.author?.surname}
            </p>
            {isGlobal && (
              <p style={{ fontWeight: 700 }}>
                <Link to={`/course/${announcement.course.courseId}`}>
                  {announcement.course.subject.name}
                </Link>
              </p>
            )}
          </div>
          {isTeacher && <SmallIcon src="/images/trash-red.png" alt="delete" />}
        </div>
      </AnnouncementHeader>
      <AnnouncementDate>{announcement.date}</AnnouncementDate>
      {announcement?.content}
    </AnnouncementWrapper>
  );
}

export default AnnouncementUnit;
