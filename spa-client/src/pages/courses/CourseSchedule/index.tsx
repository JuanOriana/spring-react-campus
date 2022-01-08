import {
  BigWrapper,
  SectionHeading,
} from "../../../components/generalStyles/utils";

const times = [
  { begin: { hour: 18, minute: 30 }, end: { hour: 21, minute: 30 } },
  null,
  null,
  null,
  { begin: { hour: 18, minute: 30 }, end: { hour: 21, minute: 30 } },
  null,
];

function Custom404() {
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
        Horarios
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>
          "course-schedule.comment", no recuerdo que era
        </h3>
        {days.map((day, index) => (
          <>
            {times[index] && (
              <>
                <h3 style={{ margin: "3px 0 0 10px" }}>{day}</h3>
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

export default Custom404;
