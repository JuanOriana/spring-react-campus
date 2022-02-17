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

function AnnouncementUnit({
  isGlobal,
  isTeacher,
  announcement,
  onDelete,
}: AnnouncementUnitProps) {
  const { t } = useTranslation();
  const [isReadMore, setIsReadMore] = useState(true);
  const toggleReadMore = () => {
    setIsReadMore(!isReadMore);
  };

  const readMoreBound = 580;

  const readMore = (content: string) => {
    const shorten = isReadMore && content.length > readMoreBound;
    const contentFinal = shorten ? content.slice(0, readMoreBound) : content;
    return (
      <>
        {contentFinal.split("\n").map((str, idx) => (
          <p key={idx}>{str}</p>
        ))}
        <span>
          {content.length > readMoreBound && (
            <ReadMoreButton onClick={toggleReadMore}>
              {t(
                isReadMore
                  ? "AnnouncementUnit.readMoreButton"
                  : "AnnouncementUnit.readLessButton"
              )}
            </ReadMoreButton>
          )}
        </span>
      </>
    );
  };

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
              <div style={{ fontWeight: 700 }}>
                <Link to={`/course/${announcement.course.courseId}`}>
                  <AnnouncementSubject>
                    {announcement.course.subject.name}
                  </AnnouncementSubject>
                </Link>
              </div>
            )}
          </div>
          {isTeacher && (
            <SmallIcon
              src="./images/trash-red.png"
              alt={t("AnnouncementUnit.alt.deleteButton")}
              onClick={() => onDelete!(announcement.announcementId)}
            />
          )}
        </div>
      </AnnouncementHeader>
      <AnnouncementDate>
          <img src={ './images/clock.png' } alt={t('AnnouncementUnit.alt.clock')} style={{ width: 16, height: 16, marginRight: 8, verticalAlign: "middle" }}/>
          {announcement.date.toLocaleDateString()} - {announcement.date.toTimeString().split(' ')[0].substring(0,5)}
          </AnnouncementDate>
      {readMore(announcement.content)}
    </AnnouncementWrapper>
  );
}

export default AnnouncementUnit;
