import React from "react";
import {
  AnnouncementDate,
  AnnouncementHeader,
  AnnouncementTitle,
  AnnouncementWrapper,
  SmallIcon,
} from "./styles";
import { Link } from "react-router-dom";
import { AnnouncementModel, CourseModel } from "../../types";

interface AnnouncementUnitProps {
  isGlobal?: boolean;
  isTeacher?: boolean;
  course: CourseModel;
  announcement: AnnouncementModel;
}

function AnnouncementUnit({
  isGlobal,
  course,
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
            }}
          >
            <p>
              {announcement.author?.name} {announcement.author?.surname}
            </p>
            {isGlobal && (
              <p>
                <Link to={`/course/${course.courseId}`}>SUBJECT NAME</Link>
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
