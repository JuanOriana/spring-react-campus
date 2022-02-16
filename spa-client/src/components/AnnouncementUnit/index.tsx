import React, { useState } from "react";
import {
  AnnouncementDate,
  AnnouncementHeader,
  AnnouncementSubject,
  AnnouncementTitle,
  AnnouncementWrapper,
  ReadMoreButton,
  SmallIcon,
} from "./styles";
import { Link } from "react-router-dom";
import { AnnouncementModel } from "../../types";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

interface AnnouncementUnitProps {
  isGlobal?: boolean;
  isTeacher?: boolean;
  onDelete?: (id: number) => void;
  announcement: AnnouncementModel;
}

const ReadMore = (content: string) => {
  const { t } = useTranslation();
  const text = content;
  const [isReadMore, setIsReadMore] = useState(true);
  const toggleReadMore = () => {
    setIsReadMore(!isReadMore);
  };
  return (
    <p>
      {isReadMore ? text.slice(0, 300) : text}
      <span onClick={toggleReadMore}>
        {isReadMore ? (
          <ReadMoreButton>
            {t("AnnouncementUnit.readMoreButton")}
          </ReadMoreButton>
        ) : (
          <ReadMoreButton>
            {t("AnnouncementUnit.readLessButton")}
          </ReadMoreButton>
        )}
      </span>
    </p>
  );
};

function AnnouncementUnit({
  isGlobal,
  isTeacher,
  announcement,
  onDelete,
}: AnnouncementUnitProps) {
  const { t } = useTranslation();
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
              {t("AnnouncementUnit.author", {
                name: announcement.author?.name,
                surname: announcement.author?.surname,
              })}
            </p>
            {isGlobal && (
              <p style={{ fontWeight: 700 }}>
                <Link to={`/course/${announcement.course.courseId}`}>
                  <AnnouncementSubject>
                    {announcement.course.subject.name}
                  </AnnouncementSubject>
                </Link>
              </p>
            )}
          </div>
          {isTeacher && (
            <SmallIcon
              src="/images/trash-red.png"
              alt={t("AnnouncementUnit.alt.deleteButton")}
              onClick={() => onDelete!(announcement.announcementId)}
            />
          )}
        </div>
      </AnnouncementHeader>
      <AnnouncementDate>
        {announcement.date.toLocaleDateString()}
      </AnnouncementDate>
      {announcement?.content.split("\n").map((str, idx) => (
        <p key={idx}>{str.length > 300 ? ReadMore(str) : str}</p>
      ))}
    </AnnouncementWrapper>
  );
}

export default AnnouncementUnit;
