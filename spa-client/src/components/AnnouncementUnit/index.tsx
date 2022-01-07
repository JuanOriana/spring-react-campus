import React from "react";
import PropTypes, { InferProps } from "prop-types";
import {
  AnnouncementDate,
  AnnouncementHeader,
  AnnouncementTitle,
  AnnouncementWrapper,
  SmallIcon,
} from "./styles";
import { Link } from "react-router-dom";

AnnouncementUnit.propTypes = {
  isGlobal: PropTypes.bool,
  isTeacher: PropTypes.bool,
  //Fix typing
  course: PropTypes.shape({
    courseId: PropTypes.number,
  }).isRequired,
  announcement: PropTypes.shape({
    title: PropTypes.string,
    author: PropTypes.shape({
      name: PropTypes.string,
      surname: PropTypes.string,
    }),
    content: PropTypes.string,
    date: PropTypes.string,
  }).isRequired,
};

function AnnouncementUnit({
  isGlobal,
  course,
  isTeacher,
  announcement,
}: InferProps<typeof AnnouncementUnit.propTypes>) {
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
