import { TimetableLayout, Days, Time } from "./styles";
import { SectionHeading } from "../../components/generalStyles/utils";
import React, { useEffect, useState } from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { userService } from "../../services";
import { useAuth } from "../../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import { handleService } from "../../scripts/handleService";
import LoadableData from "../../components/LoadableData";
import { CourseModel } from "../../types";
//

function Timetable() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const days: string[] = [
    "Lunes",
    "Martes",
    "Miercoles",
    "Jueves",
    "Viernes",
    "Sabado",
  ];
  const hours: string[] = [
    "08:00",
    "09:00",
    "10:00",
    "11:00",
    "12:00",
    "13:00",
    "14:00",
    "15:00",
    "16:00",
    "17:00",
    "18:00",
    "19:00",
    "20:00",
    "21:00",
    "22:00",
  ];
  const colors: string[] = [
    "#2EC4B6",
    "#173E5C",
    "#B52F18",
    "#821479",
    "#6F9A13",
  ];
  const [courseToColor, setCourseToColor] = useState<
    Map<number, string> | undefined
  >(undefined);
  let maxIdx = 0;
  const { user } = useAuth();
  const [times, setTimes] = useState<(CourseModel | null)[][]>([[null]]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    setIsLoading(true);
    if (user) {
      handleService(
        userService.getTimeTable(user.userId),
        navigate,
        (timesData) => {
          setTimes(timesData ? timesData : [[]]);
          if (!timesData) {
            setCourseToColor(new Map());
            return;
          }
          const newCourseToColor = new Map<number, string>();
          for (let i = 0; i < 6; i++) {
            for (let j = 0; j < 15; j++) {
              if (timesData[i] && timesData[i][j]) {
                newCourseToColor.set(timesData[i][j]!.courseId, colors[maxIdx]);
                maxIdx = (maxIdx + 1) % colors.length;
              }
            }
          }
          setCourseToColor(newCourseToColor);
        },
        () => {
          setIsLoading(false);
        }
      );
    }
  }, [user]);
  return (
    <>
      <SectionHeading>{t("Timetable.title")}</SectionHeading>
      <LoadableData isLoading={isLoading}>
        <div>
          <TimetableLayout>
            <Days>
              <th></th>
              {days.map((day) => (
                <th key={day}>{t("DaysOfTheWeek." + day)}</th>
              ))}
            </Days>
            {hours.map((hour, hourIdx) => (
              <tr key={hour}>
                <Time>{hour}</Time>
                {courseToColor &&
                  days.map((day, dayIdx) => {
                    if (!times || !times[dayIdx] || !times[dayIdx][hourIdx])
                      return <td key={`${dayIdx}-${hourIdx}`}></td>;
                    const currentCourse = times[dayIdx][hourIdx];
                    return (
                      <td
                        style={{
                          background: courseToColor.get(
                            currentCourse!.courseId
                          ),
                          cursor: "pointer",
                          fontWeight: 700,
                        }}
                        data-tooltip={
                          currentCourse?.subject.code +
                          " [" +
                          currentCourse?.board +
                          "]"
                        }
                      >
                        <Link
                          key={`${dayIdx}-${hourIdx}`}
                          to={`/course/${currentCourse!.courseId}`}
                        >
                          {times[dayIdx][hourIdx]?.subject.name}
                        </Link>
                      </td>
                    );
                  })}
              </tr>
            ))}
          </TimetableLayout>
        </div>
      </LoadableData>
    </>
  );
}

export default Timetable;
