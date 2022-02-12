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
  onDelete?: (id: number) => void;
  announcement: AnnouncementModel;
}

function AnnouncementUnit({
  isGlobal,
  isTeacher,
  announcement,
  onDelete,
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
              Autor: {announcement.author?.name} {announcement.author?.surname}
            </p>
            {isGlobal && (
              <p style={{ fontWeight: 700 }}>
                <Link to={`/course/${announcement.course.courseId}`}>
                  Materia: {announcement.course.subject.name}
                </Link>
              </p>
            )}
          </div>
          {isTeacher && (
            <SmallIcon
              src="/images/trash-red.png"
              alt="delete"
              onClick={() => onDelete!(announcement.announcementId)}
            />
          )}
        </div>
      </AnnouncementHeader>
      <AnnouncementDate>{announcement.date.toDateString()}</AnnouncementDate>
      {announcement?.content}
    </AnnouncementWrapper>
  );
}

export default AnnouncementUnit;
