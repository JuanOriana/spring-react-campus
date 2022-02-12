import {
  BigWrapper,
  SectionHeading,
} from "../../../components/generalStyles/utils";
import React, { useEffect, useState } from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
import { courseService } from "../../../services";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import { handleService } from "../../../scripts/handleService";
import { useNavigate } from "react-router-dom";
import LoadableData from "../../../components/LoadableData";
//

function CourseSchedule() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const course = useCourseData();
  const [times, setTimes] = useState(new Array(1));
  const [isLoading, setIsLoading] = useState(true);
  const days: string[] = [
    "Lunes",
    "Martes",
    "Miercoles",
    "Jueves",
    "Viernes",
    "Sabado",
  ];

  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getTimes(course.courseId),
      navigate,
      (timesData) => setTimes(timesData ? timesData : []),
      () => setIsLoading(false)
    );
  }, []);

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t("CourseSchedule.title")}
      </SectionHeading>
      <BigWrapper>
        <LoadableData isLoading={isLoading}>
          <h3 style={{ margin: "10px 0" }}>{t("CourseSchedule.subTitle")}</h3>
          {days.map((day, index) => (
            <div key={day}>
              {times[index] && times[index].startTime && (
                <>
                  <h3 style={{ margin: "3px 0 0 10px" }}>
                    {t("DaysOfTheWeek." + day)}
                  </h3>
                  <p style={{ marginLeft: "15px" }}>
                    {`› ${times[index]!.startTime}
                     - ${times[index]!.endTime}`}
                  </p>
                </>
              )}
            </div>
          ))}
        </LoadableData>
      </BigWrapper>
    </>
  );
}

export default CourseSchedule;
