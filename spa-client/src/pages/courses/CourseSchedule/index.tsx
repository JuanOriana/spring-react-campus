import {
  BigWrapper,
  SectionHeading,
} from "../../../components/generalStyles/utils";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

const times = [
  { begin: { hour: 18, minute: 30 }, end: { hour: 21, minute: 30 } },
  null,
  null,
  null,
  { begin: { hour: 18, minute: 30 }, end: { hour: 21, minute: 30 } },
  null,
];

function CourseSchedule() {
  const { t } = useTranslation();
  const days: string[] = [
    "Lunes",
    "Martes",
    "Miercoles",
    "Jueves",
    "Viernes",
    "Sabado",
  ];

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t('CourseSchedule.title')}
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>
          {t('CourseSchedule.subTitle')}
        </h3>
        {days.map((day, index) => (
          <>
            {times[index] && (
              <>
                <h3 style={{ margin: "3px 0 0 10px" }}>{t('DaysOfTheWeek.' + day)}</h3>
                <p style={{ marginLeft: "15px" }}>
                  {`› ${times[index]!.begin.hour}:${
                    times[index]!.begin.minute
                  } - ${times[index]!.end.hour}:${times[index]!.end.minute}`}
                </p>
              </>
            )}
          </>
        ))}
      </BigWrapper>
    </>
  );
}

export default CourseSchedule;
